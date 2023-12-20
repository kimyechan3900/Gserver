package com.example.Gserver.Main.domain.question.Repository;

import com.example.Gserver.Main.domain.question.Model.CustomQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomQuestionRepo extends JpaRepository<CustomQuestion,Long> {


}
