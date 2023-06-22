package com.sahno.job;

import com.sahno.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class RefreshJob {
    @Autowired
    private DashboardService dashboardService;


    @Scheduled(fixedDelayString = "${refresh.fixed.delay}")
    public void refresh() {
        dashboardService.refreshAll();
    }
}
