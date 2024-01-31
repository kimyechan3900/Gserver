package com.gserver.domain.question.Controller;

import com.gserver.domain.game.Dto.ResponseDto.QuestionResponseDto;
import com.gserver.domain.question.Dto.Request.CustomQuestionDto;
import com.gserver.domain.question.Service.QuestionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/Question")
public class QuestionController {

    QuestionService questionService;

    public QuestionController(QuestionService questionService){
        this.questionService = questionService;
    }


    @RequestMapping(value = "RequestQuestion/{roomId}",method = RequestMethod.GET)
    @ApiOperation(value="질문 랜덤가져오기", notes="방넘버(String)")
    public ResponseEntity<QuestionResponseDto> RequestQuestion(@PathVariable String roomId){
        QuestionResponseDto questionResponseDto = questionService.GetQuestion(roomId);

        return new ResponseEntity<>(questionResponseDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/CreateCustomQuestion",method = RequestMethod.POST)
    @ApiOperation(value="커스텀질문생성", notes="방넘버(String),커스텀질문(String)")
    public void CreateCustomQuery(@RequestBody CustomQuestionDto customQuestionDTO){
        questionService.SaveCustomQuestion(customQuestionDTO);
    }


}