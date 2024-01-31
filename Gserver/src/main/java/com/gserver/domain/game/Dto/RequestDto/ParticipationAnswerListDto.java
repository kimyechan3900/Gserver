package com.gserver.domain.game.Dto.RequestDto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParticipationAnswerListDto {
    String roomId;
    Long playerId;
    int answerRound;
    Long[] playerIdList;
    String[] answerList;
}
