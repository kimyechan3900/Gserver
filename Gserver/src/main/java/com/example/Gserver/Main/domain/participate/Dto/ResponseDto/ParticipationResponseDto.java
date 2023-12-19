package com.example.Gserver.Main.domain.participate.Dto.ResponseDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ParticipationResponseDto {

    @NotNull
    int playerId;

    @NotNull
    String nickName;
}
