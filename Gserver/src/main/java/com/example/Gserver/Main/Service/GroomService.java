package com.example.Gserver.Main.Service;

import com.example.Gserver.Error.CustomException;
import com.example.Gserver.Error.ErrorCode;
import com.example.Gserver.Main.Dto.*;
import com.example.Gserver.Main.Model.*;
import com.example.Gserver.Main.Repository.*;
import com.example.Gserver.Main.Model.*;
import com.example.Gserver.Main.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Random;

@Service
public class GroomService {


    private final EntityManager entityManager;

    @Autowired
    private GroomRepo groomRepo;
    @Autowired
    private ParticipationRepo participationRepo;
    @Autowired
    private PlayerAnswerRepo playerAnswerRepo;
    @Autowired
    private DefaultQuestionRepo defaultQuestionRepo;
    @Autowired
    private CustomQueryRepo customQueryRepo;


    public GroomService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void CreateRoom(ParticipationDTO participationDTO) {
        String roomNumber = participationDTO.getRoomNumber();
        String nickName = participationDTO.getNickName();
        if (groomRepo.existsById(roomNumber)) {
            throw new CustomException(ErrorCode.DUPLICATED_ROOMNUMBER);
        } else {
            Groom groom = new Groom(roomNumber, 1, 0, 0, 0);//방 생성
            groomRepo.save(groom);

            System.out.println(roomNumber +" " + nickName+"  Controller");
            Participation participation = new Participation(groom, nickName, true, 0,false);
            participationRepo.save(participation);
        }
    }

    public void Participation(ParticipationDTO participationDTO) {
        String roomNumber = participationDTO.getRoomNumber();
        String nickName = participationDTO.getNickName();
        if (groomRepo.existsById(roomNumber)) {
            Groom groom = groomRepo.getById(roomNumber);
            //System.out.println(groomRepo.existsById(roomNumber));
            if(participationRepo.existsByRoomIDAndNickName(groom,nickName)) { //방에 해당 닉네임이 있을때
                throw new CustomException(ErrorCode.DUPLICATED_PARTICIPATION);
            }
            else {
                Participation participation = new Participation(groom, nickName, false, 0, false);
                participationRepo.save(participation);
                groomRepo.plusParticipantCountById(roomNumber);
            }
            //groomRepo.plusParticipantCountById(roomNumber);
            //groom.setParticipationCount(groom.getParticipationCount()+1);
        } else {
            throw new CustomException(ErrorCode.NOT_EXIST_ROOM);
        }
    }

    public List<String> getParticipation(RoomDTO roomDTO) {
        String roomNumber = roomDTO.getRoomNumber();
        if (groomRepo.existsById(roomNumber)) {
            Groom groom = groomRepo.getById(roomNumber);
            return participationRepo.getParticipation(groom);
        } else {
            throw new CustomException(ErrorCode.NOT_EXIST_ROOM);
        }
    }


    public int RoomPersonnelCount(RoomDTO roomDTO) {
        String roomNumber = roomDTO.getRoomNumber();
        if (groomRepo.existsById(roomNumber)) {
            return participationRepo.countByRoomID(roomNumber);
        } else {
            throw new CustomException(ErrorCode.NOT_EXIST_ROOM);
        }
    }

    public Participation SearchHost(RoomDTO roomDTO) {
        String roomNumber = roomDTO.getRoomNumber();
        if (groomRepo.existsById(roomNumber)) {
            Groom groom = groomRepo.getById(roomNumber);
            return participationRepo.getByRoomIDAndRoomOwner(groom);
        } else {
            throw new CustomException(ErrorCode.NOT_EXIST_ROOM);
        }
    }



    public void GameStart(RoundDTO roundDTO) {
        String roomNumber = roundDTO.getRoomNumber();
        int gameRepeatCount = roundDTO.getRound();
        if (groomRepo.existsById(roomNumber)) {
            groomRepo.SetGameRepeatCount(roomNumber, gameRepeatCount);
        } else {
            throw new CustomException(ErrorCode.NOT_EXIST_ROOM);
        }
    }

    public void SaveDefaultQuestion(String question){
        DefaultQuestion defaultQuestion = new DefaultQuestion(question);
        defaultQuestionRepo.save(defaultQuestion);
    }

