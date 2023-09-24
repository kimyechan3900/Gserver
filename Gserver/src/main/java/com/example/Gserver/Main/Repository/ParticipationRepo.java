package com.example.Gserver.Main.Repository;

import com.example.Gserver.Main.Model.Groom;
import com.example.Gserver.Main.Model.Participation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface ParticipationRepo extends JpaRepository<Participation,Long> {

    @Query("SELECT count(*) FROM Participation p WHERE p.RoomID = :RoomID")
    int countByRoomID(@Param("RoomID") String RoomID);

    @Query("SELECT COUNT(p) > 0 FROM Participation p WHERE p.RoomID = :RoomID AND p.NickName = :NickName")
    boolean existsByRoomIDAndNickName(@Param("RoomID") Groom RoomID, @Param("NickName") String NickName);

    @Query("SELECT COUNT(p) > 0 FROM Participation p WHERE p.RoomID = :RoomID AND p.NickName = :NickName AND p.RoomOwner = 0")
    boolean existsHostByRoomIDAndNickName(@Param("RoomID") Groom RoomID, @Param("NickName") String NickName);

    @Query("SELECT p FROM Participation p WHERE p.RoomID = :RoomID and p.NickName = :NickName")
    Participation getByRoomIDAndNickName(@Param("RoomID") Groom RoomID, @Param("NickName") String NickName);

    @Query("SELECT p.ParticipationID FROM Participation p WHERE p.RoomID = :RoomID ORDER BY ParticipationID")
    List<Integer> getOneParticipation(@Param("RoomID") Groom RoomID, Pageable pageable);

    @Query("SELECT p.It FROM Participation p WHERE p.ParticipationID = :ParticipationID")
    boolean CheckIt(@Param("ParticipationID") int ParticipationID);

    @Query("SELECT p FROM Participation p WHERE p.RoomID = :RoomID and p.RoomOwner = true")
    Participation getByRoomIDAndRoomOwner(@Param("RoomID")Groom RoomID);

    @Query("SELECT p.NickName FROM Participation p WHERE p.RoomID = :RoomID AND p.It = true")
    String getIt(@Param("RoomID") Groom RoomID);

    @Query("SELECT p.CorrectAnswer FROM Participation p WHERE p.RoomID = :RoomID and p.NickName = :NickName")
    int getCorrectAnswer(@Param("RoomID") Groom RoomID, @Param("NickName") String NickName);


    @Query("SELECT NickName FROM Participation p WHERE p.RoomID = :RoomID")
    List<String> getParticipation(@Param("RoomID") Groom RoomID);


    @Query("SELECT p FROM Participation p WHERE p.RoomID = :RoomID AND p.RoomOwner = false")
    List<Participation> getNextRoomHostList(@Param("RoomID") Groom RoomID);











    @Modifying
    @Transactional
    @Query("UPDATE Participation p SET p.CorrectAnswer = p.CorrectAnswer+1 WHERE p.RoomID = :RoomID AND p.NickName = :NickName")
    void plusCorrectAnswer(@Param("RoomID") Groom RoomID, @Param("NickName") String NickName);

    @Modifying
    @Transactional
    @Query("UPDATE Participation p SET p.It = 0 WHERE p.RoomID = :RoomID")
    void resetIt(@Param("RoomID") Groom RoomID);


    //p.ParticipationID = (SELECT ParticipationID FROM Participation ORDER BY ParticipationID LIMIT 1 )
    @Modifying
    @Transactional
    @Query("UPDATE Participation p SET p.It = true WHERE p.ParticipationID = :ParticipationID ")
    void TrueIt(@Param("ParticipationID") int ParticipationID);

    @Modifying
    @Transactional
    @Query("UPDATE Participation p SET p.It = false WHERE p.ParticipationID = :ParticipationID ")
    void FalseIt(@Param("ParticipationID") int ParticipationID);


    //List<Participation> findByRoomIDAndRoomOwnerOrderByParticipationIDAsc(Groom RoomID, boolean RoomOwner);

















    @Modifying
    @Transactional
    @Query("DELETE FROM Participation p WHERE p.RoomID = :RoomID")
    void deleteByRoomID(@Param("RoomID") Groom RoomID);

    @Modifying
    @Transactional
    @Query("DELETE FROM Participation p WHERE p.RoomID = :RoomID and p.NickName = :NickName")
    void deleteByRoomIDandNickName(@Param("RoomID") Groom RoomID , @Param("NickName") String NickName);

}

