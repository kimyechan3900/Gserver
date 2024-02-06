package com.gserver.domain.game.Controller;

import com.gserver.domain.game.Dto.RequestDto.ParticipationAnswerDto;
import com.gserver.domain.game.Dto.RequestDto.ParticipationAnswerListDto;
import com.gserver.domain.game.Dto.RequestDto.RoomDto;
import com.gserver.domain.game.Dto.ResponseDto.AnswersResponseDto;
import com.gserver.domain.game.Dto.ResponseDto.CorrectResultResponseDto;
import com.gserver.domain.game.Dto.ResponseDto.ItResponseDto;
import com.gserver.domain.game.Service.GameService;
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



    @RequestMapping(value = "GetAnswers/{roomId}/{round}",method = RequestMethod.GET)
    @ApiOperation(value="참여자 응답들 가져오기", notes="방넘버(String), 라운드숫자(String)")
    public ResponseEntity<List<AnswersResponseDto>> GetAnswer(@PathVariable String roomId, @PathVariable int round){
        List<AnswersResponseDto> Answers = gameService.GetAnswers(roomId,round);

        return new ResponseEntity<>(Answers, HttpStatus.OK);
    }


    @RequestMapping(value = "/Result/{playerId}",method = RequestMethod.GET)
    @ApiOperation(value="맞춘 개수 구하기", notes="방넘버(String),본인닉네임(String)")
    public ResponseEntity<CorrectResultResponseDto> CorrectResult(@PathVariable Long playerId){
        CorrectResultResponseDto correctResultResponseDto = gameService.CorrectResult(playerId);

        return new ResponseEntity<>(correctResultResponseDto, HttpStatus.OK);
    }


    @RequestMapping(value = "CompleteAnswer",method = RequestMethod.POST)
    @ApiOperation(value="질문 답변 완료", notes="방넘버(String),닉네임(String),답변내용(String)")
    public void finishAnswer(@RequestBody ParticipationAnswerDto participationAnswerDTO){
        gameService.CompleteAnswer(participationAnswerDTO);
    }

    @RequestMapping(value = "/GuessPerson",method = RequestMethod.PATCH)
    @ApiOperation(value="질문 맞추기완료", notes="방넘버(String),본인닉네임(String),대상닉네임(String),대상 답변(String)")
    public ResponseEntity<CorrectResultResponseDto> GuessPerson(@RequestBody ParticipationAnswerListDto participationAnswerListDTO){
        CorrectResultResponseDto correctResultResponseDto = gameService.CompareAnswer(participationAnswerListDTO);

        return new ResponseEntity<>(correctResultResponseDto, HttpStatus.OK);
    }


    @RequestMapping(value = "ChangeIt",method = RequestMethod.PATCH)
    @ApiOperation(value="라운드 변환 (술래 체인지)", notes="방넘버(String)")
    public ResponseEntity<ItResponseDto> ChangeIt(@RequestBody RoomDto roomDTO){
        ItResponseDto itChangeDto = gameService.changeIt(roomDTO);

        return new ResponseEntity<>(itChangeDto, HttpStatus.OK);
    }


    @RequestMapping(value = "/Finish/{roomId}",method = RequestMethod.DELETE)
    @ApiOperation(value="게임 종료", notes="방넘버(String)")
    public void FinishGame(@PathVariable String roomId){
        gameService.FinishGame(roomId);

    }


}