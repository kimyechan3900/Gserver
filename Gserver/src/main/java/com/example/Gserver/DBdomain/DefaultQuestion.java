package com.example.Gserver.DBdomain;

import com.example.Gserver.domain.Question;
import javax.persistence.Id;

import javax.persistence.*;

@Entity
@Table(schema = "gserver",name = "DefaultQuestion")
public class DefaultQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Question_id")
    private Integer id;

    @Column(name = "Question")
    private String question;
}

