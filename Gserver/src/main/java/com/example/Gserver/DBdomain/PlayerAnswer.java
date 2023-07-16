package com.example.Gserver.DBdomain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(schema = "gserver",name = "playeranswer")
public class PlayerAnswer implements Serializable {

    @Id
    @Column(name = "PlayerAnswerID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int PlayerAnswerID;


    @ManyToOne()
    @JoinColumn(name = "PlayerParticipationID")
    private Participation ParticipationId;

    @Column(name = "RoundCount")
    private int RoundCount;

    @Column(name = "Answer")
    private String Answer;

    // 기본 생성자
    public PlayerAnswer() {
    }

    // 생성자
    public PlayerAnswer(Participation ParticipationId, int RoundCount, String Answer) {
        this.ParticipationId = ParticipationId;
        this.RoundCount = RoundCount;
        this.Answer = Answer;
    }

    // Getter 메서드
    public int getPalyerAnswerID() {
        return PlayerAnswerID;
    }

    public Participation getParticipationId() {
        return ParticipationId;
    }

    public int getRoundCount() {
        return RoundCount;
    }

    public String getAnswer() {
        return Answer;
    }

    // Setter 메서드
    public void setPalyerAnswerId(int PalyerAnswerID) {
        this.PlayerAnswerID = PalyerAnswerID;
    }

    public void setParticipationId(Participation ParticipationId) {
        this.ParticipationId = ParticipationId;
    }

    public void setRoundCount(int RoundCount) {
        this.RoundCount = RoundCount;
    }

    public void setAnswer(String Answer) {
        this.Answer = Answer;
    }
}

