package com.example.Gserver.Service;

import com.example.Gserver.DBdomain.Groom;
import com.example.Gserver.DBdomain.Participation;
import com.example.Gserver.DBdomain.PlayerAnswer;
import com.example.Gserver.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.expression.spel.ast.NullLiteral;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Optional;
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

    public void CreateRoom(String roomNumber, String NickName) {
        if (groomRepo.existsById(roomNumber)) {
            System.out.println("이미 존재하는 방입니다.");
            return ;
        } else {
            Groom groom = new Groom(roomNumber, 1, 0, 0);//방 생성
            groomRepo.save(groom);

            Participation participation = new Participation(groom, NickName, true, 0);
            participationRepo.save(participation);
        }
    }

    public void Participation(String roomNumber, String NickName) {
        if (groomRepo.existsById(roomNumber)) {
            Groom groom = groomRepo.getById(roomNumber);
            //System.out.println(groomRepo.existsById(roomNumber));
            Participation participation = new Participation(groom, NickName, false, 0);
            participationRepo.save(participation);
            groomRepo.plusParticipantCountById(roomNumber);
            //groomRepo.plusParticipantCountById(roomNumber);
            //groom.setParticipationCount(groom.getParticipationCount()+1);
        } else {
            System.out.println("방이 존재하지 않습니다.");
            return;
        }
    }

    public int RoomPersonnelCount(String roomNumber) {
        if (groomRepo.existsById(roomNumber)) {
            return participationRepo.countByRoomID(roomNumber);
        } else {
            System.out.println("해당 방이 존재하지 않습니다.");
            return 0;
        }
    }

    public void GameStart(String roomNumber, int gameRepeatCount) {
        if (groomRepo.existsById(roomNumber)) {
            groomRepo.SetGameRepeatCount(roomNumber, gameRepeatCount);
        } else {
            System.out.println("해당 방이 존재하지 않습니다.");
        }
    }

    /*public String GetQuestion(String roomNumber){
        if(groomRepo.existsById(roomNumber)) {
            Random random = new Random();
            long count = defaultQuestionRepo.count();
            int randomNumber = random.nextInt((int)count);
            defaultQuestionRepo.

        }
        else {
            System.out.println("해당 방이 존재하지 않습니다.");
        }
    }*/

    public void CompleteAnswer(String roomNumber, String NickName, String Answer) {
        if (groomRepo.existsById(roomNumber)) {
            Groom groom = groomRepo.getById(roomNumber);
            if (participationRepo.existsByRoomIDAndNickName(groom, NickName)) {
                Participation participation = participationRepo.getByRoomIDAndNickName(groom, NickName);
                int Count = playerAnswerRepo.countByParticipationAnswer(participation);
                PlayerAnswer playerAnswer = new PlayerAnswer(participation, Count + 1, Answer);
                playerAnswerRepo.save(playerAnswer);
            } else {
                System.out.println("해당 참여자가 존재하지 않습니다.");
            }
        } else {
            System.out.println("해당 방이 존재하지 않습니다.");
        }
    }

    public void CurrentRoomCount(){
        System.out.println(groomRepo.count());
    }

    public int CompareAnswer(String roomNumber,String NickName,String selectNickName[],String selectAnswer[]){
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
            System.out.println("해당 방이 존재하지 않습니다.");
            return -1;
        }

        for(int i=0;i<count-1;i++){
            Participation participation = participationRepo.getByRoomIDAndNickName(groom,selectNickName[i]);
            String Answer  = playerAnswerRepo.getByParticipationIDAndRoundCount(participation,round);
            System.out.println(Answer+"   "+selectAnswer[i]);
            if(selectAnswer[i].equals(Answer)){
                System.out.println("답변이 일치합니다!");
                participationRepo.plusCorrectAnswer(groom,NickName);
                //myPart.setCorrectAnswer(myPart.getCorrectAnswer()+1);
            }
            else{
                System.out.println("답변이 일치하지 않습니다.");
            }
        }
        return participationRepo.getCorrectAnswer(groom,NickName);
    }

    public int CorrectResult(String roomNumber,String NickName){
        if (groomRepo.existsById(roomNumber)) {
            Groom groom = groomRepo.getById(roomNumber);
            if (participationRepo.existsByRoomIDAndNickName(groom, NickName)) {
                Participation participation = participationRepo.getByRoomIDAndNickName(groom, NickName);
                return participation.getCorrectAnswer();
            } else {
                System.out.println("해당 참여자가 존재하지 않습니다.");
                return -1;
            }
        } else {
            System.out.println("해당 방이 존재하지 않습니다.");
            return -1;
        }
    }

    /*public void FinishGame(String roomNumber){
        if (groomRepo.existsById(roomNumber)) {
            groomRepo.deleteByRoomID(roomNumber);
        } else {
            System.out.println("방이 존재하지 않습니다.");
        }
    }*/



}
