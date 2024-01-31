package com.gserver.domain.room.Dto.RequestDto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ParticipationRequestDto {
    @NotNull
    String roomId;

    @NotNull
    String nickName;
}
