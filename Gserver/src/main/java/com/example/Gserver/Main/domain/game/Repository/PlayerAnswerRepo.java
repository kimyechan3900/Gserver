package com.example.Gserver.Main.domain.game.Repository;

import com.example.Gserver.Main.domain.game.Model.PlayerAnswer;
import com.example.Gserver.Main.domain.participate.Model.Player;
import com.example.Gserver.Main.domain.room.Model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PlayerAnswerRepo extends JpaRepository<PlayerAnswer,Long> {



    PlayerAnswer findByPlayerAndAnswerRound(Player currentPlayer, int answerRound);
}
