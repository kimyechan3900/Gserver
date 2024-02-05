package com.gserver.domain.game.Mapper;


import com.gserver.domain.question.Dto.Request.CustomQuestionDto;
import com.gserver.domain.game.Dto.RequestDto.ParticipationAnswerDto;
import com.gserver.domain.game.Dto.ResponseDto.CorrectResultResponseDto;
import com.gserver.domain.game.Dto.ResponseDto.ItResponseDto;
import com.gserver.domain.game.Dto.ResponseDto.QuestionResponseDto;
import com.gserver.domain.question.Model.CustomQuestion;
import com.gserver.domain.question.Model.DefaultQuestion;
import com.gserver.domain.game.Model.PlayerAnswer;
import com.gserver.domain.participate.Model.Player;
import com.gserver.domain.room.Model.Room;
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
    CustomQuestion toCustomQuestion(Room room, CustomQuestionDto customQuestionDto);




    @Mappings({
            @Mapping(target = "roomId", source = "room.roomId"),
            @Mapping(target = "question", source = "defaultQuestion.defaultQuestion")
    })
    QuestionResponseDto toQuestionResponse (Room room , DefaultQuestion defaultQuestion);

    @Mapping(target = "correctAnswer", source = "correctAnswer")
    CorrectResultResponseDto toCorrectResultResponse (Integer correctAnswer);

    @Mapping(target = "playerId", source = "player.playerId")
    @Mapping(target = "nickName", source = "player.nickName")
    ItResponseDto toItResponseDto (Player player);

}