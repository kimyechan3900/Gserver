package com.example.Gserver.Repository;

import com.example.Gserver.domain.GRoom;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class CroomRepo {
    private Map<String, GRoom> roomMap;


    public CroomRepo() {
        roomMap = new HashMap<>();
    }

    public void addRoom(String roomNumber,String Nickname) {
        GRoom groom = new GRoom(roomNumber);
        SetHost(groom,Nickname);
        roomMap.put(roomNumber, groom);
    }

    public GRoom getRoom(String roomNumber) {
        return roomMap.get(roomNumber);
    }

    public boolean removeRoom(String roomNumber) {
        GRoom rRoom=roomMap.remove(roomNumber);
        if(rRoom == null){
            System.out.println("방이 존재하지 않습니다.");
            return false;
        }
        else{
            System.out.println("방이 삭제되었습니다.");
            return true;
        }
    }

    public boolean containsRoom(String roomNumber) {
        return roomMap.containsKey(roomNumber);
    }

    public int getRoomCount() {
        return roomMap.size();
    }
    public void SetHost(GRoom room, String Nickname){
        room.setHostPlayer(Nickname);
    }
    public void InsertParticipant(String roomNumber, String Nickname){
        if(roomMap.containsKey(roomNumber)==true){
            roomMap.get(roomNumber).insertParticipantPlayer(Nickname);
        }
    }
    public int GetParticipantCount(String roomNumber){
        if(roomMap.containsKey(roomNumber)==true){
            return roomMap.get(roomNumber).getParticipantCount();
        }
        return 0;
    }
    public void SetGameRepeatCount(String roomNumber,int gameRepeatCount){
        if(roomMap.containsKey(roomNumber)==true){
            roomMap.get(roomNumber).setGameRepeatCount(gameRepeatCount);
        }
    }

    public int getCorrectCount(String roomNumber,String NickName){
        if(roomMap.containsKey(roomNumber)==true){
            return roomMap.get(roomNumber).getCorrectCount(NickName);
        }
        return 0;
    }

    public void plusCorrectCount(String roomNumber,String NickName){
        if(roomMap.containsKey(roomNumber)==true){
            roomMap.get(roomNumber).plusCorrectCount(NickName);
        }
    }

    public String GetRandomQuestion(String roomNumber){
        if(roomMap.containsKey(roomNumber)==true){
            roomMap.get(roomNumber).plusCurrentCount();
            return roomMap.get(roomNumber).getQuestionArray();
        }
        return null;
    }

    public void InsertAnswer(String roomNumber,String NickName,String answer){
        if(roomMap.containsKey(roomNumber)==true){
            roomMap.get(roomNumber).insertAnswer(NickName,answer);
        }
    }

    public String GetAnswer(String roomNumber,String NickName){
        if(roomMap.containsKey(roomNumber)==true){
            return roomMap.get(roomNumber).getAnswer(NickName);
        }
        return null;
    }


}
