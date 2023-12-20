package com.example.Gserver.Main.domain.question.Dto.Request;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RoomRequestDto {
    @NotNull
    String roomId;
}
