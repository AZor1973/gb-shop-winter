package ru.gb.external.api.rest.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import ru.gb.api.security.dto.UserDto;
import ru.gb.external.api.service.UserService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> getUserList() {
        return userService.findAll();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable("userId") Long id) {
        UserDto userDto;
        if (id != null) {
            userDto = userService.findById(id);
            if (userDto != null) {
                return new ResponseEntity<>(userDto, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> handlePost(@Validated @RequestBody UserDto userDto) {
        userService.register(userDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> handleUpdate(@PathVariable("userId") Long id,
                                          @Validated @RequestBody UserDto userDto) {
        log.info("Change user with id {}", id);
        userDto.setId(id);
        userService.update(userDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("userId") Long id) {
        userService.deleteById(id);
    }


}
