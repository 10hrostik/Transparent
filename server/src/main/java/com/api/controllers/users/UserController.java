package com.api.controllers.users;

import com.api.configuration.JwtConfig;
import com.api.controllers.dto.users.EditUserPasswordDto;
import com.api.controllers.dto.users.EditUserProfileDto;
import com.api.controllers.dto.users.ResponseUserDto;
import com.api.entities.users.User;
import com.api.services.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/secured/user")
public class UserController {
    private final UserService userService;

    private final JwtConfig jwtUtil;

    @PatchMapping(value = "/password", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseUserDto> editPassword(@RequestBody EditUserPasswordDto dto) {
        return userService.editUserPassword(dto.getCredential(), dto.getNewPassword());
    }

    @PatchMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ResponseUserDto>> editProfile(@RequestBody EditUserProfileDto userProfileDto) {
        return userService.editUser(userProfileDto)
                .map(user -> {
                    user.setToken(jwtUtil.generateToken(user));
                    return ResponseEntity.ok(user);
                }).defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @GetMapping(value = "/logout")
    public Mono<ResponseEntity<Object>> logout(ServerHttpRequest httpRequest, ServerHttpResponse httpResponse) {
        try {
            MultiValueMap<String, HttpCookie> cookies = httpRequest.getCookies();
            for (Map.Entry<String, List<HttpCookie>> cookie : cookies.entrySet()) {
                for (HttpCookie cookieToBeDeleted : cookie.getValue()) {
                    httpResponse.addCookie(ResponseCookie.from(cookieToBeDeleted.getName(), cookieToBeDeleted.getValue())
                            .maxAge(0).build());
                }
            }

            return Mono.just(ResponseEntity.status(HttpStatus.OK).build());
        } catch (Exception e) {
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }
    }

    @GetMapping("/refresh/{username}")
    public Mono<ResponseEntity<String>> refreshToken(@PathVariable String username) {
        return userService.findByUsername(username).cast(User.class)
                .map(user -> {
                    String token = jwtUtil.generateToken(user);
                    return ResponseEntity.ok(token);
                }).defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
