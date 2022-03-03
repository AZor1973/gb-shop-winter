package ru.gb.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.api.security.dto.UserDto;
import ru.gb.service.UserService;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "auth/login-form";
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "auth/registration-form";
    }

    @PostMapping("/register")
    public String handleRegistration(@Valid UserDto userDto, BindingResult bindingResult, Model model) {
        String username = userDto.getUsername();
        log.info("Process registration form for: " + username);
        if (bindingResult.hasErrors()) {
            return "auth/registration-form";
        }
        try {
            userService.findByUsername(username);
            model.addAttribute("user", userDto);
            model.addAttribute("registrationError", "Пользователь с таким именем уже существует");
            log.info("Username {} already exists", username);
            return "auth/registration-form";
        } catch (UsernameNotFoundException ignored) {}

        userService.register(userDto);
        log.info("Successfully created user with username: {}", username);
        model.addAttribute("username", username);
        // todo ДЗ 11 - добавить подтверждение email перед конечной активацией
        // todo сделать так чтобы аккаунт был создан но находился в статусе NOT_ACTIVE и enable=false до тех пор пока не введет на сайте пароль из мейла
        return "auth/registration-confirmation";
    }

}
