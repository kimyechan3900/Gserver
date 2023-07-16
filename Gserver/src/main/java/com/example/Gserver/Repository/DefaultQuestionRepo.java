package com.example.Gserver.Repository;

import com.example.Gserver.DBdomain.CustomQuery;
import com.example.Gserver.DBdomain.DefaultQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DefaultQuestionRepo extends JpaRepository<DefaultQuestion,String> {

}
