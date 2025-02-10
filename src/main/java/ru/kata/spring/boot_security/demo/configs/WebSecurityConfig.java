package ru.kata.spring.boot_security.demo.configs;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class WebSecurityConfig {
    private final SuccessUserHandler successUserHandler;

    public WebSecurityConfig(SuccessUserHandler successUserHandler) {
        this.successUserHandler = successUserHandler;
    }
    private UserDetailsService userDetailsService;

    @Autowired
    public void setUserDetailsService (UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/", "/index").permitAll()
//                        .requestMatchers("/login", "/login-error").permitAll() // Разрешаем доступ к страницам входа
//                        .requestMatchers("/user").hasAnyRole("USER", "ADMIN")
//                        .requestMatchers(HttpMethod.GET, "/user/**").hasAnyRole("USER", "ADMIN")
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.POST, "/admin/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.DELETE, "/admin/**").hasRole("ADMIN")
//                        .anyRequest().authenticated()
//                )
//                .formLogin(form -> form
//                        .loginPage("/login") // Указываем, что страница входа — это "/login"
//                        .failureUrl("/login-error") // При ошибке входа перенаправляем на "/login-error"
//                        .successHandler(successUserHandler)
//                        .permitAll()
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/login?logout")
//                        .permitAll()
//                );
//
//
//        return http.build();
//    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/index").permitAll()
                        .requestMatchers("/login", "/login-error").permitAll() // Разрешаем доступ к страницам входа
                        .requestMatchers("/user").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/user/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(withDefaults()) // Включаем стандартную форму логина
                .httpBasic(withDefaults()); // Поддержка Basic-Auth

        return http.build();
    }
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)  // Отключаем CSRF (для REST API)
//                .cors(Customizer.withDefaults())       // Включаем поддержку CORS
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Без сессий (JWT)
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers(HttpMethod.GET, "/api/public/**").permitAll()  // Публичные API
//                        .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()   // API для аутентификации
//                        .requestMatchers(HttpMethod.GET, "/api/user/**").hasAnyRole("USER", "ADMIN")  // Доступ к user API
//                        .requestMatchers(HttpMethod.POST, "/api/admin/**").hasRole("ADMIN")          // Админские API
//                        .requestMatchers(HttpMethod.DELETE, "/api/admin/**").hasRole("ADMIN")
//                        .anyRequest().authenticated()  // Все остальные запросы требуют аутентификации
//                )
//                .httpBasic(Customizer.withDefaults())  // Включаем Basic Auth (можно заменить на JWT)
//                .logout(logout -> logout
//                        .logoutUrl("/api/auth/logout")
//                        .logoutSuccessHandler((request, response, authentication) -> {
//                            response.setStatus(HttpServletResponse.SC_OK);
//                        })
//                        .permitAll()
//                );
//
//        return http.build();
//    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(authorize -> authorize
//                        .anyRequest().permitAll() // Разрешить ВСЕ запросы
//                )
//                .csrf(AbstractHttpConfigurer::disable) // Отключаем CSRF (не обязательно)
//                .formLogin(AbstractHttpConfigurer::disable) // Убираем форму логина
//                .httpBasic(AbstractHttpConfigurer::disable); // Отключаем Basic-Auth
//
//        return http.build();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }
}