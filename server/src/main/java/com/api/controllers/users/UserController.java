package com.api.controllers.users;

import com.api.controllers.dto.user.EditUserPasswordDto;
import com.api.controllers.dto.user.ResponseUserDto;
import com.api.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/secured/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PatchMapping(value = "/edit/password", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseUserDto> editPassword(@RequestBody EditUserPasswordDto dto) {
        return userService.editUser(dto.getCredential(), dto.getNewPassword());
    }
}
