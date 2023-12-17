package com.example.Gserver.Main.domain.room.Controller;

import com.example.Gserver.Main.domain.room.Dto.RequestDto.ParticipationRequestDto;
import com.example.Gserver.Main.domain.room.Dto.ResponseDto.ParticipationResponseDto;
import com.example.Gserver.Main.domain.room.Service.RoomService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/Room")
public class RoomController {

    RoomService roomService;

    public RoomController(RoomService roomService){
        this.roomService = roomService;
    }

    @GetMapping("/")
    public ResponseEntity<String> home() {
        String response = "ok";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/CreateRoom",method = RequestMethod.POST)
    @ApiOperation(value="방장 방생성", notes="방넘버(String),방장닉네임(String)")
    public ResponseEntity<ParticipationResponseDto> CreateRoom(@RequestBody @Valid ParticipationRequestDto participationRequestDto){
        ParticipationResponseDto participationResponseDto = roomService.CreateRoom(participationRequestDto);

        return new ResponseEntity<>(participationResponseDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/ParticipateRoom",method = RequestMethod.POST)
    @ApiOperation(value="참가자 방참가", notes="방넘버(String),참가자닉네임(String)")
    public ResponseEntity<ParticipationResponseDto> ParticipateRoom(@RequestBody @Valid ParticipationRequestDto participationRequestDto){
        ParticipationResponseDto participationResponseDto = roomService.Participate(participationRequestDto);

        return new ResponseEntity<>(participationResponseDto, HttpStatus.OK);
    }


}