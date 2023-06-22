package com.sahno.model.dto;

import com.sahno.model.entity.business.DashboardRes;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class DashboardResDto {
    private String result;


    public static DashboardResDto convertToDto(Object dashboardRes) {
        return new ModelMapper().map(dashboardRes, DashboardResDto.class);
    }

    public static DashboardRes convertToEntity(DashboardResDto dashboardResDto) {
        return new ModelMapper().map(dashboardResDto, DashboardRes.class);
    }
}
