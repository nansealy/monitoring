package com.sahno.controller.rest;

import com.sahno.config.security.jwt.JwtProvider;
import com.sahno.model.dto.UserDto;
import com.sahno.model.entity.business.User;
import com.sahno.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class AuthRestController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider provider;


    @PostMapping("/authenticate")
    public void authenticate(UserDto userDto) {
        try {
            provider.authenticate(UserDto.convertToEntity(userDto));
        } catch (UsernameNotFoundException | DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password is invalid!");
        }
    }

    @PostMapping("/registration")
    public void registration(UserDto newUser) {
        try {
            User user = UserDto.convertToEntity(newUser);
            userService.create(user);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User already exists!");
        }
    }
}
