package com.example.Gserver.Main.domain.question.Repository;

import com.example.Gserver.Main.domain.question.Model.DefaultQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DefaultQuestionRepo extends JpaRepository<DefaultQuestion,String> {


    int countBy();

    DefaultQuestion findByDefaultQuestionId(int defaultQuestionId);
}
