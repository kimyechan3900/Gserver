package com.example.Gserver.Main.domain.game.Dto.ResponseDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class QuestionResponseDto {
    String roomId;

    String question;
}
