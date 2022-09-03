package ru.otus.authservice.dto;

import lombok.Getter;

@Getter
public class UserLoginDto {
    private String login;
    private String password;
}
