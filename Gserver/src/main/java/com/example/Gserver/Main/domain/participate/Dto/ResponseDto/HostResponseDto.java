package com.example.Gserver.Main.domain.participate.Dto.ResponseDto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class HostResponseDto {
    @NotNull
    String roomNumber;

    @NotNull
    List<String> nickName;
}
