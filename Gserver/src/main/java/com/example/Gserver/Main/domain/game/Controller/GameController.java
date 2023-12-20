package com.example.Gserver.Main.domain.game.Controller;

import com.example.Gserver.Error.CustomException;
import com.example.Gserver.Error.ErrorCode;
import com.example.Gserver.Main.domain.game.Dto.RequestDto.*;
import com.example.Gserver.Main.domain.game.Dto.ResponseDto.AnswersResponseDto;
import com.example.Gserver.Main.domain.game.Dto.ResponseDto.CorrectResultResponseDto;
import com.example.Gserver.Main.domain.game.Dto.ResponseDto.ItResponseDto;
import com.example.Gserver.Main.domain.game.Dto.ResponseDto.QuestionResponseDto;
import com.example.Gserver.Main.domain.game.Service.GameService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/Game")
public class GameController {

    GameService gameService;

    public GameController(GameService gameService){
        this.gameService = gameService;
    }


    @RequestMapping(value = "RequestQuestion",method = RequestMethod.POST)
    @ApiOperation(value="질문 랜덤가져오기", notes="방넘버(String)")
    public ResponseEntity<QuestionResponseDto> RequestQuestion(@RequestBody RoomRequestDto roomRequestDto){
        QuestionResponseDto questionResponseDto = gameService.GetQuestion(roomRequestDto);

        return new ResponseEntity<>(questionResponseDto, HttpStatus.OK);
    }

    @RequestMapping(value = "CompleteAnswer",method = RequestMethod.POST)
    @ApiOperation(value="질문 답변 완료", notes="방넘버(String),닉네임(String),답변내용(String)")
    public void finishAnswer(@RequestBody ParticipationAnswerDto participationAnswerDTO){
        gameService.CompleteAnswer(participationAnswerDTO);
    }

    @RequestMapping(value = "GetAnswers",method = RequestMethod.POST)
    @ApiOperation(value="참여자 응답들 가져오기", notes="방넘버(String), 라운드숫자(String)")
    public ResponseEntity<List<AnswersResponseDto>> GetAnswer(@RequestBody RoundDto roundDTO){
        List<AnswersResponseDto> Answers = gameService.GetAnswers(roundDTO);

        return new ResponseEntity<>(Answers, HttpStatus.OK);
    }

    @RequestMapping(value = "ChangeIt",method = RequestMethod.POST)
    @ApiOperation(value="술래 체인지", notes="방넘버(String)")
    public ResponseEntity<ItResponseDto> ChangeIt(@RequestBody RoomDto roomDTO){
        ItResponseDto itChangeDto = gameService.changeIt(roomDTO);

        return new ResponseEntity<>(itChangeDto, HttpStatus.OK);
    }


    @RequestMapping(value = "/GuessPerson",method = RequestMethod.POST)
    @ApiOperation(value="질문 맞추기완료", notes="방넘버(String),본인닉네임(String),대상닉네임(String),대상 답변(String)")
    public ResponseEntity<CorrectResultResponseDto> GuessPerson(@RequestBody ParticipationAnswerListDto participationAnswerListDTO){
        CorrectResultResponseDto correctResultResponseDto = gameService.CompareAnswer(participationAnswerListDTO);

        return new ResponseEntity<>(correctResultResponseDto, HttpStatus.OK);
    }


    @RequestMapping(value = "/Result",method = RequestMethod.POST)
    @ApiOperation(value="질문 검증 및 맞춘 개수 구하기", notes="방넘버(String),본인닉네임(String)")
    public ResponseEntity<CorrectResultResponseDto> CorrectResult(@RequestBody ParticipationDto participationDTO){
        CorrectResultResponseDto correctResultResponseDto = gameService.CorrectResult(participationDTO);

        return new ResponseEntity<>(correctResultResponseDto, HttpStatus.OK);
    }


    @RequestMapping(value = "/Finish",method = RequestMethod.POST)
    @ApiOperation(value="게임 종료", notes="방넘버(String)")
    public void FinishGame(@RequestBody RoomDto roomDTO){
        gameService.FinishGame(roomDTO);
    }


    @RequestMapping(value = "/CreateCustomQuery",method = RequestMethod.POST)
    @ApiOperation(value="커스텀질문생성", notes="방넘버(String),커스텀질문(String)")
    public void CreateCustomQuery(@RequestBody CustomQuestionDto customQuestionDTO){
        gameService.SaveCustomQuestion(customQuestionDTO);
    }


}