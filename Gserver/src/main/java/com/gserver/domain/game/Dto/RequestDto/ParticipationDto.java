package com.gserver.domain.game.Dto.RequestDto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ParticipationDto {
    @NotNull
    String roomId;

    @NotNull
    int playerId;
}
