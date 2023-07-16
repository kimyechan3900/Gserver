package com.example.Gserver.Repository;

import com.example.Gserver.DBdomain.Groom;
import com.example.Gserver.DBdomain.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ParticipationRepo extends JpaRepository<Participation,Long> {

    @Query("SELECT count(*) FROM Participation p WHERE p.RoomID = :RoomID")
    int countByRoomID(@Param("RoomID") String RoomID);

    @Query("SELECT CASE WHEN EXISTS (SELECT p FROM Participation p WHERE p.RoomID = :RoomID AND p.NickName = :NickName) THEN true ELSE false END FROM Participation p")
    boolean existsByRoomIDAndNickName(@Param("RoomID") Groom RoomID, @Param("NickName") String NickName);

    @Query("SELECT p FROM Participation p WHERE p.RoomID = :RoomID and p.NickName = :NickName")
    Participation getByRoomIDAndNickName(@Param("RoomID") Groom RoomID, @Param("NickName") String NickName);

    @Modifying
    @Query("UPDATE Participation p SET p.CorrectAnswer = p.CorrectAnswer+1 WHERE p.RoomID = :RoomID AND p.NickName = :NickName")
    void plusCorrectAnswer(@Param("RoomID") Groom RoomID, @Param("NickName") String NickName);

    @Query("SELECT p.CorrectAnswer FROM Participation p WHERE p.RoomID = :RoomID and p.NickName = :NickName")
    int getCorrectAnswer(@Param("RoomID") Groom RoomID, @Param("NickName") String NickName);

}

