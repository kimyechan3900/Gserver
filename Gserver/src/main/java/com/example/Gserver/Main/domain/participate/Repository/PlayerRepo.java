package com.example.Gserver.Main.domain.participate.Repository;

import com.example.Gserver.Main.domain.participate.Model.Player;
import com.example.Gserver.Main.domain.room.Model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface PlayerRepo extends JpaRepository<Player,Long> {

    Optional<Player> findByRoomAndNickName(Room room, String nickName);

    Optional<Player> findByRoomAndRoomOwnerIsTrue(Room room);


    boolean existsByRoomAndNickName(Room room, String nickName);

    List<Player> findByRoom(Room room);
}

