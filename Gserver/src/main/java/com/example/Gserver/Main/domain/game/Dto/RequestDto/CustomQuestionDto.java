package com.example.Gserver.Main.domain.game.Dto.RequestDto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CustomQuestionDto {
    @NotNull
    String roomNumber;

    @NotNull
    String question;
}
