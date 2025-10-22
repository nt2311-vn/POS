package com.nt2311.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.crypto.SecretKey;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtValidator extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String jwt = request.getHeader(JwtConstant.JWT_HEADER);

    if (jwt != null) {
      jwt = jwt.substring(7);

      try {
        SecretKey key = Keys.hmacShaKeyFor(JwtConstant.JWT_SECRET.getBytes());
        Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt).getPayload();

        String email = String.valueOf(claims.get("email"));
        String authorities = String.valueOf(claims.get("authorities"));
      } catch (Exception e) {
        // TODO: handle exception
      }
    }
  }
}
