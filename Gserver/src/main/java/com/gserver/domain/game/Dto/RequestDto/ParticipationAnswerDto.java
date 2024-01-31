package com.gserver.domain.game.Dto.RequestDto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ParticipationAnswerDto {
    String roomId;
    Long playerId;
    int roundCount;
    String answer;
}
