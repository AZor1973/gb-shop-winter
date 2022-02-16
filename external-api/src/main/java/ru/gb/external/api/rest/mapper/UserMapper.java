package ru.gb.external.api.rest.mapper;

import org.mapstruct.Mapper;
import ru.gb.api.security.dto.UserDto;
import ru.gb.external.api.entity.security.AccountUser;

@Mapper
public interface UserMapper {
    AccountUser toAccountUser(UserDto userDto);
    UserDto toUserDto(AccountUser accountUser);
}
