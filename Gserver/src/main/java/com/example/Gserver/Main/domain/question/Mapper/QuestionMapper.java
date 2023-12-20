package com.example.Gserver.Main.domain.question.Mapper;


import com.example.Gserver.Main.domain.game.Dto.RequestDto.ParticipationAnswerDto;
import com.example.Gserver.Main.domain.game.Dto.ResponseDto.CorrectResultResponseDto;
import com.example.Gserver.Main.domain.game.Dto.ResponseDto.ItResponseDto;
import com.example.Gserver.Main.domain.game.Dto.ResponseDto.QuestionResponseDto;
import com.example.Gserver.Main.domain.question.Model.CustomQuestion;
import com.example.Gserver.Main.domain.question.Model.DefaultQuestion;
import com.example.Gserver.Main.domain.game.Model.PlayerAnswer;
import com.example.Gserver.Main.domain.participate.Model.Player;
import com.example.Gserver.Main.domain.question.Dto.Request.CustomQuestionDto;
import com.example.Gserver.Main.domain.room.Model.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

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


}