    public void SaveCustomQuestion(CustomQueryDTO customQueryDTO){
        String roomNumber = customQueryDTO.getRoomNumber();
        String question = customQueryDTO.getQuestion();
        if (groomRepo.existsById(roomNumber)) {
            Groom groom = groomRepo.getById(roomNumber);
            CustomQuery customQuestion = new CustomQuery(groom,question);
            customQueryRepo.save(customQuestion);
        }
        else{
            throw new CustomException(ErrorCode.NOT_EXIST_ROOM);
        }
    }

    public String GetQuestion(RoomDTO roomDTO){
        String roomNumber = roomDTO.getRoomNumber();
        if(groomRepo.existsById(roomNumber)) {
            Random random = new Random();
            int count = defaultQuestionRepo.getQustionCount();
            if(count == 0)
                throw new CustomException(ErrorCode.NOT_EXIST_ROOM);
            System.out.println("my test   "+ count);
            int randomNumber = random.nextInt(count);
            groomRepo.plusRoundById(roomNumber);
            return defaultQuestionRepo.getQuestion(randomNumber);
        }
        else {
            throw new CustomException(ErrorCode.NOT_EXIST_ROOM);
        }
    }


    public void CompleteAnswer(ParticipationAnswerDTO participationAnswerDTO) {
        String roomNumber = participationAnswerDTO.getRoomNumber();
        String NickName = participationAnswerDTO.getNickName();
        String Answer = participationAnswerDTO.getAnswer();
        if (groomRepo.existsById(roomNumber)) {
            Groom groom = groomRepo.getById(roomNumber);
            if (participationRepo.existsByRoomIDAndNickName(groom, NickName)) {
                Participation participation = participationRepo.getByRoomIDAndNickName(groom, NickName);
                int Count = playerAnswerRepo.countByParticipationAnswer(participation);
                PlayerAnswer playerAnswer = new PlayerAnswer(participation,groom, Count + 1, Answer);
                playerAnswerRepo.save(playerAnswer);
            } else {
                throw new CustomException(ErrorCode.NOT_EXIST_PARTICIPATION);
            }
        } else {
            throw new CustomException(ErrorCode.NOT_EXIST_ROOM);
        }
    }


    public void CurrentRoomCount(){
        System.out.println(groomRepo.count());
    }

    public int CompareAnswer(ParticipationAnswerListDTO participationAnswerListDTO){
        String roomNumber = participationAnswerListDTO.getRoomNumber();
        String NickName = participationAnswerListDTO.getNickName();
        String selectNickName[] = participationAnswerListDTO.getNickNameList();
        String selectAnswer[] = participationAnswerListDTO.getAnswerList();
        int count;
        int round;
        Groom groom;
        Participation myPart;
        if (groomRepo.existsById(roomNumber)) {
            groom = groomRepo.getById(roomNumber);
            count = groomRepo.getParticipationCount(roomNumber);
            round = groomRepo.getCurrentRound(roomNumber);
            myPart = participationRepo.getByRoomIDAndNickName(groom,NickName);
        }
        else{
            throw new CustomException(ErrorCode.NOT_EXIST_ROOM);
        }

        if(selectNickName.length != count-1 || selectAnswer.length != count-1)
            throw new CustomException(ErrorCode.INVALID_INPUT_LIST);

        for(int i=0;i<count-1;i++) {
            Participation participation = participationRepo.getByRoomIDAndNickName(groom, selectNickName[i]);
            String Answer = playerAnswerRepo.getByParticipationIDAndRoundCount(participation, round);
            System.out.println(Answer + "   " + selectAnswer[i]);
            if (selectAnswer[i].equals(Answer)) {
                System.out.println("답변이 일치합니다!");
                participationRepo.plusCorrectAnswer(groom, NickName);
                //myPart.setCorrectAnswer(myPart.getCorrectAnswer()+1);
            } else {
                System.out.println("답변이 일치하지 않습니다.");
            }
        }
        System.out.println(groom.getRoomID() + NickName);

        return participationRepo.getCorrectAnswer(groom,NickName);
    }

