package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.AuthService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private UserDetailsService userDetailsService;

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;


    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService,
                          JwtAuthenticationFilter jwtAuthFilter,
                          AuthenticationProvider authenticationProvider) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider(AuthService authService) {
//        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
//        auth.setUserDetailsService(authService);
//        auth.setPasswordEncoder(passwordEncoder());
//        return auth;
//    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeRequests(auth ->
//                auth
//                        // auth
//                        .requestMatchers(HttpMethod.POST, "api/v1/auth/login").permitAll()
//                        .requestMatchers(HttpMethod.POST, "api/v1/auth/register").permitAll()
//                        .requestMatchers(HttpMethod.POST, "api/v1/auth/register/confirm").permitAll()
//                        .requestMatchers(HttpMethod.POST, "api/v1/auth/forgot-password").permitAll()
//                        .requestMatchers(HttpMethod.POST, "api/v1/auth/forgot-password/confirm").permitAll()
//                        .anyRequest().permitAll()
//        );
//        http.httpBasic();
//        http.csrf().disable();
//        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
//                .requestMatchers(HttpMethod.POST, "").permitAll()
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/api/v1/product/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/test/").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
