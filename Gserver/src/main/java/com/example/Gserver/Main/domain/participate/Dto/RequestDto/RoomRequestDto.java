package com.example.Gserver.Main.domain.participate.Dto.RequestDto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RoomRequestDto {
    @NotNull
    String roomNumber;
}
