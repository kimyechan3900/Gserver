package com.gserver.domain.participate.Model;

import com.gserver.domain.game.Model.PlayerAnswer;
import com.gserver.domain.room.Model.Room;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Player implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PLAYER_ID")
    private Long playerId;

    @OneToMany(mappedBy = "player", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<PlayerAnswer> playerAnswers;


    @ManyToOne(optional = false)
    @JoinColumn(name = "ROOM_ID")
    private Room room;

    @Column(name = "NICK_NAME")
    private String nickName;

    @Column(name = "ROOM_OWNER")
    private boolean roomOwner;

    @Column(name = "CORRECT_ANSWER")
    private int correctAnswer;

    @Column(name = "IT")
    private boolean it;

}

