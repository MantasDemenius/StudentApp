package com.app.ktu.controller;

import com.app.ktu.common.converter.SeatToSeatUsageDTOConverter;
import com.app.ktu.common.converter.SeatUsageToUserCourseSeatDTOConverter;
import com.app.ktu.dto.EndDateDTO;
import com.app.ktu.dto.LectureDTO;
import com.app.ktu.dto.seat.OccupySeatDTO;
import com.app.ktu.dto.seat.SeatCreateDTO;
import com.app.ktu.dto.seat.SeatManageDTO;
import com.app.ktu.dto.seat.SeatUsageDTO;
import com.app.ktu.dto.user.UserCourseSeatDTO;
import com.app.ktu.model.Seat;
import com.app.ktu.model.SeatUsage;
import com.app.ktu.service.SeatService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SeatController {

  private SeatService seatService;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  public SeatService setSeatService(SeatService seatService) {
    return this.seatService = seatService;
  }

  @GetMapping("/rooms/{roomId}/seats")
  @PreAuthorize("hasAuthority('seats:read')")
  public List<SeatManageDTO> getRoomSeats(@PathVariable(value = "roomId") long roomId) {
    return convertSeatListToSeatManageDTOList(seatService.getRoomSeats(roomId));
  }

  @PostMapping("/rooms/{roomId}/seats")
  @PreAuthorize("hasAuthority('seats:create')")
  public ResponseEntity<List<SeatManageDTO>> createSeats(
    @PathVariable(value = "roomId") long roomId,
    @Valid @RequestBody SeatCreateDTO seats) {
    return new ResponseEntity<>(
      convertSeatListToSeatManageDTOList(seatService.createSeats(roomId, seats)),
      HttpStatus.CREATED);
  }

  @DeleteMapping("/seats/{seatId}")
  @PreAuthorize("hasAuthority('seats:delete')")
  public ResponseEntity deleteSeat(@PathVariable(value = "seatId") long seatId) {
    return seatService.deleteSeat(seatId);
  }

  @GetMapping(value = "/rooms/{roomId}/seats/{seatId}/qr", produces = "image/png")
  @PreAuthorize("hasAuthority('seats:download')")
  public ResponseEntity<byte[]> downloadSeatQr(
    @PathVariable(value = "roomId") long roomId,
    @PathVariable(value = "seatId") long seatId) {
    return seatService.qrCodeGeneration(roomId, seatId);
  }

  @PostMapping("/seats/occupy")
  @PreAuthorize("hasAuthority('seats:occupy')")
  public ResponseEntity<LectureDTO> occupySeat(@Valid @RequestBody OccupySeatDTO occupySeatDTO) {
    return seatService.occupySeat(occupySeatDTO);
  }

  @GetMapping("/seats/leave")
  @PreAuthorize("hasAuthority('seats:occupy')")
  public ResponseEntity leaveSeat() {
    return seatService.leaveSeat();
  }

  @GetMapping("/rooms/{roomId}/seatUsages")
  @PreAuthorize("hasAuthority('seats:usage')")
  public List<SeatUsageDTO> getActiveSeatUsagesByRoomId(
    @PathVariable(value = "roomId") long roomId) {
    return convertSeatListToSeatUsageDTOList(seatService.getRoomSeats(roomId));
  }

  @PatchMapping("/seats/{seatId}/clear")
  @PreAuthorize("hasAuthority('seats:clear')")
  public ResponseEntity clearSeat(@RequestBody EndDateDTO endDateDTO,
    @PathVariable(value = "seatId") long seatId) {
    return seatService.clearSeat(endDateDTO, seatId);
  }

  @PatchMapping("/seats/{seatId}/lock")
  @PreAuthorize("hasAuthority('seats:lock')")
  public ResponseEntity lockSeat(@PathVariable(value = "seatId") long seatId,
    @RequestParam boolean isLocked) {
    return seatService.lockSeat(seatId, isLocked);
  }

  @GetMapping("/courses/{courseId}/seatUsages")
  @PreAuthorize("hasAuthority('seats:history')")
  public List<UserCourseSeatDTO> getCourseSeatUsages(
    @PathVariable(value = "courseId") long courseId) {
    return convertSeatUsageListToUserCourseSeatDTOList(seatService.getCourseSeatUsages(courseId));
  }

  private List<UserCourseSeatDTO> convertSeatUsageListToUserCourseSeatDTOList(
    List<SeatUsage> seats) {
    return seats.stream().map(this::convertSeatUsageToUserCourseSeatDTO)
      .collect(Collectors.toList());
  }

  private UserCourseSeatDTO convertSeatUsageToUserCourseSeatDTO(SeatUsage seat) {
    modelMapper.addConverter(new SeatUsageToUserCourseSeatDTOConverter());
    return modelMapper.map(seat, UserCourseSeatDTO.class);
  }

  private List<SeatManageDTO> convertSeatListToSeatManageDTOList(
    List<Seat> seats) {
    return seats.stream().map(this::convertSeatToSeatManageDTO)
      .collect(Collectors.toList());
  }

  private SeatManageDTO convertSeatToSeatManageDTO(Seat seat) {
    return modelMapper.map(seat, SeatManageDTO.class);
  }

  private List<SeatUsageDTO> convertSeatListToSeatUsageDTOList(
    List<Seat> seats) {
    return seats.stream().map(this::convertSeatToSeatUsageDTO)
      .collect(Collectors.toList());
  }

  private SeatUsageDTO convertSeatToSeatUsageDTO(Seat seat) {
    modelMapper.addConverter(new SeatToSeatUsageDTOConverter());
    return modelMapper.map(seat, SeatUsageDTO.class);
  }
}