package com.sahno.model.dto;

import com.sahno.model.entity.business.User;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class UserDto {
    private String username;
    private String email;
    private String password;


    public static UserDto convertToDto(User user) {
        return new ModelMapper().map(user, UserDto.class);
    }

    public static User convertToEntity(UserDto userDto) {
        return new ModelMapper().map(userDto, User.class);
    }

}
