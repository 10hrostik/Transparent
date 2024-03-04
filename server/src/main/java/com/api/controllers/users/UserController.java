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
@RequestMapping("/secured/user")
public class UserController {

  private final UserService userService;

  private final JwtConfig jwtUtil;

  @PatchMapping(value = "/password", produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<ResponseUserDto> editPassword(@RequestBody EditUserPasswordDto dto) {
    return userService.editUserPassword(dto.getCredential(), dto.getNewPassword());
  }

  @PatchMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<ResponseUserDto>> editProfile(@RequestBody EditUserProfileDto userProfileDto) {
    return userService.edit(userProfileDto)
     .map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.badRequest().build());
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

      return Mono.just(ResponseEntity.noContent().build());
    } catch (Exception e) {
      return Mono.just(ResponseEntity.internalServerError().build());
    }
  }

  @GetMapping("/refresh/{username}")
  public Mono<ResponseEntity<String>> refreshToken(@PathVariable String username) {
    return userService.findByUsername(username).cast(User.class)
     .map(this::refresh).defaultIfEmpty(ResponseEntity.badRequest().build());
  }

  private ResponseEntity<String> refresh(User user) {
    String token = jwtUtil.generateToken(user);
    return ResponseEntity.ok(token);
  }
}
