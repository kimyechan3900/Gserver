package com.gserver.domain.game.Model;

import com.gserver.domain.participate.Model.Player;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table
public class PlayerAnswer implements Serializable {

    @Id
    @Column(name = "PLATER_ANSWER_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int playerAnswerID;


    @ManyToOne(optional = false)
    @JoinColumn(name = "PLAYER_ID")
    private Player player;


    @Column(name = "ANSWER_ROUND")
    private int answerRound;

    @Column(name = "ANSWER")
    private String answer;


}

