package com.example.Gserver.Main.domain.participate.Repository;

import com.example.Gserver.Main.domain.participate.Model.Participation;
import com.example.Gserver.Main.domain.room.Model.Room;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface ParticipationRepo extends JpaRepository<Participation,Long> {

    Optional<Participation> findByRoomIDAndNickName(Room room, String nickName);

    Optional<Participation> findByRoomIDAndRoomOwnerIsTrue(Room room);


    boolean existsByRoomIDAndNickName(Room room, String nickName);

    List<Participation> findAllByRoomID(Room room);
}

