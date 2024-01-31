package com.gserver.domain.question.Mapper;


import com.gserver.domain.game.Dto.ResponseDto.QuestionResponseDto;
import com.gserver.domain.question.Model.CustomQuestion;
import com.gserver.domain.question.Model.DefaultQuestion;
import com.gserver.domain.question.Dto.Request.CustomQuestionDto;
import com.gserver.domain.room.Model.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

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


}