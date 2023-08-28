package com.example.Gserver.Service;

import com.example.Gserver.Model.*;
import com.example.Gserver.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public void CreateRoom(String roomNumber, String NickName) {
        if (groomRepo.existsById(roomNumber)) {
            System.out.println("이미 존재하는 방입니다.");
            return ;
        } else {
            Groom groom = new Groom(roomNumber, 1, 0, 0, 0);//방 생성
            groomRepo.save(groom);

            Participation participation = new Participation(groom, NickName, true, 0,false);
            participationRepo.save(participation);
        }
    }

    public void Participation(String roomNumber, String NickName) {
        if (groomRepo.existsById(roomNumber)) {
            Groom groom = groomRepo.getById(roomNumber);
            //System.out.println(groomRepo.existsById(roomNumber));
            Participation participation = new Participation(groom, NickName, false, 0,false);
            participationRepo.save(participation);
            groomRepo.plusParticipantCountById(roomNumber);
            //groomRepo.plusParticipantCountById(roomNumber);
            //groom.setParticipationCount(groom.getParticipationCount()+1);
        } else {
            System.out.println("방이 존재하지 않습니다.");
            return;
        }
    }

    public List<String> getParticipation(String roomNumber) {
        if (groomRepo.existsById(roomNumber)) {
            Groom groom = groomRepo.getById(roomNumber);
            return participationRepo.getParticipation(groom);
        } else {
            System.out.println("방이 존재하지 않습니다.");
            return null;
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

    public void SaveDefaultQuestion(String question){
        DefaultQuestion defaultQuestion = new DefaultQuestion(question);
        defaultQuestionRepo.save(defaultQuestion);
    }

    public void SaveCustomQuestion(String roomNumber, String question){
        if (groomRepo.existsById(roomNumber)) {
            Groom groom = groomRepo.getById(roomNumber);
            CustomQuery customQuestion = new CustomQuery(groom,question);
            customQueryRepo.save(customQuestion);
        }
        else{
            System.out.println("해당 방이 존재하지 않습니다.");
        }
    }

    public String GetQuestion(String roomNumber){
        if(groomRepo.existsById(roomNumber)) {
            Random random = new Random();
            int count = defaultQuestionRepo.getQustionCount();
            int randomNumber = random.nextInt(count);
            groomRepo.plusRoundById(roomNumber);
            return defaultQuestionRepo.getQuestion(randomNumber);
        }
        else {
            System.out.println("해당 방이 존재하지 않습니다.");
        }
        return null;
    }

    public void CompleteAnswer(String roomNumber, String NickName, String Answer) {
        if (groomRepo.existsById(roomNumber)) {
            Groom groom = groomRepo.getById(roomNumber);
            if (participationRepo.existsByRoomIDAndNickName(groom, NickName)) {
                Participation participation = participationRepo.getByRoomIDAndNickName(groom, NickName);
                int Count = playerAnswerRepo.countByParticipationAnswer(participation);
                PlayerAnswer playerAnswer = new PlayerAnswer(participation,groom, Count + 1, Answer);
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

    public String ChangeIt(String roomNumber){
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
                    System.out.println("!@#!@#!@#$@$%#$^%$#%^$%^$%^");
                }
                else {
                    participationRepo.FalseIt(ParticipationID);
                    System.out.println("!!!!!!1");
                }
            }
            groomRepo.plusItCountById(roomNumber);
            return participationRepo.getIt(groom);
        }
        return null;
    }

    public List<String> GetAnswers(String roomNumber,int CurrentCount){
        if (groomRepo.existsById(roomNumber)){
            Groom groom = groomRepo.getById(roomNumber);
            if(groomRepo.getParticipationCount(roomNumber)-1 == playerAnswerRepo.getAnswerCount(groom,CurrentCount)){
                return playerAnswerRepo.getAnswers(groom,CurrentCount);
            }
            else
                return null;
        }
        return null;
    }

    public int CorrectResult(String roomNumber,String NickName){
        if (groomRepo.existsById(roomNumber)) {
            Groom groom = groomRepo.getById(roomNumber);
            if (participationRepo.existsByRoomIDAndNickName(groom, NickName)) {
                Participation participation = participationRepo.getByRoomIDAndNickName(groom, NickName);
                return participationRepo.getCorrectAnswer(groom,NickName);
            } else {
                System.out.println("해당 참여자가 존재하지 않습니다.");
                return -1;
            }
        } else {
            System.out.println("해당 방이 존재하지 않습니다.");
            return -1;
        }
    }

    public void ExitPlayer(String roomNumber,String NickName){
        if (groomRepo.existsById(roomNumber)) {
            Groom groom = groomRepo.getById(roomNumber);
            if(participationRepo.existsByRoomIDAndNickName(groom,NickName)) {
                Participation participation = participationRepo.getByRoomIDAndNickName(groom, NickName);
                playerAnswerRepo.deleteByParticipationId(participation);
                participationRepo.deleteByRoomIDandNickName(groom, NickName);
                groomRepo.minusParticipantCountById(roomNumber);
                if(groomRepo.getParticipationCount(roomNumber) == 0)
                    groomRepo.deleteByRoomID(roomNumber);
            }
            else
                System.out.println("플레이어가 존재하지 않습니다.");
        }
        else
            System.out.println("방이 존재하지 않습니다.");
    }

    public void FinishGame(String roomNumber){
        if (groomRepo.existsById(roomNumber)) {
            Groom groom = groomRepo.getById(roomNumber);
            playerAnswerRepo.deleteByRoomID(groom);
            participationRepo.deleteByRoomID(groom);
            customQueryRepo.deleteByRoomID(groom);
            groomRepo.deleteByRoomID(roomNumber);
        } else {
            System.out.println("방이 존재하지 않습니다.");
        }
    }



}
