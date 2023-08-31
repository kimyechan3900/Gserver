package com.example.Gserver.Main.Repository;

import com.example.Gserver.Main.Model.Groom;
import com.example.Gserver.Main.Model.Participation;
import com.example.Gserver.Main.Model.PlayerAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PlayerAnswerRepo extends JpaRepository<PlayerAnswer,Long> {

    @Query("SELECT count(*) FROM PlayerAnswer p WHERE p.ParticipationId = :ParticipationId")
    int countByParticipationAnswer(@Param("ParticipationId")Participation ParticipationId);
    @Query("SELECT Answer FROM PlayerAnswer p WHERE p.ParticipationId = :ParticipationId and p.RoundCount = :RoundCount")
    String getByParticipationIDAndRoundCount(@Param("ParticipationId")Participation ParticipationId,@Param("RoundCount") int RoundCount);

    @Query("SELECT count(*) FROM PlayerAnswer p WHERE p.RoomID = :RoomID and p.RoundCount = :RoundCount")
    int getAnswerCount(@Param("RoomID")Groom groom,@Param("RoundCount") int RoundCount);

    @Query("SELECT Answer FROM PlayerAnswer p WHERE p.RoomID = :RoomID and p.RoundCount = :RoundCount")
    List<String> getAnswers(@Param("RoomID")Groom groom,@Param("RoundCount") int RoundCount);










    @Modifying
    @Transactional
    @Query("DELETE FROM PlayerAnswer p WHERE p.RoomID = :RoomID")
    void deleteByRoomID(@Param("RoomID") Groom RoomID);

    @Modifying
    @Transactional
    @Query("DELETE FROM PlayerAnswer p WHERE p.ParticipationId = :ParticipationId")
    void deleteByParticipationId(@Param("ParticipationId")Participation ParticipationId);



}
