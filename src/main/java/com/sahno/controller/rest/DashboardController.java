package com.sahno.controller.rest;

import com.sahno.model.dto.DashboardDto;
import com.sahno.repository.DashboardRepo;
import com.sahno.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;
    @Autowired
    private DashboardRepo dashboardRepo;


    @GetMapping("/all")
    public List<DashboardDto> getAll(){
        return dashboardService.getAll();
    }

    @PostMapping("/remove")
    public void remove(@RequestParam("id") Long id){
        dashboardRepo.deleteById(id);
    }

    @PostMapping("/create")
    public void create(DashboardDto dto){
        dashboardRepo.saveAndFlush(DashboardDto.convertToEntity(dto));
    }

    @GetMapping("/find")
    public DashboardDto findBy(@RequestParam("id") Long id){
        return DashboardDto.convertToDto(dashboardRepo.findById(id).get());
    }

    @PostMapping("/updateQuery")
    public void updateQuery(@RequestParam("id") Long id, DashboardDto dto){
        dashboardService.updateQuery(id, dto.getQuery());
    }

    @PostMapping("/run")
    public void run(@RequestParam("id") Long id){
        dashboardService.refresh(id);
    }

    @PostMapping("/delete")
    public void delete(@RequestParam("id") Long id){
        dashboardRepo.deleteById(id);
    }
}
