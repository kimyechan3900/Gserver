package com.example.Gserver.Controller;

import com.example.Gserver.Service.CroomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/Room")
public class CroomController {

    CroomService service;

    public CroomController(CroomService service){
        this.service = service;
    }

    @GetMapping("/")
    public String home() {
        System.out.println("헬로");
        return "home";
    }

    @RequestMapping(value = "/CreateRoom",method = RequestMethod.POST)
    @ApiOperation(value="방장 방생성", notes="방넘버(String),방장닉네임(String)")
    public ResponseEntity<String> CreateRoom(@RequestBody Map<String,Object> requestMap){
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

    @RequestMapping(value = "/GameStart",method = RequestMethod.POST)
    @ApiOperation(value="게임 시작", notes="방넘버(String),질문개수(String)")
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

    @RequestMapping(value = "finishAnswer",method = RequestMethod.POST)
    @ApiOperation(value="질문 답변 완료", notes="방넘버(String),닉네임(String),답변내용(String)")
    public ResponseEntity<String> finishAnswer(@RequestBody Map<String,Object> requestMap){
        String roomNumber = (String)requestMap.get("roomNumber");
        String NickName = (String) requestMap.get("NickName");
        String Answer = (String)requestMap.get("Answer");
        service.completeAnswer(roomNumber,NickName,Answer);
        String response = "ok";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/GuessPerson",method = RequestMethod.POST)
    @ApiOperation(value="질문 맞추기완료", notes="방넘버(String),본인닉네임(String),대상닉네임(String),대상 답변(String)")
    public ResponseEntity<Integer> GuessPerson(@RequestBody Map<String,Object> requestMap){
        String roomNumber = (String)requestMap.get("roomNumber");
        String NickName = (String) requestMap.get("NickName");
        String[] selectNickName = (String[])requestMap.get("selectNickName");
        String[] selectAnswer = (String[])requestMap.get("selectAnswer");
        int result = service.CompareAnswer(roomNumber,NickName,selectNickName,selectAnswer);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }





    @RequestMapping(value = "/Result",method = RequestMethod.POST)
    @ApiOperation(value="질문 검증 및 맞춘 개수 구하기", notes="방넘버(String),본인닉네임(String)")
    public ResponseEntity<Integer> Result(@RequestBody Map<String,Object> requestMap){
        String roomNumber = (String)requestMap.get("roomNumber");
        String NickName = (String) requestMap.get("NickName");
        Integer result = service.CorrectResult(roomNumber,NickName);
        return new ResponseEntity<>(result, HttpStatus.OK);
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
