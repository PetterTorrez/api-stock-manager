package com.bluedot.stock_manager.auth.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;

  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
  ) throws ServletException, IOException {
    final String authHeader = request.getHeader("Authorization");
    final Boolean isAuthPath = request.getRequestURI().startsWith("/auth/");

    if (isAuthPath || authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    String jwt = authHeader.substring(7);

    try {
      String email = jwtService.extractEmailFromToken(jwt);
      Long userId = jwtService.extractUserIdFromToken(jwt);
      String role = jwtService.extractRoleFromToken(jwt);

      if (
        email != null &&
        SecurityContextHolder.getContext().getAuthentication() == null
      ) {
        if (jwtService.isTokenValid(jwt, email)) {
          UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(
              userId,
              null,
              Collections.singletonList(new SimpleGrantedAuthority(role))
            );

          authToken.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request)
          );

          SecurityContextHolder.getContext().setAuthentication(authToken);
        }
      }
    } catch (ExpiredJwtException ex) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType("application/json");
      response
        .getWriter()
        .write(
          """
              {
                  "success": false,
                  "message": "Token expirado"
              }
          """
        );

      return;
    } catch (JwtException ex) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType("application/json");
      response
        .getWriter()
        .write(
          """
              {
                  "success": false,
                  "message": "Token inválido"
              }
          """
        );

      return;
    }

    filterChain.doFilter(request, response);
  }
}
