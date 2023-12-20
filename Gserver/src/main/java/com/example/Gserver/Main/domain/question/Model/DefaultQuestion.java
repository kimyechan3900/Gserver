package com.example.Gserver.Main.domain.question.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class DefaultQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "DEFAULT_QUESTION_ID")
    private int defaultQuestionId;

    @Column(name = "DEFAULT_QUESTION")
    private String defaultQuestion;

    public DefaultQuestion(String question) {
    }
}


