package com.gserver.domain.room.Dto.ResponseDto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ParticipationResponseDto {
    String roomId;

    Long playerId;

    String nickName;
}
