package com.app.ktu.repository;

import com.app.ktu.model.Room;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

  List<Room> findAllByOrderByAddressAscBuildingAscRoomNumberAsc();
}

