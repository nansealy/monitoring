package com.sahno.service.impl;

import com.sahno.model.dto.VisibilityDto;
import com.sahno.repository.UserRepo;
import com.sahno.service.VisibilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisibilityServiceImpl implements VisibilityService {
    @Autowired
    private UserRepo userRepo;


    @Override
    public VisibilityDto calculateSignUpVisibility() {
        long count = userRepo.count();
        String visibility = "";
        if (count > 0){
            visibility = "visibility: hidden";
        }
        return VisibilityDto.builder().signUpVisible(visibility).build();
    }
}
