package com.example.Gserver.Main.domain.question.Controller;

import com.example.Gserver.Main.domain.game.Dto.RequestDto.*;
import com.example.Gserver.Main.domain.game.Dto.ResponseDto.QuestionResponseDto;
import com.example.Gserver.Main.domain.game.Service.GameService;
import com.example.Gserver.Main.domain.question.Dto.Request.CustomQuestionDto;
import com.example.Gserver.Main.domain.question.Service.QuestionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/Question")
public class QuestionController {

    QuestionService questionService;

    public QuestionController(QuestionService questionService){
        this.questionService = questionService;
    }


    @RequestMapping(value = "RequestQuestion",method = RequestMethod.POST)
    @ApiOperation(value="질문 랜덤가져오기", notes="방넘버(String)")
    public ResponseEntity<QuestionResponseDto> RequestQuestion(@RequestBody RoomRequestDto roomRequestDto){
        QuestionResponseDto questionResponseDto = questionService.GetQuestion(roomRequestDto);

        return new ResponseEntity<>(questionResponseDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/CreateCustomQuestion",method = RequestMethod.POST)
    @ApiOperation(value="커스텀질문생성", notes="방넘버(String),커스텀질문(String)")
    public void CreateCustomQuery(@RequestBody CustomQuestionDto customQuestionDTO){
        questionService.SaveCustomQuestion(customQuestionDTO);
    }


}