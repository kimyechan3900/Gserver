package com.example.Gserver.Model;

import javax.persistence.Id;

import javax.persistence.*;

@Entity
@Table(schema = "gserver", name = "DefaultQuestion")
public class DefaultQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Question_id")
    private int id;

    @Column(name = "Question")
    private String question;

    // 기본 생성자 (빈 생성자)
    public DefaultQuestion() {
    }

    // 인자를 받는 생성자
    public DefaultQuestion (String question) {
        this.question = question;
    }

    // Getter 메서드
    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    // Setter 메서드
    public void setId(int id) {
        this.id = id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}


