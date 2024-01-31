package com.gserver.domain.room.Mapper;


import com.gserver.domain.participate.Model.Player;
import com.gserver.domain.room.Dto.RequestDto.ParticipationRequestDto;
import com.gserver.domain.room.Dto.ResponseDto.ParticipationResponseDto;
import com.gserver.domain.room.Model.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    @Mapping(target = "roomId", source = "roomId")
    @Mapping(target = "playerCount", constant = "1") //방장 참가
    @Mapping(target = "gameRepeat", constant = "0")
    @Mapping(target = "currentRound", constant = "0")
    Room toRoom(ParticipationRequestDto participationRequestDTO); // 방생성 Mapper

    @Mapping(target = "room", source = "room")
    @Mapping(target = "nickName", source = "participationRequestDto.nickName")
    @Mapping(target = "roomOwner", constant = "true")
    @Mapping(target = "correctAnswer", constant = "0")
    @Mapping(target = "it", constant = "false")
    Player toRoomManager(Room room , ParticipationRequestDto participationRequestDto); // 방장 참가 Mapper

    @Mapping(target = "room", source = "room")
    @Mapping(target = "nickName", source = "participationRequestDto.nickName")
    @Mapping(target = "roomOwner", constant = "false")
    @Mapping(target = "correctAnswer", constant = "0")
    @Mapping(target = "it", constant = "false")
    Player toParticipation(Room room , ParticipationRequestDto participationRequestDto); // 참가자 참가 Mapper







    @Mapping(target = "roomId", source = "room.roomId")
    @Mapping(target = "playerId", source = "player.playerId")
    @Mapping(target = "nickName", source = "player.nickName")
    ParticipationResponseDto toParticipationResponse (Room room, Player player);




}
