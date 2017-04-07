package com.esgi.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by rfruitet on 06/04/2017.
 */

/**
 * Intercept all requests to validate the presence of the token,
 * This validation is done with the help of the TokenAuthenticationService
 */
public class JWTAuthenticationFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain)
            throws IOException, ServletException {
        Authentication authentication = TokenAuthenticationService
                .getAuthentication((HttpServletRequest)request);

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
        filterChain.doFilter(request,response);
    }
}
