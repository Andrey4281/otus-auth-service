package ru.otus.authservice.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.authservice.domain.UserLogin;
import ru.otus.authservice.dto.UserLoginDto;
import ru.otus.authservice.util.security.PasswordUtil;

@Component
@RequiredArgsConstructor
public class UserLoginRowMapper {

    private final PasswordUtil securityUtil;

    public UserLogin toEntity(UserLoginDto userLoginDto) {
        return UserLogin.builder()
                .login(userLoginDto.getLogin())
                .password(securityUtil.getSecuredPassword(userLoginDto.getPassword()))
                .build();
    }
}
