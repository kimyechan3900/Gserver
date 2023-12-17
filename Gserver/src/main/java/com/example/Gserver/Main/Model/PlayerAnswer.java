package com.example.Gserver.Main.Model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "gserver",name = "playeranswer")
public class PlayerAnswer implements Serializable {

    @Id
    @Column(name = "PlayerAnswerID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int PlayerAnswerID;


    @ManyToOne(optional = false)
    @JoinColumn(name = "PlayerParticipationID")
    private Participation ParticipationId;


    @Column(name = "RoundCount")
    private int RoundCount;

    @Column(name = "Answer")
    private String Answer;


}

