package com.example.Gserver.Main.Dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RoomDTO {
    @NotNull
    String roomNumber;
}
