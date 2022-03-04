package ru.gb.service;

import ru.gb.api.security.dto.UserDto;
import ru.gb.entity.security.AccountUser;

import java.util.List;

public interface UserService {

    UserDto register(UserDto userDto);

    UserDto update(UserDto userDto);

    UserDto findById(Long id);

    List<UserDto> findAll();

    AccountUser findByUsername(String username);

    AccountUser update(AccountUser accountUser);

    void deleteById(Long id);
}
