package com.gserver.domain.room.Model;

import com.gserver.domain.question.Model.CustomQuestion;
import com.gserver.domain.participate.Model.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Room {

    @Id
    @Column(name = "ROOM_ID",length = 100)
    private String roomId;

    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Player> players;

    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CustomQuestion> customQuestions;

    @Column(name = "PLAYER_COUNT")
    private int playerCount;

    @Column(name = "GAME_REPEAT")
    private int gameRepeat;

    @Column(name = "CURRENT_ROUND")
    private int currentRound;

    //@Column(name = "IT_COUNT")
    //private int itCount;
}
