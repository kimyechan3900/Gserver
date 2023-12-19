package com.example.Gserver.Main.domain.game.Mapper;


import com.example.Gserver.Main.domain.game.Dto.RequestDto.CustomQuestionDto;
import com.example.Gserver.Main.domain.game.Dto.RequestDto.ItChangeDto;
import com.example.Gserver.Main.domain.game.Dto.RequestDto.ParticipationAnswerDto;
import com.example.Gserver.Main.domain.game.Dto.ResponseDto.CorrectResultResponseDto;
import com.example.Gserver.Main.domain.game.Dto.ResponseDto.QuestionResponseDto;
import com.example.Gserver.Main.domain.game.Model.CustomQuestion;
import com.example.Gserver.Main.domain.game.Model.PlayerAnswer;
import com.example.Gserver.Main.domain.participate.Dto.ResponseDto.ParticipationResponseDto;
import com.example.Gserver.Main.domain.participate.Model.Player;
import com.example.Gserver.Main.domain.room.Model.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface GameMapper {

    @Mapping(target = "player", source = "player")
    @Mapping(target = "roundCount", source = "participationAnswerDto.roundCount")
    @Mapping(target = "answer", source = "participationAnswerDto.answer")
    PlayerAnswer toPlaterAnswer (Player player, ParticipationAnswerDto participationAnswerDto);


    @Mapping(target = "room", source = "room")
    @Mapping(target = "customQuestion", source = "customQuestionDto.question")
    CustomQuestion toCustomQuestion (Room room, CustomQuestionDto customQuestionDto);




    @Mapping(target = "roomNumber", source = "room.roomNumber")
    @Mapping(target = "question", source = "question")
    QuestionResponseDto toQuestionResponse (Room room , String question);

    @Mapping(target = "roomNumber", source = "roomID")
    @Mapping(target = "nickName", source = "participation.nickName")
    ParticipationResponseDto toParticipationResponse (Room room);

    @Mapping(target = "correctAnswer", source = "correctAnswer")
    CorrectResultResponseDto toCorrectResultResponse (int correctAnswer);

    @Mapping(target = "playerId", source = "playerId")
    @Mapping(target = "nickName", source = "nickName")
    ItChangeDto toItChangeDto (Long playerId, String nickName);

}