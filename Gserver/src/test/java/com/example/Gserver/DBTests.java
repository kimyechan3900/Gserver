package com.example.Gserver;

import com.example.Gserver.Controller.CroomController;
import com.example.Gserver.Service.GroomService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@EnableTransactionManagement
@SpringBootTest
public class DBTests {
    @Autowired
    GroomService groomService;

    @Test
    @Order(1)
    public void 방생성(){
        String roomNumber="3726";
        String hostName="tioon";
        groomService.CreateRoom(roomNumber,hostName);
    }

    @Test
    @Order(2)
    public void 참가자입장(){
        String roomNumber="3726";
        String hostName1="participant2";
        String hostName2="participant3";
        String hostName3="participant4";
        groomService.Participation(roomNumber,hostName1);
        groomService.Participation(roomNumber,hostName2);
        groomService.Participation(roomNumber,hostName3);
    }

    @Test
    @Order(3)
    public void 게임시작(){
        String roomNumber="3726";
        int count = 3;
        groomService.GameStart(roomNumber,count);
    }

    @Test
    @Order(4)
    public void 질문답변완료(){
        String roomNumber="3726";
        String hostName="tioon";
        String Answer="답변 Test입니다.";
        String hostName2="participant3";
        String Answer2="답변 Test입니다.";
        String hostName3="participant4";
        String Answer3="답변 Test입니다.";

        groomService.CompleteAnswer(roomNumber,hostName,Answer);
        groomService.CompleteAnswer(roomNumber,hostName2,Answer2);
        groomService.CompleteAnswer(roomNumber,hostName3,Answer3);
    }

    @Test
    @Order(6)
    public void 답변맞추기(){
        String roomNumber="3726";
        String hostName="participant";
        String selectNickName[]={"tioon","participant3","participant4"};
        String selectAnswer[]={"답변 Test입니다.","hello","hello2"};
        groomService.CompareAnswer(roomNumber,hostName,selectNickName,selectAnswer);
    }

}
