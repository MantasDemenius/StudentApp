package com.app.ktu.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.impl.DefaultClaims;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JwtUtil implements Serializable {

  private String accessSecret;
  private String refreshSecret;
  private String refreshCookieName;
  private int jwtExpirationInMs;
  private int refreshExpirationDateInMs;
  private String path;

  @Value("${url.path}")
  public void setPath(String path) {
    this.path = path;
  }

  @Value("${jwt.accessSecret}")
  public void setAccessSecret(String accessSecret) {
    this.accessSecret = accessSecret;
  }

  @Value("${jwt.refreshSecret}")
  public void setRefreshSecret(String refreshSecret) {
    this.refreshSecret = refreshSecret;
  }

  @Value("${jwt.refreshCookieName}")
  public void setRefreshCookieName(String refreshCookieName) {
    this.refreshCookieName = refreshCookieName;
  }

  @Value("${jwt.expirationInMs}")
  public void setJwtExpirationInMs(int jwtExpirationInMs) {
    this.jwtExpirationInMs = jwtExpirationInMs;
  }

  @Value("${jwt.refreshExpirationDateInMs}")
  public void setRefreshExpirationDateInMs(int refreshExpirationDateInMs) {
    this.refreshExpirationDateInMs = refreshExpirationDateInMs;
  }

  public String generateToken(UserDetails userDetails) {
    return Jwts
      .builder()
      .setSubject(userDetails.getUsername())
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
      .signWith(SignatureAlgorithm.HS512, accessSecret)
      .compact();
  }

  public String generateRefreshToken(UserDetails userDetails) {
    return Jwts
      .builder()
      .setSubject(userDetails.getUsername())
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationDateInMs))
      .signWith(SignatureAlgorithm.HS512, refreshSecret)
      .compact();
  }

  public boolean validateToken(String token, String secret) {
    try {
      Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
      return true;
    } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
      throw new BadCredentialsException("INVALID_CREDENTIALS", ex);
    } catch (ExpiredJwtException ex) {
      throw ex;
    }
  }

  public boolean validateAccessToken(String token) {
    return validateToken(token, accessSecret);
  }

  public boolean validateRefreshToken(String token) {
    return validateToken(token, refreshSecret);
  }


  public Boolean validateTokenWithSubject(String token, UserDetails userDetails, String type) {
    final String username = getUsernameFromToken(token, type);
    return username.equals(userDetails.getUsername());
  }

  public Date getExpirationDateFromToken(String token, String type) {
    return getClaimFromToken(token, Claims::getExpiration, type);
  }

  public String getUsernameFromToken(String token, String type) {
    return getClaimFromToken(token, Claims::getSubject, type);
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver, String type) {
    final Claims claims = getAllClaimsFromToken(token, type);
    return claimsResolver.apply(claims);
  }

  private Claims getAllClaimsFromToken(String token, String type) {
    String secret = "";
    if (type.equals("access")) {
      secret = accessSecret;
    }
    if (type.equals("refresh")) {
      secret = refreshSecret;
    }
    return Jwts.parser()
      .setSigningKey(secret)
      .parseClaimsJws(token)
      .getBody();
  }

  public UsernamePasswordAuthenticationToken getAuthentication(final String token,
    final Authentication existingAuth, final UserDetails userDetails) {
    validateAccessToken(token);
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public void createRefreshCookie(String refreshToken, HttpServletResponse response) {
    Cookie cookie = new Cookie(refreshCookieName, refreshToken);
    cookie.setMaxAge(refreshExpirationDateInMs);
    cookie.setHttpOnly(true);
    cookie.setPath(path + "/refreshtoken");

    response.addCookie(cookie);
  }

  public String extractJwtFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7, bearerToken.length());
    }
    return null;
  }

  public String extractRefreshJwtFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("refresh_token");
    if (StringUtils.hasText(bearerToken)) {
      return bearerToken;
    }
    return null;
  }

  public Map<String, Object> getMapFromJwtClaims(DefaultClaims claims) {
    Map<String, Object> expectedMap = new HashMap<String, Object>();
    for (Entry<String, Object> entry : claims.entrySet()) {
      expectedMap.put(entry.getKey(), entry.getValue());
    }
    return expectedMap;
  }

  public String getSubjectFromClaims(Claims claims) {
    return getMapFromJwtClaims((DefaultClaims) claims)
      .get("sub")
      .toString();
  }
}
