package com.gserver.domain.question.Repository;

import com.gserver.domain.question.Model.CustomQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomQuestionRepo extends JpaRepository<CustomQuestion,Long> {


}
