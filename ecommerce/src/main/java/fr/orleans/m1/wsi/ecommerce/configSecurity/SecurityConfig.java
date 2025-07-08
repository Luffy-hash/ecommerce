
package fr.orleans.m1.wsi.ecommerce.configSecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import fr.orleans.m1.wsi.ecommerce.services.custumUsers.CustumUsersService;
import fr.orleans.m1.wsi.ecommerce.services.entryPoint.JwtAuthenticationEntryPoint;
import fr.orleans.m1.wsi.ecommerce.services.jwtTokens.AuthFilterRequestService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustumUsersService userDetailService;
    private final AuthFilterRequestService authFilterRequestService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;


    public SecurityConfig(
        CustumUsersService userDetailService,
        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
        AuthFilterRequestService authFilterRequestService) {
        this.userDetailService = userDetailService;
        this.authFilterRequestService = authFilterRequestService;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            )
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(authFilterRequestService,
             UsernamePasswordAuthenticationFilter.class)
            .httpBasic(Customizer.withDefaults())
            .build();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
       return authenticationConfiguration.getAuthenticationManager();
    }

}