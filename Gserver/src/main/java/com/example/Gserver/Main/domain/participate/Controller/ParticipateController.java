package com.example.Gserver.Main.domain.participate.Controller;

import com.example.Gserver.Error.CustomException;
import com.example.Gserver.Error.ErrorCode;
import com.example.Gserver.Main.Dto.*;
import com.example.Gserver.Main.domain.participate.Dto.ResponseDto.HostResponseDto;
import com.example.Gserver.Main.domain.participate.Dto.ResponseDto.ParticipationResponseDto;
import com.example.Gserver.Main.domain.participate.Model.Participation;
import com.example.Gserver.Main.domain.participate.Service.ParticipateService;
import com.example.Gserver.Main.domain.room.Dto.RequestDto.ParticipationRequestDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/Participate")
public class ParticipateController {

    ParticipateService participateService;

    public ParticipateController(ParticipateService participateService){
        this.participateService = participateService;
    }


    @RequestMapping(value = "/SearchHost",method = RequestMethod.POST)
    @ApiOperation(value="방장 정보 가져오기", notes="방넘버(String)")
    public ResponseEntity<HostResponseDto> SearchHost(@RequestBody @Valid RoomDTO roomDTO){
        HostResponseDto hostResponseDto= participateService.SearchHost(roomDTO);

        return new ResponseEntity<>(hostResponseDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/GetParticipation",method = RequestMethod.POST)
    @ApiOperation(value="참가자 정보 가져오기", notes="방넘버(String)")
    public ResponseEntity<ParticipationResponseDto> GetParticipation(@RequestBody @Valid RoomDTO roomDTO){
        ParticipationResponseDto participationResponseDto = participateService.getParticipation(roomDTO);

        return new ResponseEntity<>(participationResponseDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/GameStart",method = RequestMethod.POST)
    @ApiOperation(value="게임 시작", notes="방넘버(String),라운드개수(String)")
    public void GameStart(@RequestBody @Valid RoundDTO roundDTO){
        participateService.GameStart(roundDTO);

    }


    @RequestMapping(value = "/Exit",method = RequestMethod.POST)
    @ApiOperation(value="플레이어 방 나가기", notes="방넘버(String),본인닉네임(String)")
    public void Exit(@RequestBody @Valid ParticipationRequestDto participationRequestDto){
        participateService.ExitPlayer(participationRequestDto);
    }


}