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
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.AuthService;

import java.util.Arrays;

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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:3001", "https://teelab-admin-v2.web.app/", "https://teelab-client-v1.web.app"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        //

        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                // USER
                .requestMatchers(HttpMethod.POST, "/api/v1/user").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/user/{id:\\d+}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/user/{id:\\d+}").hasRole("ADMIN")
                // BLOG
                .requestMatchers(HttpMethod.POST, "/api/v1/blog").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/blog/{id:\\d+}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/blog/{id:\\d+}").hasRole("ADMIN")
                // PRODUCT
                .requestMatchers(HttpMethod.POST, "/api/v1/product").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/product/{id:\\d+}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/product/{id:\\d+}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/product").hasRole("PRODUCT_MANAGER")
                .requestMatchers(HttpMethod.PUT, "/api/v1/product/{id:\\d+}").hasRole("PRODUCT_MANAGER")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/product/{id:\\d+}").hasRole("PRODUCT_MANAGER")
                // COLOR
                .requestMatchers(HttpMethod.POST, "/api/v1/color").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/color/{id:\\d+}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/color/{id:\\d+}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/color").hasRole("PRODUCT_MANAGER")
                .requestMatchers(HttpMethod.PUT, "/api/v1/color/{id:\\d+}").hasRole("PRODUCT_MANAGER")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/color/{id:\\d+}").hasRole("PRODUCT_MANAGER")
                // SIZE
                .requestMatchers(HttpMethod.POST, "/api/v1/size").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/size/{id:\\d+}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/size/{id:\\d+}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/size").hasRole("PRODUCT_MANAGER")
                .requestMatchers(HttpMethod.PUT, "/api/v1/size/{id:\\d+}").hasRole("PRODUCT_MANAGER")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/size/{id:\\d+}").hasRole("PRODUCT_MANAGER")
                // CATEGORIES
                .requestMatchers(HttpMethod.POST, "/api/v1/category").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/category/{id:\\d+}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/category/{id:\\d+}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/category").hasRole("PRODUCT_MANAGER")
                .requestMatchers(HttpMethod.PUT, "/api/v1/category/{id:\\d+}").hasRole("PRODUCT_MANAGER")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/category/{id:\\d+}").hasRole("PRODUCT_MANAGER")
                // WAREHOUSE
                .requestMatchers(HttpMethod.POST, "/api/v1/warehouse").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/warehouse").hasRole("PRODUCT_MANAGER")
                // DISCOUNT CODE
                .requestMatchers(HttpMethod.PUT, "/api/v1/discount-code/{id:\\d+}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/discount-code/{id:\\d+}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/discount-code/{id:\\d+}").hasRole("PROMOTION_MANAGER")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/discount-code/{id:\\d+}").hasRole("PROMOTION_MANAGER")
                // PROMOTION
                .requestMatchers(HttpMethod.POST, "/api/v1/promotion").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/promotion/{id:\\d+}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/promotion/{id:\\d+}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/promotion").hasRole("PROMOTION_MANAGER")
                .requestMatchers(HttpMethod.PUT, "/api/v1/promotion/{id:\\d+}").hasRole("PROMOTION_MANAGER")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/promotion/{id:\\d+}").hasRole("PROMOTION_MANAGER")
                // SLIDER
                .requestMatchers(HttpMethod.POST, "/api/v1/slider").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/slider/{id:\\d+}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/slider/{id:\\d+}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/slider").hasRole("PROMOTION_MANAGER")
                .requestMatchers(HttpMethod.PUT, "/api/v1/slider/{id:\\d+}").hasRole("PROMOTION_MANAGER")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/slider/{id:\\d+}").hasRole("PROMOTION_MANAGER")
                // ORDER
                .requestMatchers(HttpMethod.PUT, "/api/v1/order/confirm/{id:\\d+}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/order/{id:\\d+}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/order/confirm/{id:\\d+}").hasRole("ORDER_MANAGER")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/order/{id:\\d+}").hasRole("ORDER_MANAGER")
                // REVIEW
//                .requestMatchers(HttpMethod.POST, "/api/v1/review").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/review/{id:\\d+}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/review/{id:\\d+}").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.POST, "/api/v1/review").hasRole("ORDER_MANAGER")
                .requestMatchers(HttpMethod.PUT, "/api/v1/review/{id:\\d+}").hasRole("ORDER_MANAGER")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/review/{id:\\d+}").hasRole("ORDER_MANAGER")

                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/api/v1/user/**").permitAll()
                .requestMatchers("/api/v1/product/**").permitAll()
                .requestMatchers("/api/v1/category/**").permitAll()
                .requestMatchers("/api/v1/color/**").permitAll()
                .requestMatchers("/api/v1/size/**").permitAll()
                .requestMatchers("/api/v1/promotion/**").permitAll()
                .requestMatchers("/api/v1/warehouse/**").permitAll()
                .requestMatchers("/api/v1/discount-code/**").permitAll()
                .requestMatchers("/api/v1/order/**").permitAll()
                .requestMatchers("/api/v1/payment/**").permitAll()
                .requestMatchers("/api/v1/blog/**").permitAll()
                .requestMatchers("/api/v1/review/**").permitAll()
                .requestMatchers( "/api/v1/test/**").permitAll()
                .requestMatchers("/api/v1/permission/**").permitAll()
                .requestMatchers("/api/v1/log/**").permitAll()
                .requestMatchers("/api/v1/slider/**").permitAll()
                .requestMatchers("/api/v1/notification/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .cors().configurationSource(source);
//                .cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
        ;
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:3001")); // specify the allowed origin here
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
