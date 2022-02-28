package ru.gb.config;

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

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(
                (requests) -> {
                    requests.antMatchers("/").permitAll();
                    requests.antMatchers("/product/all").permitAll();
                    requests.antMatchers(HttpMethod.POST,"/product").hasRole("ADMIN");
                    requests.mvcMatchers(HttpMethod.GET,"/product/{productId}").permitAll();
                }
        );
        http.authorizeRequests((requests) -> {
            ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)requests.anyRequest()).authenticated();
        });
        http.exceptionHandling().accessDeniedPage("/access-denied");
        http.formLogin();
        http.httpBasic();
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user")
//                .password("user")
//                .roles("$2a$10$rSL9AI8zo3jevAAjOJtwLOCknuqPWMQsw5dMFoCSi0IdTwyz7kuMi")
//                .and()
//                .withUser("admin")
//                .password("$2a$10$MTOD2wE6bZqbgm0DfG41be44FCTnNyeVkkksycfWCJaFPmgHazOUG")
//                .roles("ADMIN");
//    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
