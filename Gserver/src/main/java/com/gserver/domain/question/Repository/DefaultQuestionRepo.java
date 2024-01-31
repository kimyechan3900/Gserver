package com.gserver.domain.question.Repository;

import com.gserver.domain.question.Model.DefaultQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DefaultQuestionRepo extends JpaRepository<DefaultQuestion,String> {


    int countBy();

    DefaultQuestion findByDefaultQuestionId(int defaultQuestionId);
}
