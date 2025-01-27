package com.fis.deloitte.planOnboarding.config;

import com.fis.deloitte.planOnboarding.repository.UserRepository;
import com.fis.deloitte.planOnboarding.security.JwtAuthenticationEntryPoint;
import com.fis.deloitte.planOnboarding.security.JwtAuthenticationFilter;
import com.fis.deloitte.planOnboarding.security.JwtHelper;
import com.fis.deloitte.planOnboarding.service.impl.CustomUserDetailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
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
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(
                                //EXCLUDED_PATTERNS
                                "/auth/**","/swagger-ui/index.html",
                                      "/swagger-ui/**", "/v3/api-docs","/api-docs/**","/v3/api-docs/**"
                                     //   "/swagger-ui/index.html","/v3/api-docs/**","/swagger-ui/**"
                                ).permitAll()
                                .anyRequest().authenticated())
                .exceptionHandling(exception -> {
                    exception.authenticationEntryPoint(point);
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtHelper, userDetailService,handlerExceptionResolver
                //             , new CustomAuthenticationSuccessHandler(userRepository)
        );
        filter.setAuthenticationManager(authenticationManager(http));
        filter.setFilterProcessesUrl("/user/users");
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        JwtAuthenticationFilter filter2 = new JwtAuthenticationFilter(jwtHelper, userDetailService,handlerExceptionResolver);
        filter2.setAuthenticationManager(authenticationManager(http));
        filter2.setFilterProcessesUrl("/user/logout");
        http.addFilterBefore(filter2, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userDetailService);
        return builder.build();
    }
}
