package com.app.ktu.controller;

import com.app.ktu.dto.AuthenticationRequestDTO;
import com.app.ktu.dto.AuthenticationResponseDTO;
import com.app.ktu.dto.RefreshAuthenticationResponseDTO;
import com.app.ktu.security.CustomUserDetailsService;
import com.app.ktu.security.JwtUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private CustomUserDetailsService userDetailsService;

  @Autowired
  private JwtUtil jwtTokenUtil;

  @PostMapping(value = "/authenticate")
  public ResponseEntity<?> authenticate(
    @RequestBody AuthenticationRequestDTO authenticationRequestDTO,
    HttpServletResponse response) {

    final Authentication authentication = authenticationManager
      .authenticate(new UsernamePasswordAuthenticationToken(
        authenticationRequestDTO.getUsername(), authenticationRequestDTO.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);

    final UserDetails userDetails = userDetailsService
      .loadUserByUsername(authenticationRequestDTO.getUsername());

    final String token = jwtTokenUtil.generateToken(userDetails);
    final String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails);

    return ResponseEntity.ok(new AuthenticationResponseDTO(token, refreshToken));
  }

  @GetMapping(value = "/refresh_token")
  public ResponseEntity<RefreshAuthenticationResponseDTO> refreshToken(HttpServletRequest request,
    HttpServletResponse response) {
    String subject = (String) request.getAttribute("subject");
    if (subject == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    final UserDetails userDetails = userDetailsService.loadUserByUsername(subject);
    final String token = jwtTokenUtil.generateToken(userDetails);

    return new ResponseEntity<>(new RefreshAuthenticationResponseDTO(token), HttpStatus.OK);
  }
}
