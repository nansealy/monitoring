package com.sahno.config.security.jwt;

import com.sahno.model.entity.business.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@PropertySource("classpath:security.properties")
public class JwtProvider {
    @Value("${jwt.session.secret_key}")
    private String key;
    @Value("${jwt.session.cookie.title}")
    private String cookieTitle;
    @Value("${jwt.session.identity_claim_name}")
    private String identityClaimName;
    @Autowired
    @Qualifier("userServiceImpl")
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder encoder;


    public String generateToken(String username) {
        if (username == null) {
            return null;
        }
        Map<String, Object> tokenData = new HashMap<>();
        tokenData.put("username", username);

        JwtBuilder builder = Jwts.builder();
        builder.addClaims(tokenData);
        return builder.signWith(SignatureAlgorithm.HS512, key).compact();
    }

    public String getTokenFrom(HttpServletRequest request) {
        if (request.getCookies() != null) {
            return Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals(cookieTitle))
                    .findFirst().map(Cookie::getValue).orElse(null);
        }
        return null;
    }

    public void setToken(String token) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getResponse();
        Cookie cookie = new Cookie(cookieTitle, token);
        cookie.setPath("/");

        response.addCookie(cookie);
    }

    public boolean isValid(String token) {
        try {
            Jwts.parser().setSigningKey(key).parse(token);
            return true;
        } catch (ExpiredJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        try {
            UserDetails user = userDetailsService.loadUserByUsername(getUsername(token));
            return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
        } catch (UsernameNotFoundException e) {
            return null;
        }
    }

    private String getUsername(String token) {
        DefaultClaims claims = (DefaultClaims) Jwts.parser().setSigningKey(key).parse(token).getBody();
        return claims.get(identityClaimName, String.class);
    }

    public boolean autoAuth(HttpServletRequest request) {
        String token = getTokenFrom(request);
        if (token != null && isValid(token)) {
            Authentication auth = getAuthentication(token);

            if (auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth);
                return true;
            }
        }
        return false;
    }

    public boolean authenticate(User user) {
        UserDetails userFromDb = userDetailsService.loadUserByUsername(user.getUsername());
        boolean usernameIsEquals = userFromDb.getUsername()
                .equals(user.getUsername());
        boolean passwordIsEquals = encoder.matches(
                user.getPassword(), userFromDb.getPassword());

        if (usernameIsEquals && passwordIsEquals) {
            Authentication auth = new UsernamePasswordAuthenticationToken(userFromDb, "", user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
            setToken(generateToken(userFromDb.getUsername()));
            return true;
        }
        return false;
    }
}
