package com.example.Gserver.Main.domain.game.Model;



import com.example.Gserver.Main.domain.room.Model.Room;

import javax.persistence.*;



@Entity
@Table
public class CustomQuestion {


    @Id
    @Column(name = "CUSTOM_QUESTION_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int customQueryID;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ROOM_ID")
    private Room room;

    @Column(name="CUSTOM_QUESTION")
    private String customQuestion;

}
