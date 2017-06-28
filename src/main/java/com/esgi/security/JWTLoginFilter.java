package com.esgi.security;

import com.esgi.ruc.RucEntity;
import com.esgi.security.domain.Credential;
import com.esgi.user.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by rfruitet on 06/04/2017.
 */

/**
 * Intercept POST request on /login and authenticate the user
*/
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger logger = LoggerFactory.getLogger(JWTLoginFilter.class);

    private UserRepository userRepository;

    private UserService userService;

    private UserEntity userEntity;

    public JWTLoginFilter(String url, AuthenticationManager authManager, UserRepository userRepository, UserService userService) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);

        this.userRepository = userRepository;
        this.userService = userService;
        this.userEntity = null;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException, IOException, ServletException {

        Credential credential = new ObjectMapper()
                .readValue(req.getInputStream(), Credential.class);

        // TODO: 07/04/2017 PASSWORD HASH
        this.userEntity = userRepository.findByEmail(credential.getUsername());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(16);

		boolean result = false;

		if(userEntity != null) {
			result = passwordEncoder.matches(credential.getPassword(), userEntity.getPassword());
		}

		if (!result) {
			logger.info("email or password incorrect");
			res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "L'adresse email ou le mot de passe est incorrect.");
			return null;
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
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res,
											FilterChain chain, Authentication auth) throws IOException, ServletException {

		UserDto userDto = userService.getCompleteUserDto(userEntity);
        TokenAuthenticationService.addAuthentication(res, userDto);
    }
}
