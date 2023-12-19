package com.example.Gserver.Main.domain.game.Repository;

import com.example.Gserver.Main.domain.game.Model.DefaultQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DefaultQuestionRepo extends JpaRepository<DefaultQuestion,String> {


    int countBy();

    DefaultQuestion findByDefaultQuestionId(int defaultQuestionId);
}
