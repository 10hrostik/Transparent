package com.api.controllers.users;

import com.api.controllers.dto.user.RegisterUserDto;
import com.api.controllers.dto.user.ResponseUserDto;
import com.api.services.users.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/public/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping(value = "/login/phone", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseUserDto> login(@RequestParam Long credential) {
        return authService.getUser(credential, "");
    }

    @PostMapping(value = "/login/credential", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseUserDto> login(@RequestParam String credential, @RequestParam String password) {
        return authService.getUser(credential, password);
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseUserDto> register(@RequestBody RegisterUserDto userDto) {
          return authService.register(userDto);
    }
}
