package com.example.Gserver.Main.Controller;

import com.example.Gserver.Error.CustomException;
import com.example.Gserver.Error.ErrorCode;
import com.example.Gserver.Main.Dto.*;
import com.example.Gserver.Main.Model.Participation;
import com.example.Gserver.Main.Service.GroomService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/Room")
public class CroomController {

    GroomService service;

    public CroomController(GroomService service){
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<String> home() {
        String response = "ok";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/CreateRoom",method = RequestMethod.POST)
    @ApiOperation(value="방장 방생성", notes="방넘버(String),방장닉네임(String)")
    public ResponseEntity<String> CreateRoom(@RequestBody ParticipationDTO participationDTO){
        if(participationDTO.getRoomNumber() == null || participationDTO.getNickName() == null)
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        System.out.println(participationDTO.getRoomNumber() +" " + participationDTO.getNickName()+"  Controller");
        service.CreateRoom(participationDTO);
        String response = "ok";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "/ParticipateRoom",method = RequestMethod.POST)
    @ApiOperation(value="참가자 방참가", notes="방넘버(String),참가자닉네임(String)")
    public ResponseEntity<String> ParticipateRoom(@RequestBody ParticipationDTO participationDTO){
        if(participationDTO.getRoomNumber() == null || participationDTO.getNickName() == null)
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        service.Participation(participationDTO);
        String response = "ok";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/SearchHost",method = RequestMethod.POST)
    @ApiOperation(value="방장가져오기", notes="방넘버(String)")
    public ResponseEntity<Participation> SearchHost(@RequestBody RoomDTO roomDTO){
        Participation participation = service.SearchHost(roomDTO);
        if(roomDTO.getRoomNumber() == null)
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        return new ResponseEntity<>(participation, HttpStatus.OK);
    }

    @RequestMapping(value = "/GetParticipation",method = RequestMethod.POST)
    @ApiOperation(value="참가자 출력", notes="방넘버(String)")
    public ResponseEntity<List<String>> GetParticipation(@RequestBody RoomDTO roomDTO){
        if(roomDTO.getRoomNumber() == null)
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        List<String> Answers = service.getParticipation(roomDTO);
        return new ResponseEntity<>(Answers, HttpStatus.OK);
    }

    @RequestMapping(value = "/GameStart",method = RequestMethod.POST)
    @ApiOperation(value="게임 시작", notes="방넘버(String),라운드개수(String)")
    public ResponseEntity<String> GameStart(@RequestBody RoundDTO roundDTO){
        if(roundDTO.getRoomNumber() == null || roundDTO.getRound() == 0)
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        service.GameStart(roundDTO);
        String response = "ok";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "RequestQuestion",method = RequestMethod.POST)
    @ApiOperation(value="질문 랜덤가져오기", notes="방넘버(String)")
    public ResponseEntity<String> RequestQuestion(@RequestBody RoomDTO roomDTO){
        if(roomDTO.getRoomNumber() == null)
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        String question = service.GetQuestion(roomDTO);
        return new ResponseEntity<>(question, HttpStatus.OK);
    }

    @RequestMapping(value = "CompleteAnswer",method = RequestMethod.POST)
    @ApiOperation(value="질문 답변 완료", notes="방넘버(String),닉네임(String),답변내용(String)")
    public ResponseEntity<String> finishAnswer(@RequestBody ParticipationAnswerDTO participationAnswerDTO){
        if(participationAnswerDTO.getRoomNumber() == null || participationAnswerDTO.getNickName() == null || participationAnswerDTO.getAnswer() == null)
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        service.CompleteAnswer(participationAnswerDTO);
        String response = "ok";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "GetAnswers",method = RequestMethod.POST)
    @ApiOperation(value="참여자 질문들 가져오기", notes="방넘버(String), 라운드숫자(String)")
    public ResponseEntity<List<String>> GetAnswer(@RequestBody RoundDTO roundDTO){
        if(roundDTO.getRoomNumber() == null || roundDTO.getRound() == 0)
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        List<String> Answers = service.GetAnswers(roundDTO);
        return new ResponseEntity<>(Answers, HttpStatus.OK);
    }

    @RequestMapping(value = "ChangeIt",method = RequestMethod.POST)
    @ApiOperation(value="술래 체인지", notes="방넘버(String)")
    public ResponseEntity<String> ChangeIt(@RequestBody RoomDTO roomDTO){
        if(roomDTO.getRoomNumber() == null )
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        String ItName = service.ChangeIt(roomDTO);
        return new ResponseEntity<>(ItName, HttpStatus.OK);
    }

    @RequestMapping(value = "GetIt",method = RequestMethod.POST)
    @ApiOperation(value="술래 가져오기", notes="방넘버(String)")
    public ResponseEntity<String> GetIt(@RequestBody RoomDTO roomDTO){
        if(roomDTO.getRoomNumber() == null )
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        String ItName = service.GetIt(roomDTO);
        return new ResponseEntity<>(ItName, HttpStatus.OK);
    }

    @RequestMapping(value = "/GuessPerson",method = RequestMethod.POST)
    @ApiOperation(value="질문 맞추기완료", notes="방넘버(String),본인닉네임(String),대상닉네임(String),대상 답변(String)")
    public ResponseEntity<Integer> GuessPerson(@RequestBody ParticipationAnswerListDTO participationAnswerListDTO){
        ObjectMapper objectMapper = new ObjectMapper();

        /*String roomNumber = (String)requestMap.get("roomNumber");
        String NickName = (String) requestMap.get("NickName");
        String[] selectNickName = objectMapper.convertValue(requestMap.get("selectNickName"), String[].class);
        String[] selectAnswer =objectMapper.convertValue(requestMap.get("selectAnswer"), String[].class);*/
        if(participationAnswerListDTO.getRoomNumber()== null || participationAnswerListDTO.getNickName() == null || participationAnswerListDTO.getNickNameList() == null || participationAnswerListDTO.getAnswerList() == null)
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        int result = service.CompareAnswer(participationAnswerListDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @RequestMapping(value = "/Result",method = RequestMethod.POST)
    @ApiOperation(value="질문 검증 및 맞춘 개수 구하기", notes="방넘버(String),본인닉네임(String)")
    public ResponseEntity<Integer> Result(@RequestBody ParticipationDTO participationDTO){
        if(participationDTO.getRoomNumber() == null || participationDTO.getNickName() == null)
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        int result = service.CorrectResult(participationDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/Exit",method = RequestMethod.POST)
    @ApiOperation(value="플레이어 방 나가기", notes="방넘버(String),본인닉네임(String)")
    public ResponseEntity<String> Exit(@RequestBody ParticipationDTO participationDTO){
        if(participationDTO.getRoomNumber() == null || participationDTO.getNickName() == null)
           throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        service.ExitPlayer(participationDTO);
        String response = "ok";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @RequestMapping(value = "/Finish",method = RequestMethod.POST)
    @ApiOperation(value="게임 종료", notes="방넘버(String)")
    public ResponseEntity<String> Finish(@RequestBody RoomDTO roomDTO){
        if(roomDTO.getRoomNumber() == null )
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        service.FinishGame(roomDTO);
        String response = "ok";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @RequestMapping(value = "/CreateCustomQuery",method = RequestMethod.POST)
    @ApiOperation(value="커스텀질문생성", notes="방넘버(String),커스텀질문(String)")
    public ResponseEntity<String> CreateCustomQuery(@RequestBody CustomQueryDTO customQueryDTO){
        if(customQueryDTO.getRoomNumber() == null || customQueryDTO.getQuestion() == null)
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        service.SaveCustomQuestion(customQueryDTO);
        String response = "ok";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}