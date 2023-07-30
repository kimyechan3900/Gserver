package com.example.Gserver.DBdomain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(schema = "gserver",name = "groom")
public class Groom {

    @Id
    @Column(name = "RoomID",length = 100)
    private String RoomID;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Participation> participation;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CustomQuery> customQueries;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<PlayerAnswer> playerAnswers;

    @Column(name = "ParticipationCount")
    private int ParticipationCount;

    @Column(name = "GameRepeat")
    private int GameRepeat;

    @Column(name = "CurrentRound")
    private int CurrentRound;


    @Column(name = "ItCount")
    private int ItCount;

    public Groom() {
        // Default constructor
    }

    public Groom(String RoomID, int ParticipationCount, int GameRepeat, int CurrentRound, int ItCount) {
        this.RoomID = RoomID;
        this.ParticipationCount = ParticipationCount;
        this.GameRepeat = GameRepeat;
        this.CurrentRound = CurrentRound;
        this.ItCount = ItCount;
    }

    public String getRoomID() {
        return RoomID;
    }

    public void setRoomID(String RoomID) {
        this.RoomID = RoomID;
    }

    public int getParticipationCount() {
        return ParticipationCount;
    }

    public void setParticipationCount(int ParticipationCount) {
        this.ParticipationCount = ParticipationCount;
    }

    public int getGameRepeat() {
        return GameRepeat;
    }

    public void setGameRepeat(int GameRepeat) {
        this.GameRepeat = GameRepeat;
    }

    public int getCurrentCount() {
        return CurrentRound;
    }

    public void setCurrentCount(int CurrentCount) {
        this.CurrentRound = CurrentCount;
    }

    public int getItCount() {
        return ItCount;
    }

    public void setItCount(int itCount) {
        ItCount = itCount;
    }
}
