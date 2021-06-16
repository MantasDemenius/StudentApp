package com.app.ktu.service;

import com.app.ktu.exception.RecordNotFoundException;
import com.app.ktu.model.Room;
import com.app.ktu.repository.RoomRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

  private RoomRepository roomRepository;

  @Autowired
  public void setRoomRepository(RoomRepository roomRepository) {
    this.roomRepository = roomRepository;
  }

  public Room getRoomById(long roomId) {
    return roomRepository.findById(roomId)
      .orElseThrow(
        () -> new RecordNotFoundException("Auditorija su " + roomId + " id buvo nerasta"));
  }

  public List<Room> getRooms() {
    return roomRepository.findAllByOrderByAddressAscBuildingAscRoomNumberAsc();
  }
}
