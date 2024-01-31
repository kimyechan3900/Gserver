package com.gserver.domain.participate.Dto.ResponseDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ParticipationResponseDto {

    Long playerId;

    String nickName;

}
