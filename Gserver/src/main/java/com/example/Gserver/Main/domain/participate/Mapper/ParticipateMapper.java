package com.example.Gserver.Main.domain.participate.Mapper;


import com.example.Gserver.Main.domain.participate.Dto.ResponseDto.ItResponseDto;
import com.example.Gserver.Main.domain.participate.Dto.ResponseDto.HostResponseDto;
import com.example.Gserver.Main.domain.participate.Dto.ResponseDto.ParticipationResponseDto;
import com.example.Gserver.Main.domain.participate.Model.Player;
import com.example.Gserver.Main.domain.room.Dto.RequestDto.ParticipationRequestDto;
import com.example.Gserver.Main.domain.room.Model.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ParticipateMapper {

    @Mapping(target = "roomId", source = "roomId")
    @Mapping(target = "playerCount", constant = "1") //방장 참가
    @Mapping(target = "gameRepeat", constant = "0")
    @Mapping(target = "currentRound", constant = "0")
    Room toRoom(ParticipationRequestDto participationRequestDTO); // 방생성 Mapper






    @Mapping(target = "playerId", source = "playerId")
    @Mapping(target = "nickName", source = "nickName")
    HostResponseDto toHostResponse (Player player);


    default List<ParticipationResponseDto> toParticipationResponse(List<Player> players) {
        return players.stream()
                .map(player -> new ParticipationResponseDto(player.getPlayerId(), player.getNickName()))
                .collect(Collectors.toList());
    }


    @Mapping(target = "playerId", source = "player.playerId")
    @Mapping(target = "nickName", source = "player.nickName")
    ItResponseDto toItResponseDto (Player player);



}