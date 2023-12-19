package com.example.Gserver.Main.domain.game.Model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

import javax.persistence.*;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class DefaultQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "DEFAULT_QUESTION_ID")
    private int id;

    @Column(name = "DEFAULT_QUESTION")
    private String question;

    public DefaultQuestion(String question) {
    }
}


