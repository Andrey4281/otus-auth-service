package ru.otus.authservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.authservice.domain.UserLogin;
import ru.otus.authservice.dto.UserLoginDto;
import ru.otus.authservice.dto.UserLoginResponse;
import ru.otus.authservice.exceptions.NotFoundUserException;
import ru.otus.authservice.mapper.UserLoginRowMapper;
import ru.otus.authservice.repository.UserLoginRepository;
import ru.otus.authservice.util.security.JwtTokenService;
import ru.otus.authservice.util.security.PasswordUtil;

import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class UserLoginService {
    private final UserLoginRepository userLoginRepository;
    private final UserLoginRowMapper userLoginRowMapper;
    private final PasswordUtil passwordUtil;
    private final JwtTokenService jwtTokenService;

    @Transactional(readOnly = false)
    public void createCredentials(UserLoginDto userLoginDto) {
        userLoginRepository.save(userLoginRowMapper.toEntity(userLoginDto));
    }

    @Transactional(readOnly = false)
    public void editCredentials(UserLoginDto userLoginDto, String oldLogin) {
        if (userLoginRepository.existsByLogin(oldLogin)) {
            userLoginRepository.deleteByLogin(oldLogin);
            userLoginRepository.save(userLoginRowMapper.toEntity(userLoginDto));
        } else {
            throw new NotFoundUserException(String.format("User with login=%s not found in database for update", oldLogin));
        }
    }

    public UserLoginResponse login(UserLoginDto userLoginDto) {
        return userLoginRepository.findById(userLoginDto.getLogin()).map(ul -> {
           UserLoginResponse userLoginResponse;
           if (ul.getPassword().equals(passwordUtil.getSecuredPassword(userLoginDto.getPassword()))) {
               userLoginResponse = UserLoginResponse.builder()
                       .isSuccess(true)
                       .token(jwtTokenService.generate(userLoginDto.getLogin()))
                       .build();
           } else {
               userLoginResponse = UserLoginResponse.builder()
                       .isSuccess(false)
                       .token(null)
                       .build();
           }
            return userLoginResponse;
        }).orElseThrow(() -> new NotFoundUserException("User not found"));
    }

    public Optional<UserLogin> getByLogin(String userLogin) {
        return userLoginRepository.findById(userLogin);
    }
}
