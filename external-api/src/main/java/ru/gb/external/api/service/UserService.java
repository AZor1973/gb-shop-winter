package ru.gb.external.api.service;

import ru.gb.api.security.dto.UserDto;
import ru.gb.external.api.entity.security.AccountUser;

import java.util.List;

public interface UserService {

    UserDto register(UserDto userDto);

    UserDto update(UserDto userDto);

    UserDto findById(Long id);

    List<UserDto> findAll();

    AccountUser findByUsername(String username);

    void deleteById(Long id);
}
