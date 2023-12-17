package com.example.Gserver.Main.domain.room.Model;

import com.example.Gserver.Main.Model.CustomQuery;
import com.example.Gserver.Main.domain.participate.Model.Participation;
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
@Table(schema = "gserver",name = "groom")
public class Room {

    @Id
    @Column(name = "ROOM_ID",length = 100)
    private String roomID;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Participation> participation;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CustomQuery> customQueries;

    @Column(name = "PARTICIPATION_COUNT")
    private int participationCount;

    @Column(name = "GAME_REPEAT")
    private int gameRepeat;

    @Column(name = "CURRENT_ROUND")
    private int currentRound;

    @Column(name = "IT_COUNT")
    private int itCount;
}
