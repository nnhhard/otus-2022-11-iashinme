package ru.iashinme.blog.security;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import ru.iashinme.blog.exception.ValidateException;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {

    private final UserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException {

        try {
            Cookie[] cookies = ((HttpServletRequest) servletRequest).getCookies();
            String userToken = null;
            String userName = null;

            if (cookies != null) {
                userToken = Arrays.stream(cookies)
                        .filter(cookie -> cookie.getName().equals("userToken"))
                        .map(Cookie::getValue)
                        .findAny()
                        .orElse(null);
            }

            if (StringUtils.isNotBlank(userToken))
                userName = jwtTokenProvider.getUsername(userToken);

            if (StringUtils.isNotBlank(userName)) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(servletRequest, servletResponse);
        } catch (JwtException e) {
            log.error(e.getMessage());
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            Cookie cookie = new Cookie("userToken", "");
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ValidateException(e.getMessage());
        }
    }
}
