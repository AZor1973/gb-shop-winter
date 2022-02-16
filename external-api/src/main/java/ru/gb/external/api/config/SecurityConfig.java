package ru.gb.external.api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.gb.external.api.security.JwtConfig;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtConfig jwtConfig;

    public static final String USER_ENDPOINT = "/api/v1/user";
    public static final String LOGIN_ENDPOINT = "/api/v1/auth/login";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(
                (requests) -> {
                    requests.antMatchers("/product").permitAll();
                    requests.antMatchers(LOGIN_ENDPOINT).permitAll();
                    requests.antMatchers(HttpMethod.POST, USER_ENDPOINT).permitAll();
                    requests.antMatchers(USER_ENDPOINT).hasRole("ADMIN");
                    requests.anyRequest().authenticated();
//                    requests.antMatchers(HttpMethod.POST,"/product").hasRole("ADMIN");
//                    requests.mvcMatchers(HttpMethod.GET,"/product/{productId}").permitAll();
                }
        );
//        http.authorizeRequests((requests) -> {
//            ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)requests.anyRequest()).authenticated();
//        });
//        http.exceptionHandling().accessDeniedPage("/access-denied");

        http.apply(jwtConfig);
        http.httpBasic().disable();
        http.csrf().disable();
    }

}
