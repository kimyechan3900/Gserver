package com.example.Gserver.Main.domain.game.Dto.RequestDto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParticipationAnswerDto {
    String roomNumber;
    Long playerId;
    int roundCount;
    String answer;
}
