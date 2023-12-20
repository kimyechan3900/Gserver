package com.example.Gserver.local;

import java.util.HashMap;
import java.util.Map;

public class GRoom {
    private String roomNumber;
    private int participantCount;
    private Map<String,Player> participantPlayer;
    private int gameRepeatCount;
    private int currentCount;
    private Question question;

    public GRoom(String roomNumber) {
        this.roomNumber = roomNumber;
        this.gameRepeatCount = 0;
        this.participantCount = 0;
        this.gameRepeatCount=0;
        this.participantPlayer = new HashMap<>();
        this.question = new Question();
    }

    // Getter and Setter methods

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getParticipantCount() {
        return participantCount;
    }

    public void setParticipantCount(int participantCount) {
        this.participantCount = participantCount;
    }

    public Player getParticipantPlayer(String nickname) {
        if (participantPlayer.containsKey(nickname)) {
            return participantPlayer.get(nickname);
        } else {
            return null;
        }
    }
    public void insertParticipantPlayer(String nickname){ //일반 참여자인 경우
        Player player = new Player(nickname,false,0);
        participantPlayer.put(nickname,player);
        participantCount++;
    }
    public void setHostPlayer(String nickname){ //방장 참여자인 경우
        Player player = new Player(nickname,true,0);
        participantPlayer.put(nickname,player);
        participantCount++;
    }


    public void setParticipantNicknames(Player participant) {
        this.participantPlayer.put(participant.getNickname(),participant);
    }

    public int getGameRepeatCount() {
        return gameRepeatCount;
    }

    public void setGameRepeatCount(int gameRepeatCount) {
        this.gameRepeatCount = gameRepeatCount;
    }

    public String getQuestionArray() {
        return question.randomQuestionArray();
    }

    public void plusCurrentCount(){
        this.currentCount++;
    }

    public int getCurrentCount(){
        return this.currentCount;
    }

    public void insertAnswer(String NickName,String answer){
        if(participantPlayer.containsKey(NickName)==true){
            participantPlayer.get(NickName).insertAnswer(answer);
        }
    }

    public String getAnswer(String NickName){
        if(participantPlayer.containsKey(NickName)==true){
            return participantPlayer.get(NickName).getAnswer();
        }
        return null;
    }

    public int getCorrectCount(String NickName){
        if(participantPlayer.containsKey(NickName)==true){
            return participantPlayer.get(NickName).getCorrectAnswerCount();
        }
        return 0;
    }

    public void plusCorrectCount(String NickName){
        if(participantPlayer.containsKey(NickName)==true){
            participantPlayer.get(NickName).plusCorrectAnswerCount();
        }
    }




}
