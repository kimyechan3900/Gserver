package com.example.Gserver.Main.domain.question.Dto.Response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QuestionResponseDto {
    String roomId;

    String question;
}
