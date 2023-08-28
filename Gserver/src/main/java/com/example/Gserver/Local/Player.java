package com.example.Gserver.Local;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String nickname;
    private boolean isRoomOwner;
    private int correctAnswerCount;

    private List<PlayerAnswer> Answer;

    public Player(String nickname, boolean Owner, int correctAnswerCount) {
        this.nickname = nickname;
        this.isRoomOwner = Owner;
        this.correctAnswerCount = correctAnswerCount;
        this.Answer=new ArrayList<>();
    }

    // Getter and Setter methods

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isRoomOwner() {
        return isRoomOwner;
    }

    public void setRoomOwner(boolean roomOwner) {
        isRoomOwner = roomOwner;
    }

    public int getCorrectAnswerCount() {
        return correctAnswerCount;
    }

    public void plusCorrectAnswerCount() {
        this.correctAnswerCount++;
    }

    public void insertAnswer(String answer){
        PlayerAnswer a=new PlayerAnswer(answer);
        Answer.add(a);
    }

    public String getAnswer(){
        System.out.println(Answer.size());
        return Answer.get(Answer.size()-1).getContent();
    }
}
