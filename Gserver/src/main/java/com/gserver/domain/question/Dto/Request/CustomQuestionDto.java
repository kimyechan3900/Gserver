package com.gserver.domain.question.Dto.Request;

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
