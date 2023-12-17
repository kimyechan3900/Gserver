package com.example.Gserver.Main.domain.room.Service;

import com.example.Gserver.Error.CustomException;
import com.example.Gserver.Error.ErrorCode;
import com.example.Gserver.Main.Dto.ParticipationDTO;
import com.example.Gserver.Main.Model.*;
import com.example.Gserver.Main.Repository.CustomQueryRepo;
import com.example.Gserver.Main.Repository.DefaultQuestionRepo;
import com.example.Gserver.Main.Repository.PlayerAnswerRepo;
import com.example.Gserver.Main.domain.participate.Model.Participation;
import com.example.Gserver.Main.domain.participate.Repository.ParticipationRepo;
import com.example.Gserver.Main.domain.room.Dto.RequestDto.ParticipationRequestDto;
import com.example.Gserver.Main.domain.room.Dto.ResponseDto.ParticipationResponseDto;
import com.example.Gserver.Main.domain.room.Mapper.RoomMapper;
import com.example.Gserver.Main.domain.room.Model.Room;
import com.example.Gserver.Main.domain.room.Repository.RoomRepo;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class RoomService {


    private final EntityManager entityManager;

    @Autowired
    private RoomRepo roomRepo;
    @Autowired
    private ParticipationRepo participationRepo;
    @Autowired
    private PlayerAnswerRepo playerAnswerRepo;
    @Autowired
    private DefaultQuestionRepo defaultQuestionRepo;
    @Autowired
    private CustomQueryRepo customQueryRepo;

    @Autowired
    private RoomMapper mapper = Mappers.getMapper(RoomMapper.class);


    public RoomService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public ParticipationResponseDto CreateRoom(ParticipationRequestDto participationRequestDto) {
        String roomNumber = participationRequestDto.getRoomNumber();

        // 방 번호 중복 여부 확인
        if (roomRepo.existsById(roomNumber)) {
            throw new CustomException(ErrorCode.DUPLICATED_ROOMNUMBER);
        }

        // 방 생성 및 참여자 추가
        Room room = mapper.toRoom(participationRequestDto);
        Participation participation = mapper.toRoomManager(room, participationRequestDto);

        // 방과 참여자 정보를 데이터베이스에 저장
        roomRepo.save(room);
        participationRepo.save(participation);

        return mapper.toParticipationResponse(room,participation);
    }


    @Transactional
    public ParticipationResponseDto Participate(ParticipationRequestDto participationRequestDto) {
        String roomNumber = participationRequestDto.getRoomNumber();
        String nickName = participationRequestDto.getNickName();

        // 방 조회 (없으면 예외 발생)
        Room room = roomRepo.findById(roomNumber)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_ROOM));

        // 닉네임 중복 체크
        if (participationRepo.existsByRoomIDAndNickName(room, nickName)) {
            throw new CustomException(ErrorCode.DUPLICATED_PARTICIPATION);
        }

        // 참여 엔티티 생성
        Participation participation = mapper.toParticipation(room, participationRequestDto);
        participationRepo.save(participation);

        // 방 참여자 수 증가 및 저장
        room.setParticipationCount(room.getParticipationCount() + 1);
        roomRepo.save(room);

        return mapper.toParticipationResponse(room,participation);
    }

}
