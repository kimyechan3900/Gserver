package com.example.Gserver.Main.Model;



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

    public CustomQuery(Groom roomID, String question) {
        RoomID = roomID;
        Question = question;
    }
}
