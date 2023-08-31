package com.example.Gserver.Main.Repository;

import com.example.Gserver.Main.Model.DefaultQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DefaultQuestionRepo extends JpaRepository<DefaultQuestion,String> {


    @Query("SELECT count(*) FROM DefaultQuestion")
    int getQustionCount();

    @Query("SELECT question FROM DefaultQuestion d where Question_id =:Question_id")
    String getQuestion(@Param("Question_id")int question_id);
}
