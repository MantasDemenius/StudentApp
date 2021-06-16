package com.app.ktu.controller;

import com.app.ktu.dto.LectureDTO;
import com.app.ktu.service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LectureController {

  private LectureService lectureService;

  @Autowired
  public void setLectureService(LectureService lectureService) {
    this.lectureService = lectureService;
  }

  @GetMapping("/lecture/active")
  @PreAuthorize("hasAuthority('lectures:read')")
  public ResponseEntity<LectureDTO> getActiveLecture() {
    return lectureService.getActiveLecture();
  }

}
