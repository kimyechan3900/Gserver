package com.example.Gserver.Controller;

import com.example.Gserver.Service.CroomService;
import com.example.Gserver.Service.GroomService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
        System.out.println("헬로");
        String response = "ok";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/CreateRoom",method = RequestMethod.POST)
    @ApiOperation(value="방장 방생성", notes="방넘버(String),방장닉네임(String)")
    public ResponseEntity<String> CreateRoom(@RequestBody Map<String,Object> requestMap){
        System.out.println(requestMap);
        String roomNumber = (String) requestMap.get("roomNumber");
        String NickName = (String) requestMap.get("NickName");
        service.CreateRoom(roomNumber,NickName);
        String response = "ok";
        System.out.println("헬로");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "/ParticipateRoom",method = RequestMethod.POST)
    @ApiOperation(value="참가자 방참가", notes="방넘버(String),참가자닉네임(String)")
    public ResponseEntity<String> ParticipateRoom(@RequestBody Map<String,Object> requestMap){
        String roomNumber = (String)requestMap.get("roomNumber");
        String NickName = (String) requestMap.get("NickName");
        service.Participation(roomNumber,NickName);
        String response = "ok";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/GetParticipation",method = RequestMethod.POST)
    @ApiOperation(value="참가자 방참가", notes="방넘버(String)")
    public ResponseEntity<List<String>> GetParticipation(@RequestBody Map<String,Object> requestMap){
        String roomNumber = (String)requestMap.get("roomNumber");
        List<String> Answers = service.getParticipation(roomNumber);
        return new ResponseEntity<>(Answers, HttpStatus.OK);
    }

    @RequestMapping(value = "/GameStart",method = RequestMethod.POST)
    @ApiOperation(value="게임 시작", notes="방넘버(String),라운드개수(String)")
    public ResponseEntity<String> GameStart(@RequestBody Map<String,Object> requestMap){
        String roomNumber = (String)requestMap.get("roomNumber");
        int gameRepeatCount = (int) requestMap.get("gameRepeatCount");
        service.GameStart(roomNumber,gameRepeatCount);
        String response = "ok";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "RequestQuestion",method = RequestMethod.POST)
    @ApiOperation(value="질문 랜덤가져오기", notes="방넘버(String)")
    public ResponseEntity<String> RequestQuestion(@RequestBody Map<String,Object> requestMap){
        String roomNumber = (String)requestMap.get("roomNumber");
        String question = service.GetQuestion(roomNumber);
        return new ResponseEntity<>(question, HttpStatus.OK);
    }

    @RequestMapping(value = "CompleteAnswer",method = RequestMethod.POST)
    @ApiOperation(value="질문 답변 완료", notes="방넘버(String),닉네임(String),답변내용(String)")
    public ResponseEntity<String> finishAnswer(@RequestBody Map<String,Object> requestMap){
        String roomNumber = (String)requestMap.get("roomNumber");
        String NickName = (String) requestMap.get("NickName");
        String Answer = (String)requestMap.get("Answer");
        service.CompleteAnswer(roomNumber,NickName,Answer);
        String response = "ok";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "GetAnswers",method = RequestMethod.POST)
    @ApiOperation(value="참여자 질문들 가져오기", notes="방넘버(String), 라운드숫자(String)")
    public ResponseEntity<List<String>> GetAnswer(@RequestBody Map<String,Object> requestMap){
        String roomNumber = (String)requestMap.get("roomNumber");
        int CurrentCount = (int) requestMap.get("gameRepeatCount");
        List<String> Answers = service.GetAnswers(roomNumber,CurrentCount);
        return new ResponseEntity<>(Answers, HttpStatus.OK);
    }

    @RequestMapping(value = "GetIt",method = RequestMethod.POST)
    @ApiOperation(value="술래 가져오기", notes="방넘버(String)")
    public ResponseEntity<String> GetIt(@RequestBody Map<String,Object> requestMap){
        String roomNumber = (String)requestMap.get("roomNumber");
        String ItName = service.ChangeIt(roomNumber);
        return new ResponseEntity<>(ItName, HttpStatus.OK);
    }


    @RequestMapping(value = "/GuessPerson",method = RequestMethod.POST)
    @ApiOperation(value="질문 맞추기완료", notes="방넘버(String),본인닉네임(String),대상닉네임(String),대상 답변(String)")
    public ResponseEntity<Integer> GuessPerson(@RequestBody Map<String,Object> requestMap){
        ObjectMapper objectMapper = new ObjectMapper();

        String roomNumber = (String)requestMap.get("roomNumber");
        String NickName = (String) requestMap.get("NickName");
        String[] selectNickName = objectMapper.convertValue(requestMap.get("selectNickName"), String[].class);
        String[] selectAnswer =objectMapper.convertValue(requestMap.get("selectAnswer"), String[].class);
        int result = service.CompareAnswer(roomNumber,NickName,selectNickName, selectAnswer);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }



    @RequestMapping(value = "/Result",method = RequestMethod.POST)
    @ApiOperation(value="질문 검증 및 맞춘 개수 구하기", notes="방넘버(String),본인닉네임(String)")
    public ResponseEntity<Integer> Result(@RequestBody Map<String,Object> requestMap){
        String roomNumber = (String)requestMap.get("roomNumber");
        String NickName = (String) requestMap.get("NickName");
        int result = service.CorrectResult(roomNumber,NickName);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/Exit",method = RequestMethod.POST)
    @ApiOperation(value="플레이어 방 나가기", notes="방넘버(String),본인닉네임(String)")
    public ResponseEntity<String> Exit(@RequestBody Map<String,Object> requestMap){
        String roomNumber = (String)requestMap.get("roomNumber");
        String NickName = (String) requestMap.get("NickName");
        service.ExitPlayer(roomNumber,NickName);
        String response = "ok";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @RequestMapping(value = "/Finish",method = RequestMethod.POST)
    @ApiOperation(value="게임 종료", notes="방넘버(String)")
    public ResponseEntity<String> Finish(@RequestBody Map<String,Object> requestMap){
        String roomNumber = (String)requestMap.get("roomNumber");
        service.FinishGame(roomNumber);
        String response = "ok";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
