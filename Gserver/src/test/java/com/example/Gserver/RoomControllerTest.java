package com.example.Gserver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.Gserver.Controller.CroomController;
import com.example.Gserver.Service.CroomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;


@SpringJUnitConfig
@WebMvcTest(CroomController.class)
public class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CroomService croomService;


    @Test
    void helloTest() throws Exception {
        mockMvc.perform(get("/Room/")).andExpect(status().isOk());
    }

    @Test
    public void testCreateRoom() throws Exception {
        // 테스트에 필요한 입력 데이터
        String roomNumber = "123";
        String nickName = "John";

        // API 호출
        mockMvc.perform(post("/Room/CreateRoom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"roomNumber\": \"" + roomNumber + "\", \"NickName\": \"" + nickName + "\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("ok"));
        // 테스트 이후에 추가적인 검증 로직 작성
    }
}

