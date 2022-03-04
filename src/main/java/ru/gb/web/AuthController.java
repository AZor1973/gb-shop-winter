package ru.gb.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.gb.api.security.dto.UserDto;
import ru.gb.entity.security.AccountStatus;
import ru.gb.entity.security.AccountUser;
import ru.gb.service.MailService;
import ru.gb.service.UserService;

import javax.validation.Valid;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final MailService mailService;
    private Integer code;

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
        } catch (UsernameNotFoundException ignored) {
        }

        userService.register(userDto);
        code = new Random().nextInt(9000) + 1000;
        mailService.sendMail(userDto.getEmail(), "Подтверждение регистрации", "Код для подтверждения регистрации: " + code);
        log.info("Successfully created user with username: {}", username);
        model.addAttribute("username", username);
        // todo ДЗ 11 - добавить подтверждение email перед конечной активацией
        // todo сделать так чтобы аккаунт был создан но находился в статусе NOT_ACTIVE и enable=false до тех пор пока не введет на сайте пароль из мейла
        return "auth/registration-confirmation";
    }

    @PostMapping("/confirm")
    public String handleConfirmation(@RequestParam String inputCode, @RequestParam String username,
                                     Model model) {
        if (code == Integer.parseInt(inputCode)) {
            AccountUser user = userService.findByUsername(username);
            user.setStatus(AccountStatus.ACTIVE);
            user.setEnabled(true);
            userService.update(user);
        } else {
            model.addAttribute("wrongCode", "Неправильный код");
            model.addAttribute("username", username);
            return "auth/registration-confirmation";
        }
        return "redirect:/product/all";
    }
}
