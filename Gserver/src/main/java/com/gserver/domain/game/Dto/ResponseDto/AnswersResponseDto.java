package com.gserver.domain.game.Dto.ResponseDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnswersResponseDto {
    String nickName;

    @NotNull
    String question;
}
