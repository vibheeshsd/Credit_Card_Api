package com.example.CreditCard.Dto;

import com.example.CreditCard.Entity.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Data
@Builder
public class UserDTO {
    private Long id;
    private String userName;
    private String email;
    private String password;

    // Convert from User entity to UserDTO
    public static UserDTO fromEntity(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .password(user.getPassword()) // Typically, you'd not return password in the DTO, just for example
                .build();
    }

    // Convert from UserDTO to User entity
    public User toEntity() {
        User user = new User();
        user.setId(this.id);
        user.setUserName(this.userName);
        user.setEmail(this.email);
        user.setPassword(this.password);
        return user;
    }
}
