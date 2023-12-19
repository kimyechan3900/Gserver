package com.example.Gserver.Main.domain.game.Mapper;


import com.example.Gserver.Main.domain.game.Dto.RequestDto.CustomQuestionDto;
import com.example.Gserver.Main.domain.game.Dto.RequestDto.ParticipationAnswerDto;
import com.example.Gserver.Main.domain.game.Dto.ResponseDto.CorrectResultResponseDto;
import com.example.Gserver.Main.domain.game.Dto.ResponseDto.ItResponseDto;
import com.example.Gserver.Main.domain.game.Dto.ResponseDto.QuestionResponseDto;
import com.example.Gserver.Main.domain.game.Model.CustomQuestion;
import com.example.Gserver.Main.domain.game.Model.DefaultQuestion;
import com.example.Gserver.Main.domain.game.Model.PlayerAnswer;
import com.example.Gserver.Main.domain.participate.Model.Player;
import com.example.Gserver.Main.domain.room.Model.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface GameMapper {

    @Mapping(target = "player", source = "player")
    @Mapping(target = "answerRound", source = "participationAnswerDto.roundCount")
    @Mapping(target = "answer", source = "participationAnswerDto.answer")
    PlayerAnswer toPlayerAnswer (Player player, ParticipationAnswerDto participationAnswerDto);


    @Mappings({
            @Mapping(target = "room", source = "room"),
            @Mapping(target = "customQuestion", source = "customQuestionDto.question")
    })
    CustomQuestion toCustomQuestion(Room room,CustomQuestionDto customQuestionDto);





    @Mappings({
            @Mapping(target = "roomId", source = "room.roomId"),
            @Mapping(target = "question", source = "defaultQuestion.defaultQuestion")
    })
    QuestionResponseDto toQuestionResponse (Room room , DefaultQuestion defaultQuestion);


    QuestionResponseDto toQuestionResponse(Room room, String question);

    @Mapping(target = "correctAnswer", source = "correctAnswer")
    CorrectResultResponseDto toCorrectResultResponse (Integer correctAnswer);


    @Mapping(target = "playerId", source = "player.playerId")
    @Mapping(target = "nickName", source = "player.nickName")
    ItResponseDto toItResponseDto (Player player);

}