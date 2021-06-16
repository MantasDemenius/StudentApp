package com.app.ktu.security;

import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import java.util.Arrays;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class CustomJwtAuthenticationFilter extends OncePerRequestFilter {

  private String refreshCookieName;
  @Autowired
  private JwtUtil jwtTokenUtil;
  @Autowired
  private CustomUserDetailsService customUserDetailsService;

  @Value("${jwt.refreshCookieName}")
  public void setRefreshCookieName(String refreshCookieName) {
    this.refreshCookieName = refreshCookieName;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
    FilterChain chain)
    throws ServletException, IOException {
    try {
      String jwtToken = jwtTokenUtil.extractJwtFromRequest(request);
      if (StringUtils.hasText(jwtToken) && jwtTokenUtil.validateAccessToken(jwtToken)) {
        String username = jwtTokenUtil.getUsernameFromToken(jwtToken, "access");

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
          UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

          if (jwtTokenUtil.validateTokenWithSubject(jwtToken, userDetails, "access")) {
            UsernamePasswordAuthenticationToken authentication = jwtTokenUtil
              .getAuthentication(jwtToken, SecurityContextHolder.getContext().getAuthentication(),
                userDetails);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
          }

        }
      }
    } catch (ExpiredJwtException ex) {
      request.setAttribute("exception", ex);
      String requestURL = request.getRequestURL().toString();
      if (requestURL.contains("refresh_token")) {
        String refreshToken = jwtTokenUtil.extractRefreshJwtFromRequest(request);
        if (jwtTokenUtil.validateRefreshToken(refreshToken) && refreshToken != null) {
          String accessTokenSubject = jwtTokenUtil.getSubjectFromClaims(ex.getClaims());
          String username = jwtTokenUtil.getUsernameFromToken(refreshToken, "refresh");
          if (accessTokenSubject.equals(username)
            && SecurityContextHolder.getContext().getAuthentication() == null) {
            allowForRefreshToken();
            request.setAttribute("subject", username);
          }
        }
      }
    } catch (BadCredentialsException ex) {
      request.setAttribute("exception", ex);
    } catch (Exception ex) {
      request.setAttribute("exception", ex);
      System.out.println(ex);
    }
    chain.doFilter(request, response);
  }

  private void allowForRefreshToken() {
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
      null, null, null);
    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
  }

  public String getTokenFromCookies(Cookie[] cookies) {
    if (cookies != null) {
      return Arrays.stream(cookies).filter(c -> c.getName().equals(refreshCookieName)).map(
        Cookie::getValue).findFirst().orElse(null);
    }
    return "";
  }

}
