package com.gserver.domain.game.Repository;

import com.gserver.domain.game.Model.PlayerAnswer;
import com.gserver.domain.participate.Model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerAnswerRepo extends JpaRepository<PlayerAnswer,Long> {



    PlayerAnswer findByPlayerAndAnswerRound(Player currentPlayer, int answerRound);
}
