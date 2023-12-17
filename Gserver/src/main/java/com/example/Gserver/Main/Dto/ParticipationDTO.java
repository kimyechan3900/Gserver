package com.example.Gserver.Main.Dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ParticipationDTO {
    @NotNull
    String roomNumber;

    @NotNull
    String nickName;
}