    @Transactional
    public String ChangeIt(RoomDTO roomDTO){
        String roomNumber = roomDTO.getRoomNumber();
        if (groomRepo.existsById(roomNumber)){
            Groom groom = groomRepo.getById(roomNumber);
            if(groomRepo.getItCount(roomNumber) == groomRepo.getParticipationCount(roomNumber)){ // 술래 한바퀴 돌았을 때
                groomRepo.resetItCount(roomNumber);
                participationRepo.resetIt(groom);
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            }
            int max = groomRepo.getParticipationCount(roomNumber);
            int current = groomRepo.getItCount(roomNumber);
            for(int i=0;i<max;i++){
                Pageable pageable = PageRequest.of(i,1);
                int ParticipationID=participationRepo.getOneParticipation(groom,pageable).get(0);
                System.out.println(ParticipationID+"\n\n\n\n\n\n"+(i)+"   "+current+"\n\n\n\n");
                if(i==current) {
                    participationRepo.TrueIt(ParticipationID);
                }
                else {
                    participationRepo.FalseIt(ParticipationID);
                }
            }
            groomRepo.plusItCountById(roomNumber);
            return participationRepo.getIt(groom);
        }
        return null;
    }

    public String GetIt(RoomDTO roomDTO){
        String roomNumber = roomDTO.getRoomNumber();
        if (groomRepo.existsById(roomNumber)){
            Groom groom = groomRepo.getById(roomNumber);
            return participationRepo.getIt(groom);
        }
        throw new CustomException(ErrorCode.NOT_EXIST_ROOM);
    }

    public List<String> GetAnswers(RoundDTO roundDTO){
        String roomNumber = roundDTO.getRoomNumber();
        int CurrentCount = roundDTO.getRound();
        if (groomRepo.existsById(roomNumber)){
            Groom groom = groomRepo.getById(roomNumber);
            if(groomRepo.getParticipationCount(roomNumber)-1 == playerAnswerRepo.getAnswerCount(groom,CurrentCount)){
                return playerAnswerRepo.getAnswers(groom,CurrentCount);
            }
            else
                throw new CustomException(ErrorCode.INVALID_LIST);
        }
        throw new CustomException(ErrorCode.NOT_EXIST_ROOM);
    }

    public int CorrectResult(ParticipationDTO participationDTO){
        String roomNumber = participationDTO.getRoomNumber();
        String NickName = participationDTO.getNickName();
        if (groomRepo.existsById(roomNumber)) {
            Groom groom = groomRepo.getById(roomNumber);
            if (participationRepo.existsByRoomIDAndNickName(groom, NickName)) {
                Participation participation = participationRepo.getByRoomIDAndNickName(groom, NickName);
                return participationRepo.getCorrectAnswer(groom,NickName);
            } else {
                throw new CustomException(ErrorCode.NOT_EXIST_PARTICIPATION);
            }
        } else {
            throw new CustomException(ErrorCode.NOT_EXIST_ROOM);
        }
    }

    @Transactional
    public void ExitPlayer(ParticipationDTO participationDTO){
        String roomNumber = participationDTO.getRoomNumber();
        String nickName = participationDTO.getNickName();
        if (groomRepo.existsById(roomNumber)) {
            Groom groom = groomRepo.getById(roomNumber);
            if(participationRepo.existsByRoomIDAndNickName(groom,nickName)) {
                Participation participation = participationRepo.getByRoomIDAndNickName(groom, nickName);
                playerAnswerRepo.deleteByParticipationId(participation);

                if(participationRepo.existsHostByRoomIDAndNickName(groom,nickName)){
                    List<Participation> participationList = participationRepo.getNextRoomHostList(groom);
                    if(!participationList.isEmpty())
                        participationList.get(0).setRoomOwner(true); // 이부분이 문제인거같음
                }

                participationRepo.deleteByRoomIDandNickName(groom, nickName);
                groomRepo.minusParticipantCountById(roomNumber);

                if(groomRepo.getParticipationCount(roomNumber) == 0)
                    groomRepo.deleteByRoomID(roomNumber);
            }
            else
                throw new CustomException(ErrorCode.NOT_EXIST_PARTICIPATION);
        }
        else
            throw new CustomException(ErrorCode.NOT_EXIST_ROOM);
    }

    public void FinishGame(RoomDTO roomDTO){
        String roomNumber = roomDTO.getRoomNumber();
        if (groomRepo.existsById(roomNumber)) {
            Groom groom = groomRepo.getById(roomNumber);
            playerAnswerRepo.deleteByRoomID(groom);
            participationRepo.deleteByRoomID(groom);
            customQueryRepo.deleteByRoomID(groom);
            groomRepo.deleteByRoomID(roomNumber);
        } else {
            throw new CustomException(ErrorCode.NOT_EXIST_ROOM);
        }
    }



}
