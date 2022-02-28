package ru.gb.web.dto.mapper;

import org.mapstruct.Mapper;
import ru.gb.api.security.dto.UserDto;
import ru.gb.entity.security.AccountUser;

@Mapper
public interface UserMapper {
    AccountUser toAccountUser(UserDto userDto);
    UserDto toUserDto(AccountUser accountUser);
}
