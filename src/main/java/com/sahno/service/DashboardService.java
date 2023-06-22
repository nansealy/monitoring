package com.sahno.service;

import com.sahno.model.dto.DashboardDto;

import java.util.List;

public interface DashboardService {
    List<DashboardDto> getAll();

    void updateQuery(Long id, String query);

    void refresh(Long id);

    void refreshAll();
}
