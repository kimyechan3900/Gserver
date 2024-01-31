package com.gserver;

import static org.hamcrest.core.Is.isA;
import static org.hamcrest.core.StringRegularExpression.matchesRegex;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.Gserver.Main.Controller.CroomController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;


@SpringJUnitConfig
@WebMvcTest(CroomController.class) //수정 필요
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private GroomService groomService;


    @Test
    void helloTest() throws Exception {
        mockMvc.perform(get("/Room/"))
                        .andExpect(status().isOk());
    }

    @Test
    public void testCreateRoom() throws Exception {
        // 테스트에 필요한 입력 데이터
        String roomNumber = "3726";
        String nickName = "tioon";

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(Map.of(
                "roomNumber", roomNumber,
                "NickName", nickName
        ));

        // API 호출
        mockMvc.perform(post("/Room/CreateRoom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string("ok"));
        // 테스트 이후에 추가적인 검증 로직 작성
    }

    @Test
    public void testParticipateRoom() throws Exception {
        // 테스트에 필요한 입력 데이터
        String roomNumber="3726";
        String nickName="participation1";

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(Map.of(
                "roomNumber", roomNumber,
                "NickName", nickName
        ));

        // API 호출
        mockMvc.perform(post("/Room/ParticipateRoom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string("ok"));
        // 테스트 이후에 추가적인 검증 로직 작성
    }


    @Test
    public void testGameStart() throws Exception {
        // 테스트에 필요한 입력 데이터
        String roomNumber="3726";
        int gameRepeatCount = 3;

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(Map.of(
                "roomNumber", roomNumber,
                "gameRepeatCount", gameRepeatCount
        ));

        // API 호출
        mockMvc.perform(post("/Room/GameStart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string("ok"));
        // 테스트 이후에 추가적인 검증 로직 작성
    }

    @Test
    public void testRequestQuestion() throws Exception {
        // 테스트에 필요한 입력 데이터
        String roomNumber="3724";

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(Map.of(
                "roomNumber", roomNumber
        ));

        // API 호출
        MvcResult result = mockMvc.perform(post("/Room/RequestQuestion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string(isA(String.class)))
                .andReturn();

        System.out.println("결과 : " + result.getResponse().getContentAsString());
    }

    @Test
    public void testCompleteAnswer() throws Exception {
        // 테스트에 필요한 입력 데이터
        String roomNumber = "3726";
        String NickName = "tioon";
        String Answer = "답변 Test입니다.";

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(Map.of(
                "roomNumber", roomNumber,
                "NickName", NickName,
                "Answer", Answer
        ));

        // API 호출
        MvcResult result = mockMvc.perform(post("/Room/CompleteAnswer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string("ok"))
                .andReturn();

        // 테스트 이후에 추가적인 검증 로직 작성
    }

    @Test
    public void testGuessPerson() throws Exception {
        // 테스트에 필요한 입력 데이터
        String roomNumber = "3724";
        String NickName = "tioon";
        String[] selectNickName = {"participant1", "participant2", "participant3"};
        String[] selectAnswer = {"답변 Test입니다.", "답변 Test입니다.", "답변 Test입니다."};

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(Map.of(
                "roomNumber", roomNumber,
                "NickName", NickName,
                "selectNickName", selectNickName,
                "selectAnswer", selectAnswer
        ));


        // API 호출
        MvcResult result = mockMvc.perform(post("/Room/GuessPerson")
                        .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                        //.content("{\"roomNumber\": \"" + roomNumber +"\" , \"NickName\": \"" + NickName + "\", \"selectNickName\":"  + Arrays.toString(selectNickName) + ", \"selectAnswer\": " + Arrays.toString(selectAnswer)  + "}"))
                .andExpect(status().isOk())
                .andExpect(content().string(matchesRegex("\\d+")))
                .andReturn();
        // 테스트 이후에 추가적인 검증 로직 작성
    }

    @Test
    public void testResult() throws Exception {
        // 테스트에 필요한 입력 데이터
        String roomNumber = "3724";
        String NickName = "participant3";

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(Map.of(
                "roomNumber", roomNumber,
                "NickName", NickName
        ));

        // API 호출
        MvcResult result = mockMvc.perform(post("/Room/Result")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string(matchesRegex("\\d+")))
                .andReturn();

        /*String responseContent = result.getResponse().getContentAsString();
        System.out.println("응답 결과 : " + responseContent);*/

        // 테스트 이후에 추가적인 검증 로직 작성
    }

    @Test
    public void testFinish() throws Exception {
        // 테스트에 필요한 입력 데이터
        String roomNumber="3726";

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(Map.of(
                "roomNumber", roomNumber
        ));

        // API 호출
        MvcResult result = mockMvc.perform(post("/Room/Finish")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string("ok"))
                .andReturn();

        // 테스트 이후에 추가적인 검증 로직 작성
    }

}

