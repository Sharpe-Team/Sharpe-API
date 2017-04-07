package com.esgi.security;

import com.esgi.security.domain.Credential;
import com.esgi.user.UserEntity;
import com.esgi.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * Created by rfruitet on 06/04/2017.
 */

/**
 * Intercept POST request on /login and authenticate the user
*/
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger logger = LoggerFactory.getLogger(JWTLoginFilter.class);

    private UserRepository userRepository;

    public JWTLoginFilter(String url, AuthenticationManager authManager, UserRepository userRepository) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
        this.userRepository = userRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException, IOException, ServletException {

        Credential credential = new ObjectMapper()
                .readValue(req.getInputStream(), Credential.class);

        // TODO: 07/04/2017 PASSWORD HASH
        UserEntity userEntity = userRepository.findByEmail(credential.getUsername());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(16);

       // if (userEntity == null
         //       || passwordNotMatching(userEntity.getPassword(), credential.getPassword())) {
         if (userEntity == null
               || !passwordEncoder.matches(credential.getPassword(), userEntity.getPassword())) {
            logger.info("email or password incorrect");
            throw new AuthenticationFailException();
        }
        
        return new UsernamePasswordAuthenticationToken(
                credential.getUsername(),
                credential.getPassword(),
                Collections.emptyList());
        /**Authentication authenticate = getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        credential.getUsername(),
                        credential.getPassword(),
                        Collections.emptyList()
                ));
        return authenticate;*/
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res, FilterChain chain,
            Authentication auth) throws IOException, ServletException {
        TokenAuthenticationService
                .addAuthentication(res, auth.getName());
    }

    private boolean passwordNotMatching(String passwordRepository, String passwordRequest) {
        return passwordRepository.compareTo(passwordRequest) == 1;
    }
}
