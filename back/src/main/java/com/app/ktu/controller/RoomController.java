package com.app.ktu.controller;

import com.app.ktu.dto.RoomDTO;
import com.app.ktu.model.Room;
import com.app.ktu.service.RoomService;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class RoomController {

  private RoomService roomService;

  @Autowired
  private ModelMapper modelMapper;

  public RoomController(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  @Autowired
  public RoomService setRoomService(RoomService roomService) {
    return this.roomService = roomService;
  }

  @GetMapping("/rooms")
  @PreAuthorize("hasAuthority('rooms:read')")
  public List<RoomDTO> getRooms() {
    return convertRoomListToRoomDTOList(roomService.getRooms());
  }

  private List<RoomDTO> convertRoomListToRoomDTOList(List<Room> rooms) {
    return rooms.stream().map(this::convertRoomToRoomDTO)
      .collect(Collectors.toList());
  }

  private RoomDTO convertRoomToRoomDTO(Room room) {
    return modelMapper.map(room, RoomDTO.class);
  }
}
