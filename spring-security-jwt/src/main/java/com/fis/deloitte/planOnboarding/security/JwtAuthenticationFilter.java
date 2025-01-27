package com.fis.deloitte.planOnboarding.security;

import com.fis.deloitte.planOnboarding.exception.AuthException;
import com.fis.deloitte.planOnboarding.exception.InvalidHeaderException;
import com.fis.deloitte.planOnboarding.service.impl.CustomUserDetailService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@AllArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtHelper jwtHelper;

    private final CustomUserDetailService userDetailsService;

    private final HandlerExceptionResolver handlerExceptionResolver;

//    private final  customAuthenticationSuccessHandler;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        logger.info("Attempting authentication...");
        String requestHeader = request.getHeader("Authorization");
        logger.info("Header: {}", requestHeader);

        String username = null;
        String token = null;
        try {
            if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
                token = requestHeader.substring(7);
               // try {
                    username = jwtHelper.getUsernameFromToken(token);
               /* } catch (IllegalArgumentException e) {
                    logger.info("Illegal Argument while fetching the username!!");
                } catch (ExpiredJwtException e) {
                    logger.info("Given JWT token is expired!!");
                } catch (MalformedJwtException e) {
                    logger.info("Invalid Token!!");
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
            } else {
                logger.info("Invalid Header value!!");
                throw new InvalidHeaderException("Invalid Header value!!");
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                Boolean validateToken = jwtHelper.validateToken(token, userDetails);

                if (validateToken) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    logger.info("Authentication successful for user: {}", username);
                    return authentication;
                } else {
                    logger.info("Token validation failed!!");
                    throw new AuthException("Token validation failed!!");
                }
            }
        }catch(Exception ex){
            handlerExceptionResolver.resolveException(request,response,null,ex);
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
       // customAuthenticationSuccessHandler.onAuthenticationSuccess(request, response, authResult);
        chain.doFilter(request, response);
    }
}
