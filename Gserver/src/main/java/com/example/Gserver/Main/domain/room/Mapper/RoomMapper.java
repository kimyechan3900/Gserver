package com.example.Gserver.Main.domain.room.Mapper;


import com.example.Gserver.Main.domain.participate.Model.Player;
import com.example.Gserver.Main.domain.room.Dto.RequestDto.ParticipationRequestDto;
import com.example.Gserver.Main.domain.room.Dto.ResponseDto.ParticipationResponseDto;
import com.example.Gserver.Main.domain.room.Model.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface RoomMapper {

    @Mapping(target = "room", source = "roomNumber")
    @Mapping(target = "participationCount", constant = "1") //방장 참가
    @Mapping(target = "gameRepeat", constant = "0")
    @Mapping(target = "currentRound", constant = "0")
    @Mapping(target = "itCount", constant = "0")
    Room toRoom(ParticipationRequestDto participationRequestDTO); // 방생성 Mapper

    @Mapping(target = "room", source = "room")
    @Mapping(target = "nickName", source = "nickName")
    @Mapping(target = "roomOwner", source = "1")
    @Mapping(target = "correctAnswer", source = "0")
    @Mapping(target = "it", source = "0")
    Player toRoomManager(Room room , ParticipationRequestDto participationRequestDto); // 방장 참가 Mapper

    @Mapping(target = "room", source = "room")
    @Mapping(target = "nickName", source = "nickName")
    @Mapping(target = "roomOwner", source = "0")
    @Mapping(target = "correctAnswer", source = "0")
    @Mapping(target = "it", source = "0")
    Player toParticipation(Room room , ParticipationRequestDto participationRequestDto); // 참가자 참가 Mapper







    @Mapping(target = "roomNumber", source = "room")
    @Mapping(target = "nickName", source = "nickName")
    ParticipationResponseDto toParticipationResponse (Room room, Player PLAYER);




}
