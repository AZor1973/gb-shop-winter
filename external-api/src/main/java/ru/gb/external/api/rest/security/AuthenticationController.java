package ru.gb.external.api.rest.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.support.incrementer.HanaSequenceMaxValueIncrementer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.api.security.dto.AuthenticationUserDto;
import ru.gb.external.api.entity.security.AccountUser;
import ru.gb.external.api.security.JwtTokenProvider;
import ru.gb.external.api.service.UserService;

import java.util.HashMap;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthenticationController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationUserDto authenticationUserDto) {
        AccountUser accountUser = userService.findByUsername(authenticationUserDto.getUsername());

        String token = jwtTokenProvider.createToken(accountUser.getUsername(), accountUser.getRoles());

        HashMap<Object, Object> authMap = new HashMap<>();
        authMap.put("username", accountUser.getUsername());
        authMap.put("token", token);

        return ResponseEntity.ok(authMap);
    }

}
