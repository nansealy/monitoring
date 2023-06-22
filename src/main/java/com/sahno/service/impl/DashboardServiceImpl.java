package com.sahno.service.impl;

import com.sahno.jdbc.JdbcService;
import com.sahno.model.dto.DashboardDto;
import com.sahno.model.entity.business.Dashboard;
import com.sahno.model.entity.business.DashboardRes;
import com.sahno.repository.DashboardRepo;
import com.sahno.repository.DashboardResRepo;
import com.sahno.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {
    @Autowired
    private DashboardRepo dashboardRepo;
    @Autowired
    private DashboardResRepo dashboardResRepo;
    @Autowired
    private JdbcService jdbcService;


    @Override
    public List<DashboardDto> getAll() {
        List<DashboardDto> res = new ArrayList<>();
        List<Dashboard> dashboards = dashboardRepo.findAll();
        dashboards.stream().forEach(dashboard -> {
            DashboardDto dto = DashboardDto.convertToDto(dashboard);
            dto.setCountRes(dashboard.getResults().size());
            res.add(dto);
        });
        return res;
    }

    @Override
    public void updateQuery(Long id, String query) {
        dashboardRepo.findById(id).get().setQuery(query);
        dashboardRepo.flush();
    }

    @Override
    @Transactional
    public void refresh(Long id) {
        Dashboard dashboard = dashboardRepo.findById(id).get();
        String query = dashboard.getQuery();
        if (query != null) {
            dashboardResRepo.clear(id);

            List<String> jsons = jdbcService.executeQuery(dashboard.getUrl(), dashboard.getUsername(), dashboard.getPassword(), query);

            List<DashboardRes> results = jsons.stream()
                    .map(json -> DashboardRes.builder().result(json).build())
                    .collect(Collectors.toList());

            dashboard.setResults(results);
        }
    }

    @Override
    @Transactional
    public void refreshAll() {
        dashboardRepo.findAll().stream().forEach(dashboard -> refresh(dashboard.getId()));
    }
}
