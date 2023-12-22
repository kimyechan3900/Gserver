package com.example.Gserver.Main.domain.participate.Controller;

import com.example.Gserver.Main.domain.game.Dto.RequestDto.RoundDto;
import com.example.Gserver.Main.domain.participate.Dto.ResponseDto.HostResponseDto;
import com.example.Gserver.Main.domain.participate.Dto.ResponseDto.ItResponseDto;
import com.example.Gserver.Main.domain.participate.Dto.ResponseDto.ParticipationResponseDto;
import com.example.Gserver.Main.domain.participate.Service.PlayerService;
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


    @RequestMapping(value = "/GetHost/{roomId}",method = RequestMethod.GET)
    @ApiOperation(value="방장 정보 가져오기", notes="방넘버(String)")
    public ResponseEntity<HostResponseDto> SearchHost(@PathVariable String roomId){
        HostResponseDto hostResponseDto= playerService.GetHost(roomId);

        return new ResponseEntity<>(hostResponseDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/GetParticipation/{roomId}",method = RequestMethod.GET)
    @ApiOperation(value="참가자 정보 가져오기", notes="방넘버(String)")
    public ResponseEntity<List<ParticipationResponseDto>> GetParticipation(@PathVariable String roomId){
        List<ParticipationResponseDto> participationResponseDto = playerService.getParticipation(roomId);

        return new ResponseEntity<>(participationResponseDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/GameStart",method = RequestMethod.PATCH)
    @ApiOperation(value="게임 시작", notes="방넘버(String),라운드개수(String)")
    public ResponseEntity<ItResponseDto> GameStart(@RequestBody @Valid RoundDto roundDTO){
        ItResponseDto itResponseDto = playerService.GameStart(roundDTO);

        return new ResponseEntity<>(itResponseDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/Exit/{playerId}",method = RequestMethod.DELETE)
    @ApiOperation(value="플레이어 방 나가기", notes="방넘버(String),본인닉네임(String)")
    public void Exit(@PathVariable Long playerId){
        playerService.ExitPlayer(playerId);
    }
}