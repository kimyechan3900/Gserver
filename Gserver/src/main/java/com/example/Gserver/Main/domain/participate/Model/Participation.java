package com.example.Gserver.Main.domain.participate.Model;

import com.example.Gserver.Main.Model.PlayerAnswer;
import com.example.Gserver.Main.domain.room.Model.Room;
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
@Table(schema = "gserver",name = "participation", uniqueConstraints = {@UniqueConstraint(columnNames = {"PartRoomID", "NickName"})})
public class Participation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ParticipationID")
    private int participationID;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<PlayerAnswer> playerAnswers;


    @ManyToOne(optional = false)
    @JoinColumn(name = "PartRoomID")
    private Room room;
    @Column(name = "NickName")
    private String nickName;

    @Column(name = "RoomOwner")
    private boolean roomOwner;

    @Column(name = "CorrectAnswer")
    private int correctAnswer;

    @Column(name = "It")
    private boolean it;

}

