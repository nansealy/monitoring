package com.sahno.service.impl;

import com.sahno.model.entity.business.User;
import com.sahno.repository.UserRepo;
import com.sahno.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
public class UserServiceImplTest {

    private static final String USERNAME = "useraname";

    @InjectMocks
    private UserDetailsService userService;
    @Mock
    private UserRepo userRepo;
    private User user;


    @Before
    public void setUp() {
        user = new User();
    }


    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsernameException() {
        when(userRepo.findByUsername(USERNAME)).thenReturn(null);
        userService.loadUserByUsername(USERNAME);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsername() {
        when(userRepo.findByUsername(USERNAME)).thenReturn(user);
        Assert.assertEquals(user, userService.loadUserByUsername(USERNAME));
    }
}