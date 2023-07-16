package com.example.Gserver.Repository;

import com.example.Gserver.DBdomain.CustomQuery;
import com.example.Gserver.DBdomain.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomQueryRepo extends JpaRepository<CustomQuery,Long> {
}
