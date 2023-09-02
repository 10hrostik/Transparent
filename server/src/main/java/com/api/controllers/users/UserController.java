package com.api.controllers.users;

import com.api.controllers.dto.user.EditUserPasswordDto;
import com.api.controllers.dto.user.ResponseUserDto;
import com.api.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClientRequest;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/secured/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PatchMapping(value = "/edit/password", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseUserDto> editPassword(@RequestBody EditUserPasswordDto dto) {
        return userService.editUser(dto.getCredential(), dto.getNewPassword());
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
            e.printStackTrace();
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }

    }
}
