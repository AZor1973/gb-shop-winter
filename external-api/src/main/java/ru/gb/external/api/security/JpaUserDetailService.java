package ru.gb.external.api.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.api.security.dto.UserDto;
import ru.gb.external.api.dao.security.AccountRoleDao;
import ru.gb.external.api.dao.security.AccountUserDao;
import ru.gb.external.api.entity.security.AccountRole;
import ru.gb.external.api.entity.security.AccountStatus;
import ru.gb.external.api.entity.security.AccountUser;
import ru.gb.external.api.rest.mapper.UserMapper;
import ru.gb.external.api.service.UserService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class JpaUserDetailService implements UserDetailsService, UserService {

    private final AccountUserDao accountUserDao;
    private final AccountRoleDao accountRoleDao;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountUserDao.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username: " + username + " not found")
        );
    }

    @Override
    public UserDto register(UserDto userDto) {
        if (accountUserDao.findByUsername(userDto.getUsername()).isPresent()) {
            throw new RuntimeException("Username: " + userDto.getUsername() + " already exists");
        }
        AccountUser accountUser = userMapper.toAccountUser(userDto);
        AccountRole accountRole = accountRoleDao.findByName("ROLE_USER");

        accountUser.setRoles(Set.of(accountRole));
        accountUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        accountUser.setStatus(AccountStatus.ACTIVE);

        AccountUser registeredAccountUser = accountUserDao.save(accountUser);

        log.info("user with username {} was registered successfully", registeredAccountUser.getUsername());

        return userMapper.toUserDto(registeredAccountUser);

    }

    @Override
    public UserDto update(UserDto userDto) {
        return userMapper.toUserDto(accountUserDao.save(userMapper.toAccountUser(userDto)));
    }

    @Override
    public UserDto findById(Long id) {
        return userMapper.toUserDto(accountUserDao.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User with id = " + id + " not found")
        ));
    }

    @Override
    public List<UserDto> findAll() {
        log.info("findAll users was called");
        return accountUserDao.findAll().stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public AccountUser findByUsername(String username) {
        return accountUserDao.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User with username = " + username + " not found")
        );
    }

    @Override
    public void deleteById(Long id) {
        accountUserDao.deleteById(id);
    }
}
