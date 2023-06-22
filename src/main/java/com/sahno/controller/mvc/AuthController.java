package com.sahno.controller.mvc;

import com.sahno.service.VisibilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private VisibilityService visibilityService;

    @GetMapping("/sign_in")
    public String getSignInPage(Model model) {
        model.addAttribute("signUpVisibility", visibilityService.calculateSignUpVisibility().getSignUpVisible());
        return "sign_in";
    }

    @GetMapping("/sign_up")
    public String getSignUpPage() {
        return "sign_up";
    }
}
