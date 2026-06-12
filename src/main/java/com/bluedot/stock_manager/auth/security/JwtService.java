package com.bluedot.stock_manager.auth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  @Value("${jwt.secret}")
  private String secretKey;

  @Value("${jwt.expiration}")
  private long jwtExpiration;

  public String generateToken(Long userId, String email, String role) {
    return Jwts.builder()
      .setSubject(email)
      .claim("role", role)
      .claim("userId", userId)
      .setIssuedAt(new Date())
      .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
      .signWith(getSignKey(), SignatureAlgorithm.HS256)
      .compact();
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()
      .setSigningKey(getSignKey())
      .build()
      .parseClaimsJws(token)
      .getBody();
  }

  public String extractEmailFromToken(String token) {
    return extractAllClaims(token).getSubject();
  }

  public Long extractUserIdFromToken(String token) {
    return extractAllClaims(token).get("userId", Long.class);
  }

  public String extractRoleFromToken(String token) {
    return extractAllClaims(token).get("role", String.class);
  }

  public boolean isTokenValid(String token, String username) {
    final String extractedUsername = extractEmailFromToken(token);

    return extractedUsername.equals(username) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return extractAllClaims(token).getExpiration().before(new Date());
  }

  private Key getSignKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
