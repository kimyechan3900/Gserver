package com.example.Gserver.Main.Controller;

import com.example.Gserver.Error.CustomException;
import com.example.Gserver.Error.ErrorCode;
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
        if(roomNumber == null || NickName == null)
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
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
        if(roomNumber == null || NickName == null)
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        service.Participation(roomNumber,NickName);
        String response = "ok";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/SearchHost",method = RequestMethod.POST)
    @ApiOperation(value="방장가져오기", notes="방넘버(String)")
    public ResponseEntity<Participation> SearchHost(@RequestBody Map<String,Object> requestMap){
        String roomNumber = (String)requestMap.get("roomNumber");
        Participation participation = service.SearchHost(roomNumber);
        if(roomNumber == null)
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        return new ResponseEntity<>(participation, HttpStatus.OK);
    }

    @RequestMapping(value = "/GetParticipation",method = RequestMethod.POST)
    @ApiOperation(value="참가자 출력", notes="방넘버(String)")
    public ResponseEntity<List<String>> GetParticipation(@RequestBody Map<String,Object> requestMap){
        String roomNumber = (String)requestMap.get("roomNumber");
        if(roomNumber == null)
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        List<String> Answers = service.getParticipation(roomNumber);
        return new ResponseEntity<>(Answers, HttpStatus.OK);
    }

    @RequestMapping(value = "/GameStart",method = RequestMethod.POST)
    @ApiOperation(value="게임 시작", notes="방넘버(String),라운드개수(String)")
    public ResponseEntity<String> GameStart(@RequestBody Map<String,Object> requestMap){
        String roomNumber = (String)requestMap.get("roomNumber");
        int gameRepeatCount = (int) requestMap.get("gameRepeatCount");
        if(roomNumber == null || gameRepeatCount == 0)
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        service.GameStart(roomNumber,gameRepeatCount);
        String response = "ok";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "RequestQuestion",method = RequestMethod.POST)
    @ApiOperation(value="질문 랜덤가져오기", notes="방넘버(String)")
    public ResponseEntity<String> RequestQuestion(@RequestBody Map<String,Object> requestMap){
        String roomNumber = (String)requestMap.get("roomNumber");
        if(roomNumber == null)
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        String question = service.GetQuestion(roomNumber);
        return new ResponseEntity<>(question, HttpStatus.OK);
    }

    @RequestMapping(value = "CompleteAnswer",method = RequestMethod.POST)
    @ApiOperation(value="질문 답변 완료", notes="방넘버(String),닉네임(String),답변내용(String)")
    public ResponseEntity<String> finishAnswer(@RequestBody Map<String,Object> requestMap){
        String roomNumber = (String)requestMap.get("roomNumber");
        String NickName = (String) requestMap.get("NickName");
        String Answer = (String)requestMap.get("Answer");
        if(roomNumber == null || NickName == null || Answer == null)
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        service.CompleteAnswer(roomNumber,NickName,Answer);
        String response = "ok";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "GetAnswers",method = RequestMethod.POST)
    @ApiOperation(value="참여자 질문들 가져오기", notes="방넘버(String), 라운드숫자(String)")
    public ResponseEntity<List<String>> GetAnswer(@RequestBody Map<String,Object> requestMap){
        String roomNumber = (String)requestMap.get("roomNumber");
        int CurrentCount = (int) requestMap.get("gameRepeatCount");
        if(roomNumber == null || CurrentCount == 0)
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        List<String> Answers = service.GetAnswers(roomNumber,CurrentCount);
        return new ResponseEntity<>(Answers, HttpStatus.OK);
    }

    @RequestMapping(value = "ChangeIt",method = RequestMethod.POST)
    @ApiOperation(value="술래 체인지", notes="방넘버(String)")
    public ResponseEntity<String> ChangeIt(@RequestBody Map<String,Object> requestMap){
        String roomNumber = (String)requestMap.get("roomNumber");
        if(roomNumber == null )
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        String ItName = service.ChangeIt(roomNumber);
        return new ResponseEntity<>(ItName, HttpStatus.OK);
    }

    @RequestMapping(value = "GetIt",method = RequestMethod.POST)
    @ApiOperation(value="술래 가져오기", notes="방넘버(String)")
    public ResponseEntity<String> GetIt(@RequestBody Map<String,Object> requestMap){
        String roomNumber = (String)requestMap.get("roomNumber");
        if(roomNumber == null )
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        String ItName = service.GetIt(roomNumber);
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
        if(roomNumber == null || NickName == null || selectNickName == null || selectAnswer == null)
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        int result = service.CompareAnswer(roomNumber,NickName,selectNickName, selectAnswer);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @RequestMapping(value = "/Result",method = RequestMethod.POST)
    @ApiOperation(value="질문 검증 및 맞춘 개수 구하기", notes="방넘버(String),본인닉네임(String)")
    public ResponseEntity<Integer> Result(@RequestBody Map<String,Object> requestMap){
        String roomNumber = (String)requestMap.get("roomNumber");
        String NickName = (String) requestMap.get("NickName");
        if(roomNumber == null || NickName == null)
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        int result = service.CorrectResult(roomNumber,NickName);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/Exit",method = RequestMethod.POST)
    @ApiOperation(value="플레이어 방 나가기", notes="방넘버(String),본인닉네임(String)")
    public ResponseEntity<String> Exit(@RequestBody Map<String,Object> requestMap){
        String roomNumber = (String)requestMap.get("roomNumber");
        String NickName = (String) requestMap.get("NickName");
        if(roomNumber == null || NickName == null)
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        service.ExitPlayer(roomNumber,NickName);
        String response = "ok";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @RequestMapping(value = "/Finish",method = RequestMethod.POST)
    @ApiOperation(value="게임 종료", notes="방넘버(String)")
    public ResponseEntity<String> Finish(@RequestBody Map<String,Object> requestMap){
        String roomNumber = (String)requestMap.get("roomNumber");
        if(roomNumber == null )
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        service.FinishGame(roomNumber);
        String response = "ok";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @RequestMapping(value = "/CreateCustomQuery",method = RequestMethod.POST)
    @ApiOperation(value="커스텀질문생성", notes="방넘버(String)")
    public ResponseEntity<String> CreateCustomQuery(@RequestBody Map<String,Object> requestMap){
        String roomNumber = (String)requestMap.get("roomNumber");
        String question = (String)requestMap.get("question");
        if(roomNumber == null || question == null)
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        service.SaveCustomQuestion(roomNumber,question);
        String response = "ok";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}