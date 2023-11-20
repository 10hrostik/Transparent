package com.api.controllers.users;

import com.api.configuration.JwtConfig;
import com.api.controllers.dto.user.EditUserPasswordDto;
import com.api.controllers.dto.user.RegisterUserDto;
import com.api.controllers.dto.user.ResponseUserDto;
import com.api.services.users.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/public/auth")
public class AuthController {
    private AuthService authService;

    private JwtConfig jwtUtil;

    public AuthController(AuthService authService, JwtConfig jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(value = "/login/phone", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ResponseUserDto>> login(@RequestParam Long credential,
                                                       ServerWebExchange serverWebExchange) {
        return createResponse(credential, "", serverWebExchange);
    }

    @PostMapping(value = "/login/credential", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ResponseUserDto>> login(@RequestParam String username,
                                                       @RequestParam String password,
                                                       ServerWebExchange serverWebExchange) {
        return createResponse(username, password, serverWebExchange);
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ResponseUserDto>> register(@RequestBody RegisterUserDto userDto) {
          return authService.register(userDto).map(user -> {
              user.setToken(jwtUtil.generateToken(user));
              return ResponseEntity.ok(user);
          }).defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PatchMapping(value = "/restore/password", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> restorePassword(@RequestBody EditUserPasswordDto passwordDto) {
        return authService.restore(passwordDto).map(response -> ResponseEntity.status(HttpStatus.OK)
                .build())
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    private Mono<ResponseEntity<ResponseUserDto>> createResponse(Object userCredential, String password,
                                                                 ServerWebExchange serverWebExchange) {
        return serverWebExchange.getFormData()
                .flatMap(credential -> authService.getUser(userCredential, password))
                .map(user -> {
                    user.setToken(jwtUtil.generateToken(user));
                    return ResponseEntity.ok(user);
                }).defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
