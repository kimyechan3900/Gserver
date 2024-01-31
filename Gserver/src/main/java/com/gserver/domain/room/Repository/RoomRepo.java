package com.gserver.domain.room.Repository;

import com.gserver.domain.room.Model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepo extends JpaRepository<Room, String> {
    Optional<Room> findByRoomId(String roomId);

}
