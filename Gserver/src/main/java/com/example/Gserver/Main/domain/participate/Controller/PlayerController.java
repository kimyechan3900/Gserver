package com.example.Gserver.Main.domain.participate.Controller;

import com.example.Gserver.Main.domain.game.Dto.RequestDto.RoomRequestDto;
import com.example.Gserver.Main.domain.game.Dto.RequestDto.RoundDto;
import com.example.Gserver.Main.domain.participate.Dto.ResponseDto.HostResponseDto;
import com.example.Gserver.Main.domain.participate.Dto.ResponseDto.ItResponseDto;
import com.example.Gserver.Main.domain.participate.Dto.ResponseDto.ParticipationResponseDto;
import com.example.Gserver.Main.domain.participate.Service.PlayerService;
import com.example.Gserver.Main.domain.room.Dto.RequestDto.ParticipationRequestDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/Player")
public class PlayerController {

    PlayerService playerService;

    public PlayerController(PlayerService playerService){
        this.playerService = playerService;
    }


    @RequestMapping(value = "/SearchHost",method = RequestMethod.POST)
    @ApiOperation(value="방장 정보 가져오기", notes="방넘버(String)")
    public ResponseEntity<HostResponseDto> SearchHost(@RequestBody @Valid RoomRequestDto roomDTO){
        HostResponseDto hostResponseDto= playerService.SearchHost(roomDTO);

        return new ResponseEntity<>(hostResponseDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/GetParticipation",method = RequestMethod.POST)
    @ApiOperation(value="참가자 정보 가져오기", notes="방넘버(String)")
    public ResponseEntity<List<ParticipationResponseDto>> GetParticipation(@RequestBody @Valid RoomRequestDto roomDto){
        List<ParticipationResponseDto> participationResponseDto = playerService.getParticipation(roomDto);

        return new ResponseEntity<>(participationResponseDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/GameStart",method = RequestMethod.POST)
    @ApiOperation(value="게임 시작", notes="방넘버(String),라운드개수(String)")
    public ResponseEntity<ItResponseDto> GameStart(@RequestBody @Valid RoundDto roundDTO){
        ItResponseDto itResponseDto = playerService.GameStart(roundDTO);

        return new ResponseEntity<>(itResponseDto, HttpStatus.OK);
    }


    @RequestMapping(value = "/Exit",method = RequestMethod.POST)
    @ApiOperation(value="플레이어 방 나가기", notes="방넘버(String),본인닉네임(String)")
    public void Exit(@RequestBody @Valid ParticipationRequestDto participationRequestDto){
        playerService.ExitPlayer(participationRequestDto);
    }


}