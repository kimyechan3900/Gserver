package com.example.Gserver.Main.domain.game.Dto.RequestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter

@AllArgsConstructor
public class CustomQuestionDto {
    @NotNull
    String roomId;

    @NotNull
    String question;
}
