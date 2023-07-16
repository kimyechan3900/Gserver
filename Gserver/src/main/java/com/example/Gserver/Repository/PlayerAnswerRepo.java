package com.example.Gserver.Repository;

import com.example.Gserver.DBdomain.Groom;
import com.example.Gserver.DBdomain.Participation;
import com.example.Gserver.DBdomain.PlayerAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerAnswerRepo extends JpaRepository<PlayerAnswer,Long> {

    @Query("SELECT count(*) FROM PlayerAnswer p WHERE p.ParticipationId = :ParticipationId")
    int countByParticipationAnswer(@Param("ParticipationId")Participation ParticipationId);
    @Query("SELECT Answer FROM PlayerAnswer p WHERE p.ParticipationId = :ParticipationId and p.RoundCount = :RoundCount")
    String getByParticipationIDAndRoundCount(@Param("ParticipationId")Participation ParticipationId,@Param("RoundCount") int RoundCount);


}
