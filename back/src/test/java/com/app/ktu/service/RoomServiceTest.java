package com.app.ktu.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.app.ktu.model.CourseUsage;
import com.app.ktu.model.Room;
import com.app.ktu.model.Seat;
import com.app.ktu.repository.RoomRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class RoomServiceTest {

  @InjectMocks
  private RoomService roomServiceUnderTest;
  @Mock
  private RoomRepository mockRoomRepository;
  private AutoCloseable closeable;

  @BeforeEach
  void setUp() {
    closeable = MockitoAnnotations.openMocks(this);
    roomServiceUnderTest = new RoomService();
    roomServiceUnderTest.setRoomRepository(mockRoomRepository);
  }

  @Test
  void getRoomById_RoomExists_Room() {
    // Setup
    Room expectedResult = getExpectedRoom();
    mockFindRoomById();
    // Run the test
    final Room result = roomServiceUnderTest.getRoomById(0L);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void getRooms_RoomsExist_RoomsList() {
    // Setup
    mockFindAllRooms();
    Room expectedResult = getExpectedRoom();
    // Run the test
    final List<Room> result = roomServiceUnderTest.getRooms();

    // Verify the results
    assertThat(result).isEqualTo(List.of(expectedResult));
  }

  void mockFindRoomById() {
    Room expectedResult = getExpectedRoom();
    when(mockRoomRepository.findById(anyLong())).thenReturn(Optional.of(expectedResult));
  }

  void mockFindAllRooms() {
    Room expectedResult = getExpectedRoom();
    when(mockRoomRepository.findAllByOrderByAddressAscBuildingAscRoomNumberAsc()).thenReturn(List.of(expectedResult));
  }

  Room getExpectedRoom() {
    List<Seat> seats = new ArrayList<Seat>();
    List<CourseUsage> courseUsages = new ArrayList<CourseUsage>();
    return new Room(0L, "address", "building", "roomNumber", "type", seats, courseUsages);
  }
}
