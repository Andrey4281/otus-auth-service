package ru.otus.authservice.web.rest.userlogin;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.authservice.dto.UserLoginDto;
import ru.otus.authservice.dto.UserLoginResponse;
import ru.otus.authservice.service.UserLoginService;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class UserLoginController {

    private final UserLoginService userLoginService;

    @PostMapping(value = "/credentials/signUp")
    public ResponseEntity<Void> createCredentials(@RequestBody UserLoginDto userDto) {
        userLoginService.createCredentials(userDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/credentials/edit/{oldLogin}")
    public ResponseEntity<Void> editCredentials(@RequestBody UserLoginDto userLoginDto,
                                                @PathVariable String oldLogin) {

        userLoginService.editCredentials(userLoginDto, oldLogin);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/credentials/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginDto userLoginDto) {
        return ResponseEntity.ok(userLoginService.login(userLoginDto));
    }
}
