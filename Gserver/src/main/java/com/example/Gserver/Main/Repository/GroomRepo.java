package com.example.Gserver.Main.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface GroomRepo extends JpaRepository<Groom, String> {


    @Query("SELECT g.ParticipationCount FROM Groom g WHERE g.RoomID = :RoomID")
    int getParticipationCount(@Param("RoomID")String RoomID);

    @Query("SELECT g.ItCount FROM Groom g WHERE g.RoomID = :RoomID")
    int getItCount(@Param("RoomID")String RoomID);

    @Query("SELECT g.CurrentRound FROM Groom g WHERE g.RoomID = :RoomID")
    int getCurrentRound(@Param("RoomID")String RoomID);











    @Modifying
    @Transactional
    @Query("UPDATE Groom g SET g.ParticipationCount = g.ParticipationCount + 1 WHERE g.RoomID = :RoomID")
    void plusParticipantCountById(@Param("RoomID")String RoomID);

    @Modifying
    @Transactional
    @Query("UPDATE Groom g SET g.ParticipationCount = g.ParticipationCount - 1 WHERE g.RoomID = :RoomID")
    void minusParticipantCountById(@Param("RoomID")String RoomID);

    @Modifying
    @Transactional
    @Query("UPDATE Groom g SET g.CurrentRound = g.CurrentRound + 1 WHERE g.RoomID = :RoomID")
    void plusRoundById(@Param("RoomID")String RoomID);

    @Modifying
    @Transactional
    @Query("UPDATE Groom g SET g.ItCount = g.ItCount + 1 WHERE g.RoomID = :RoomID")
    void plusItCountById(@Param("RoomID")String RoomID);



    @Modifying
    @Transactional
    @Query("UPDATE Groom g SET g.ItCount = 0 WHERE g.RoomID = :RoomID")
    void resetItCount(@Param("RoomID")String RoomID);

    @Modifying
    @Transactional
    @Query("UPDATE Groom g SET g.GameRepeat = :GameRepeatCount WHERE g.RoomID = :RoomID")
    void SetGameRepeatCount(@Param("RoomID")String RoomID,@Param("GameRepeatCount")int gameRepeatCount);












    @Modifying
    @Transactional
    @Query("DELETE FROM Groom g WHERE g.RoomID = :RoomID")
    void deleteByRoomID(@Param("RoomID") String RoomID);
}
