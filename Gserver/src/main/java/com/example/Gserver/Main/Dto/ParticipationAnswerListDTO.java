package com.example.Gserver.Main.Dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParticipationAnswerListDTO {
    String roomNumber;
    String nickName;
    String[] nickNameList;
    String[] answerList;
}
