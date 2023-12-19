package com.example.Gserver.Main.domain.game.Dto.ResponseDto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class QuestionResponseDto {
    @NotNull
    String roomNumber;

    @NotNull
    String question;
}
