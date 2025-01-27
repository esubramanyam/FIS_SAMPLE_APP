package com.fis.deloitte.planOnboarding.security;

import com.fis.deloitte.planOnboarding.exception.UserNameFetchException;
import com.fis.deloitte.planOnboarding.logout.Blacklist;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtHelper {

    private static final Logger logger = LoggerFactory.getLogger(JwtHelper.class);

    //public static final long JWT_TOKEN_VALIDITY = 1*60*60;//hr*min*sec
    public static final long JWT_TOKEN_VALIDITY = 1 * 60;

    private String secretKey = "afafafafaGHNTRTahsgdakhdgakdadyhbdxasbkagksagdxyhagsyuagxavshauyaHASKGXDADCGVAHDCVAJHCVJKCVSDKCAJCAHCA";

    @Autowired
    private Blacklist blacklist;

    public String getUsernameFromToken(String token) {
            logger.info("Extracting username from token...");
            return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
            logger.info("Extracting expiration date from token...");
            return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T>T getClaimFromToken(String token, Function<Claims,T> claimsResolver){
            logger.info("Extracting claims from token...");
            final Claims claims = getAllClaimsFromToken(token);
            return claimsResolver.apply(claims);
    }
    public String generateToken(UserDetails userDetails) {
            logger.info("Generating token for user: {}", userDetails.getUsername());
            Map<String, Object> claims = new HashMap<>();
            return doGenerateToken(claims, userDetails.getUsername());
    }
    private Claims getAllClaimsFromToken(String token){
            logger.info("Getting all claims from token...");
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
    private Boolean isTokenExpired(String token){
            logger.info("Checking if token is expired...");
            final Date expiration = getExpirationDateFromToken(token);
            return expiration.before(new Date());
    }
    private String doGenerateToken(Map<String, Object> claims, String subject){
            logger.info("Generating token with claims and subject...");
            return Jwts.builder().setClaims(claims)
                    .setSubject(subject)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                    .signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }
 public Boolean validateToken(String token, UserDetails userDetails){
         logger.info("Validating token for user: {}", userDetails.getUsername());
         final String username = getUsernameFromToken(token);
           return (username.equals(userDetails.getUsername()) && !isTokenExpired(token)
                   && !blacklist.isBlackListed(token));
    }
}




