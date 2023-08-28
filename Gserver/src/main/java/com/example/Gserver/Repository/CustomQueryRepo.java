package com.example.Gserver.Repository;

import com.example.Gserver.Model.CustomQuery;
import com.example.Gserver.Model.Groom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CustomQueryRepo extends JpaRepository<CustomQuery,Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM CustomQuery c WHERE c.RoomID = :RoomID")
    void deleteByRoomID(@Param("RoomID") Groom RoomID);
}
