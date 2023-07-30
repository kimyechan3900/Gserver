package com.example.Gserver.DBdomain;



import javax.persistence.*;

@Entity
@Table(schema = "gserver",name = "customquery")
public class CustomQuery {

    @Id
    @Column(name = "CustomQueryID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int CustomQueryID;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CustomRoomID")
    private Groom RoomID;

    @Column(name="Question")
    private String Question;
}
