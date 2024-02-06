package com.gserver.domain.question.Service;

import com.gserver.domain.game.Dto.ResponseDto.QuestionResponseDto;
import com.gserver.domain.game.Repository.PlayerAnswerRepo;
import com.gserver.domain.participate.Repository.PlayerRepo;
import com.gserver.domain.question.Dto.Request.CustomQuestionDto;
import com.gserver.domain.question.Mapper.QuestionMapper;
import com.gserver.domain.question.Model.CustomQuestion;
import com.gserver.domain.question.Model.DefaultQuestion;
import com.gserver.domain.question.Repository.CustomQuestionRepo;
import com.gserver.domain.question.Repository.DefaultQuestionRepo;
import com.gserver.domain.room.Model.Room;
import com.gserver.domain.room.Repository.RoomRepo;
import com.gserver.global.error.CustomException;
import com.gserver.global.error.ErrorCode;
import com.gserver.domain.websocket.dto.ChatMessage;
import com.gserver.global.websocket.WebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.Random;

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
    @Autowired
    private WebSocketHandler webSocketHandler;



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

        //웹소켓 전송
        ChatMessage message = ChatMessage.builder()
                .type(ChatMessage.MessageType.GET_QUESTION)
                .roomNumber(roomId)
                .question(defaultQuestion.getDefaultQuestion())
                .build();
        try {
            webSocketHandler.handleRoomEvent(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

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
