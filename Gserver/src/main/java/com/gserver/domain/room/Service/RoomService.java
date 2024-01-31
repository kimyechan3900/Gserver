package com.gserver.domain.room.Service;

import com.gserver.domain.participate.Model.Player;
import com.gserver.domain.participate.Repository.PlayerRepo;
import com.gserver.global.error.CustomException;
import com.gserver.global.error.ErrorCode;
import com.gserver.domain.question.Repository.CustomQuestionRepo;
import com.gserver.domain.question.Repository.DefaultQuestionRepo;
import com.gserver.domain.game.Repository.PlayerAnswerRepo;
import com.gserver.domain.room.Dto.RequestDto.ParticipationRequestDto;
import com.gserver.domain.room.Dto.ResponseDto.ParticipationResponseDto;
import com.gserver.domain.room.Mapper.RoomMapper;
import com.gserver.domain.room.Model.Room;
import com.gserver.domain.room.Repository.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
public class RoomService {


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
    private RoomMapper roomMapper;


    public RoomService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public ParticipationResponseDto CreateRoom(ParticipationRequestDto participationRequestDto) {
        String roomNumber = participationRequestDto.getRoomId();

        // 방 번호 중복 여부 확인
        if (roomRepo.existsById(roomNumber)) {
            throw new CustomException(ErrorCode.DUPLICATED_ROOMNUMBER);
        }

        // 방 생성 및 참여자 추가
        Room room = roomMapper.toRoom(participationRequestDto);
        Player PLAYER = roomMapper.toRoomManager(room, participationRequestDto);

        // 방과 참여자 정보를 데이터베이스에 저장
        roomRepo.save(room);
        playerRepo.save(PLAYER);

        return roomMapper.toParticipationResponse(room, PLAYER);
    }


    @Transactional
    public ParticipationResponseDto Participate(ParticipationRequestDto participationRequestDto) {
        String roomNumber = participationRequestDto.getRoomId();
        String nickName = participationRequestDto.getNickName();

        // 방 조회 (없으면 예외 발생)
        Room room = roomRepo.findById(roomNumber)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_ROOM));

        // 닉네임 중복 체크
        if (playerRepo.existsByRoomAndNickName(room, nickName)) {
            throw new CustomException(ErrorCode.DUPLICATED_PARTICIPATION);
        }

        //게임시작 유무 확인
        if(room.getGameRepeat()!=0)
            throw new CustomException(ErrorCode.NOT_ENTRANCE_ROOM);

        // 참여 엔티티 생성
        Player PLAYER = roomMapper.toParticipation(room, participationRequestDto);
        playerRepo.save(PLAYER);

        // 방 참여자 수 증가 및 저장
        room.setPlayerCount(room.getPlayerCount() + 1);
        roomRepo.save(room);

        return roomMapper.toParticipationResponse(room, PLAYER);
    }

}
