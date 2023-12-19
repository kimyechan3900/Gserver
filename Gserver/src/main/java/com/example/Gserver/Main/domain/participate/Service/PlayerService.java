package com.example.Gserver.Main.domain.participate.Service;

import com.example.Gserver.Error.CustomException;
import com.example.Gserver.Error.ErrorCode;
import com.example.Gserver.Main.Dto.*;


import com.example.Gserver.Main.domain.game.Dto.RequestDto.RoundDto;
import com.example.Gserver.Main.domain.game.Repository.CustomQuestionRepo;
import com.example.Gserver.Main.domain.game.Repository.DefaultQuestionRepo;
import com.example.Gserver.Main.domain.game.Repository.PlayerAnswerRepo;
import com.example.Gserver.Main.domain.participate.Dto.ResponseDto.HostResponseDto;
import com.example.Gserver.Main.domain.participate.Dto.ResponseDto.ParticipationResponseDto;
import com.example.Gserver.Main.domain.participate.Mapper.ParticipateMapper;
import com.example.Gserver.Main.domain.participate.Model.Player;
import com.example.Gserver.Main.domain.participate.Repository.PlayerRepo;
import com.example.Gserver.Main.domain.room.Dto.RequestDto.ParticipationRequestDto;
import com.example.Gserver.Main.domain.room.Model.Room;
import com.example.Gserver.Main.domain.room.Repository.RoomRepo;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {


    private final EntityManager entityManager;

    @Autowired
    private RoomRepo roomRepo;
    @Autowired
    private PlayerRepo playerRepo;
    @Autowired
    private PlayerAnswerRepo playerAnswerRepo;
    @Autowired
    private DefaultQuestionRepo defaultQuestionRepo;
    @Autowired
    private CustomQuestionRepo customQuestionRepo;
    @Autowired
    private ParticipateMapper participateMapper = Mappers.getMapper(ParticipateMapper.class);



    public PlayerService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }



    public HostResponseDto SearchHost(RoomDTO roomDTO) {
        // 방 번호 추출
        String roomNumber = roomDTO.getRoomNumber();

        // 방 조회 (없으면 예외 발생)
        Room room = roomRepo.findByRoomNumber(roomNumber)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_ROOM));

        // 방장 참가자 조회
        Optional<Player> hostParticipationOptional = playerRepo.findByRoomAndRoomOwnerIsTrue(room);

        // 방장이 존재하지 않으면 예외 발생
        Player hostPlayer = hostParticipationOptional
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_HOST));

        // Participation을 RoomResponseDto로 매핑하여 반환
        return participateMapper.toHostResponse(hostPlayer);
    }


    public List<ParticipationResponseDto> getParticipation(RoomDTO roomDTO) {
        // 방 번호 추출
        String roomNumber = roomDTO.getRoomNumber();

        // 방 조회 (없으면 예외 발생)
        Room room = roomRepo.findByRoomNumber(roomNumber)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_ROOM));

        return participateMapper.toParticipationResponse(room.getPlayers());
    }

    public void GameStart(RoundDto roundDTO) {
        String roomNumber = roundDTO.getRoomNumber();
        int gameRepeatCount = roundDTO.getRound();

        // 방 조회 (없으면 예외 발생)
        Room room = roomRepo.findByRoomNumber(roomNumber)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_ROOM));
        room.setGameRepeat(gameRepeatCount);
        roomRepo.save(room);
    }

    @Transactional
    public void ExitPlayer(ParticipationRequestDto participationRequestDto){
        String roomNumber = participationRequestDto.getRoomNumber();
        String nickName = participationRequestDto.getNickName();

        // 방 조회 (없으면 예외 발생)
        Room room = roomRepo.findById(roomNumber)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_ROOM));

        // 사용자 존재 체크 및 삭제
        Player PLAYER = playerRepo.findByRoomAndNickName(room, nickName)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_PARTICIPATION));


        // 방에 방장이 나갔다면 새로운 방장을 설정
        if (PLAYER.isRoomOwner()) {
            List<Player> remainingParticipants = room.getPlayers();
            if (!remainingParticipants.isEmpty()) {
                Player newRoomOwner = remainingParticipants.get(0);
                newRoomOwner.setRoomOwner(true);
                playerRepo.save(newRoomOwner);
            }
        }

        // 방 참가자 수 갱신
        room.setPlayerCount(room.getPlayerCount() - 1);

        // 참가자 삭제
        playerRepo.delete(PLAYER);

        // 방에 참가자가 1명일 때 방 삭제
        if (room.getPlayerCount() == 0) {
            roomRepo.delete(room);
        }
    }




}
