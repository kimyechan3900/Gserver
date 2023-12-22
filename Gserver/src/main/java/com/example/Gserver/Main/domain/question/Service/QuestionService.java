package com.example.Gserver.Main.domain.question.Service;

import com.example.Gserver.Main.domain.game.Dto.RequestDto.*;
import com.example.Gserver.Main.domain.game.Dto.ResponseDto.AnswersResponseDto;
import com.example.Gserver.Main.domain.game.Dto.ResponseDto.CorrectResultResponseDto;
import com.example.Gserver.Main.domain.game.Dto.ResponseDto.ItResponseDto;
import com.example.Gserver.Main.domain.game.Dto.ResponseDto.QuestionResponseDto;
import com.example.Gserver.Main.domain.game.Mapper.GameMapper;
import com.example.Gserver.Main.domain.game.Model.PlayerAnswer;
import com.example.Gserver.Main.domain.game.Repository.PlayerAnswerRepo;
import com.example.Gserver.Main.domain.participate.Model.Player;
import com.example.Gserver.Main.domain.participate.Repository.PlayerRepo;
import com.example.Gserver.Main.domain.question.Dto.Request.CustomQuestionDto;
import com.example.Gserver.Main.domain.question.Mapper.QuestionMapper;
import com.example.Gserver.Main.domain.question.Model.CustomQuestion;
import com.example.Gserver.Main.domain.question.Model.DefaultQuestion;
import com.example.Gserver.Main.domain.question.Repository.CustomQuestionRepo;
import com.example.Gserver.Main.domain.question.Repository.DefaultQuestionRepo;
import com.example.Gserver.Main.domain.room.Model.Room;
import com.example.Gserver.Main.domain.room.Repository.RoomRepo;
import com.example.Gserver.error.CustomException;
import com.example.Gserver.error.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class QuestionService {


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
    private QuestionMapper questionMapper;



    public QuestionService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public void SaveDefaultQuestion(String question){
        DefaultQuestion defaultQuestion = new DefaultQuestion(question);
        defaultQuestionRepo.save(defaultQuestion);
    }

    public QuestionResponseDto GetQuestion(String roomId){

        // 방 조회 (없으면 예외 발생)
        Room room = roomRepo.findByRoomId(roomId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_ROOM));

        // 전체 질문 수 조회
        int questionCount = defaultQuestionRepo.countBy();

        // 질문이 하나도 없으면 예외 발생
        if (questionCount == 0) {
            throw new CustomException(ErrorCode.NOT_EXIST_QUESTION);
        }

        // 랜덤하게 질문 선택
        Random random = new Random();
        int randomNumber = random.nextInt((int) questionCount)+1;

        // 선택된 질문 조회
        DefaultQuestion defaultQuestion = defaultQuestionRepo.findByDefaultQuestionId(randomNumber);

        // QuestionResponseDto 생성 및 반환
        return questionMapper.toQuestionResponse(room, defaultQuestion);
    }

    public void SaveCustomQuestion(CustomQuestionDto customQuestionDto){
        String roomId = customQuestionDto.getRoomId();
        String question = customQuestionDto.getQuestion();

        // 방 조회 (없으면 예외 발생)
        Room room = roomRepo.findByRoomId(roomId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_ROOM));

        //엔티티 변환 후 저장
        CustomQuestion customQuestion = questionMapper.toCustomQuestion(room, customQuestionDto);
        customQuestionRepo.save(customQuestion);
    }



}
