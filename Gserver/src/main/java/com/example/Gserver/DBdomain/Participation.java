package com.example.Gserver.DBdomain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Entity
@Data
@Table(schema = "gserver",name = "participation", uniqueConstraints = {@UniqueConstraint(columnNames = {"PartRoomID", "NickName"})})
public class Participation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ParticipationID")
    private int ParticipationID;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<PlayerAnswer> playerAnswers;


    @ManyToOne(optional = false)
    @JoinColumn(name = "PartRoomID")
    private Groom RoomID;
    @Column(name = "NickName")
    private String NickName;

    @Column(name = "RoomOwner")
    private boolean RoomOwner;

    @Column(name = "CorrectAnswer")
    private int CorrectAnswer;

    @Column(name = "It")
    private boolean It;

    // 기본 생성자
    public Participation() {
    }

    // 생성자
    public Participation(Groom RoomID, String NickName, boolean RoomOwner, int CorrectAnswer,boolean It) {
        this.RoomID = RoomID;
        this.NickName = NickName;
        this.RoomOwner = RoomOwner;
        this.CorrectAnswer = CorrectAnswer;
        this.It = It;
    }

    // Getter 메서드
    public int getParticipationID() {
        return ParticipationID;
    }

    public Groom getRoomID() {
        return RoomID;
    }

    public String getNickName() {
        return NickName;
    }

    public boolean isRoomOwner() {
        return RoomOwner;
    }

    public int getCorrectAnswer() {
        return CorrectAnswer;
    }

    // Setter 메서드
    public void setParticipationId(int ParticipationID) {
        this.ParticipationID = ParticipationID;
    }

    public void setRoomID(Groom RoomID) {
        this.RoomID = RoomID;
    }

    public void setNickName(String NickName) {
        this.NickName = NickName;
    }

    public void setRoomOwner(boolean RoomOwner) {
        this.RoomOwner = RoomOwner;
    }

    public void setCorrectAnswer(int CorrectAnswer) {
        this.CorrectAnswer = CorrectAnswer;
    }
    public boolean isIt() {
        return It;
    }

    public void setIt(boolean it) {
        It = it;
    }

}

