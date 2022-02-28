package ru.gb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.gb.dao.security.AccountUserDao;
import ru.gb.entity.security.AccountUser;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAwareBean")
public class ShopConfig {


    //  если в полях createdBy и updateBy хотим испольльзовать не String username а полноценного пользователя то раскомментировать и использовать этот код
    @Bean
//    public AuditorAware<AccountUser> auditorAwareBean(AccountUserDao accountUserDao) {
    public AuditorAware<String> auditorAwareBean(AccountUserDao accountUserDao) {
        return () -> Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getName);
//                .flatMap(accountUserDao::findByUsername);
    }
}
