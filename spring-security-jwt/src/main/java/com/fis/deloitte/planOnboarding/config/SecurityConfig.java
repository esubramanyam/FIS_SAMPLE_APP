package com.fis.deloitte.planOnboarding.config;

import com.fis.deloitte.planOnboarding.repository.UserRepository;
import com.fis.deloitte.planOnboarding.security.JwtAuthenticationEntryPoint;
import com.fis.deloitte.planOnboarding.security.JwtAuthenticationFilter;
import com.fis.deloitte.planOnboarding.security.JwtHelper;
import com.fis.deloitte.planOnboarding.service.impl.CustomUserDetailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

@Configuration
@NoArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint point;

    @Autowired
    private CustomUserDetailService userDetailService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver handlerExceptionResolver;

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);


    private static final String[] EXCLUDED_PATTERNS={
            "/auth/**",
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/configuration/ui/**",
            "/swagger-resources/**",
            "/configuration/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/webjares/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        logger.info("Configuring HttpSecurity...");
        try{
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    logger.info("Setting authorization rules...");
                    auth.requestMatchers(
                                    //EXCLUDED_PATTERNS
                                    "/auth/**", "/swagger-ui/index.html",
                                    "/swagger-ui/**", "/v3/api-docs", "/api-docs/**", "/v3/api-docs/**"
                            ).permitAll()
                            .anyRequest().authenticated();
                })
                .exceptionHandling(exception -> {
                    logger.info("Configuring exception handling...");
                    exception.authenticationEntryPoint(point);
                })
                .sessionManagement(session -> {
                    logger.info("Setting session management policy...");
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                });

        logger.info("Adding JWT authentication filter...");
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtHelper, userDetailService,handlerExceptionResolver);
        filter.setAuthenticationManager(authenticationManager(http));
        filter.setFilterProcessesUrl("/user/users");
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        JwtAuthenticationFilter filter2 = new JwtAuthenticationFilter(jwtHelper, userDetailService,handlerExceptionResolver);
        filter2.setAuthenticationManager(authenticationManager(http));
        filter2.setFilterProcessesUrl("/user/logout");
        http.addFilterBefore(filter2, UsernamePasswordAuthenticationFilter.class);
        logger.info("HttpSecurity configuration completed.");
        return http.build();
    } catch (Exception e) {
        logger.error("Error configuring HttpSecurity: {}", e.getMessage());
        throw e;
    }
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        logger.info("Configuring DaoAuthenticationProvider...");
        try{
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        logger.info("DaoAuthenticationProvider configured.");
        return provider;
    } catch (Exception e) {
        logger.error("Error configuring DaoAuthenticationProvider: {}", e.getMessage());
        throw e;
    }
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        try{
        logger.info("Configuring PasswordEncoder...");
        return new BCryptPasswordEncoder();
    } catch (Exception e) {
        logger.error("Error configuring PasswordEncoder: {}", e.getMessage());
        throw e;
    }
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        try{
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userDetailService);
        return builder.build();
        } catch (Exception e) {
            logger.error("Error configuring AuthenticationManager: {}", e.getMessage());
            throw e;
        }
    }
}
