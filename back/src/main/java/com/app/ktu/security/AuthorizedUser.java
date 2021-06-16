package com.app.ktu.security;

import com.app.ktu.model.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthorizedUser {

  public static long getAuthorizedUserId() {
    Principal userPrincipal = (Principal) SecurityContextHolder.getContext().getAuthentication()
      .getPrincipal();
    return userPrincipal.getUser().getId();
  }

  public static User getAuthorizedUser() {
    Principal userPrincipal = (Principal) SecurityContextHolder.getContext().getAuthentication()
      .getPrincipal();
    return userPrincipal.getUser();
  }

  public static Principal getAuthorizedPrincipal() {
    Principal userPrincipal = (Principal) SecurityContextHolder.getContext().getAuthentication()
      .getPrincipal();
    return userPrincipal;
  }
}
