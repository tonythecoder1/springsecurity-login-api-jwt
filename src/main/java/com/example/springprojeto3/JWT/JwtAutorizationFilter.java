package com.example.springprojeto3.JWT;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtAutorizationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService detailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String token = request.getHeader(JWTUtils.JWT_AUTHORIZATION);

        if(token == null || !token.startsWith(JWTUtils.JWT_BEARER)){
            log.info("O JWT Token esta nulo, vazio");
            filterChain.doFilter(request, response);
            return;
        }

        if(!JWTUtils.isValidToken(token)){
            log.warn("O JWT é invalido ou expirado");
            filterChain.doFilter(request, response);
            return;
        }

        String username = JWTUtils.getUsernameFromToken(token);

        toAuthentication(request, username);

        filterChain.doFilter(request, response);
    }



    private void toAuthentication(HttpServletRequest request, String username) {
        Claims claims = JWTUtils.getClaimsFromToken(JWTUtils.refactorToken(request.getHeader(JWTUtils.JWT_AUTHORIZATION)));
        Long id = claims != null ? claims.get("id", Long.class) : null;

        JwtUserDetails userDetails = (JwtUserDetails) detailsService.loadUserByUsername(username);
        userDetails.setId(id);

        log.info(" ID extraído do Token: {}", id);
        userDetails.setId(id);

        log.info(" ID definido no JwtUserDetails: {}", userDetails.getId());

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        log.info("✅ Autenticação configurada para usuário: {}", userDetails.getUsername());
    }


}
