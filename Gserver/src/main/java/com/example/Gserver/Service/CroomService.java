package com.example.Gserver.Service;

import com.example.Gserver.Repository.CroomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CroomService {
    @Autowired
    CroomRepo repository;

    public void CreateRoom(String roomNumber,String Nickname){
        if(repository.containsRoom(roomNumber)==false) {
            repository.addRoom(roomNumber, Nickname);
            System.out.println("방 생성되었습니다.");
        }
        else
            System.out.println("이미 해당 방이 존재합니다.");
    }

    public void Participation(String roomNumber,String Nickname){
        if(repository.containsRoom(roomNumber)==true)
            repository.InsertParticipant(roomNumber,Nickname);
        else
            System.out.println("해당 방이 존재하지 않습니다.");
    }

    public int RoomPersonnelCount(String roomNumber){
        if(repository.containsRoom(roomNumber)==true)
            return repository.GetParticipantCount(roomNumber);
        else
            System.out.println("해당 방이 존재하지 않습니다.");
        return Integer.parseInt(null);
    }
    public void GameStart(String roomNumber,int gameRepeatCount){
        if(repository.containsRoom(roomNumber)==true)
            repository.SetGameRepeatCount(roomNumber,gameRepeatCount);
        else
            System.out.println("해당 방이 존재하지 않습니다.");
    }

    public String GetQuestion(String roomNumber){ //여기서 현재 카운트 올림
        if(repository.containsRoom(roomNumber)==true)
            return repository.GetRandomQuestion(roomNumber);
        else
            System.out.println("해당 방이 존재하지 않습니다.");
        return null;
    }

    public void completeAnswer(String roomNumber,String NickName,String Answer){
        if(repository.containsRoom(roomNumber)==true)
            repository.InsertAnswer(roomNumber,NickName,Answer);
        else
            System.out.println("해당 방이 존재하지 않습니다.");
    }

    public void CurrentRoomCount(){
        System.out.println(repository.getRoomCount());
    }

    public int CompareAnswer(String roomNumber,String NickName,String selectNickName[],String selectAnswer[]){

        int count;

        if(repository.containsRoom(roomNumber)==true)
            count = repository.GetParticipantCount(roomNumber);
        else{
            System.out.println("해당 방이 존재하지 않습니다.");
            return Integer.parseInt(null);
        }

        for(int i=0;i<count;i++){
            String Answer = repository.GetAnswer(roomNumber,selectNickName[i]);
            if (selectAnswer[i].equals(Answer)) {
                System.out.println("답변이 일치합니다!");
                repository.plusCorrectCount(roomNumber, NickName);
            }
            else{
                System.out.println("답변이 일치하지 않습니다.");
            }
        }
        return repository.getCorrectCount(roomNumber, NickName);
    }

    public int CorrectResult(String roomNumber,String NickName){

        if(repository.containsRoom(roomNumber)==true)
            return repository.getCorrectCount(roomNumber,NickName);
        else
            System.out.println("해당 방이 존재하지 않습니다.");
        return Integer.parseInt(null);
    }

    public void FinishGame(String roomNumber){
        if(repository.containsRoom(roomNumber)==true)
            repository.removeRoom(roomNumber);
        else
            System.out.println("해당 방이 존재하지 않습니다.");
    }






}
