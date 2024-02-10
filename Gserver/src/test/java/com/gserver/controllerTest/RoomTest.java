package com.gserver.controllerTest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gserver.domain.room.Repository.RoomRepo;
import org.apache.catalina.core.ApplicationContext;
import org.junit.After;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // 테스트 메소드 실행 순서 지정
public class RoomTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoomRepo roomRepo;
    private static RoomRepo staticRoomRepo;

    static List<Integer> playerList = new ArrayList<>();

    static int currentTagger; // 술래 player

    @Autowired
    public void setStaticRoomRepo(RoomRepo roomRepo) {
        staticRoomRepo = roomRepo;
    }


    @AfterAll
    static void afterAll(){
        staticRoomRepo.deleteAll();
    }

    @Test
    @Order(1)
    public void 방장_방생성_테스트() throws Exception {

        //given
        String roomId = "3728";
        String nickName = "김예찬";

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(Map.of(
                "roomId", roomId,
                "nickName", nickName
        ));

        //when
        MvcResult result = mockMvc.perform(post("/Room/CreateRoom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))

        //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomId").value(roomId))
                .andExpect(jsonPath("$.nickName").value(nickName))
                .andReturn();


        //플레이어 저장
        String jsonResponse = result.getResponse().getContentAsString();
        objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        playerList.add(jsonNode.get("playerId").asInt()); // 필드 이름이 "playerId"인 값을 가져옴
    }

    @Test
    @Order(2)
    public void 방참가_테스트() throws Exception {

        String  roomId = "3728";
        String[] nickNames = {"김철수", "황은수", "이수은", "안정수"};

        for (String nickName : nickNames) {
            //given
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(Map.of(
                    "roomId", roomId,
                    "nickName", nickName
            ));

            //when
            MvcResult result = mockMvc.perform(post("/Room/ParticipateRoom")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))

                    //then
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.roomId").value(roomId))
                    .andExpect(jsonPath("$.nickName").value(nickName))
                    .andReturn();


            //플레이어 저장
            String jsonResponse = result.getResponse().getContentAsString();
            objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);
            playerList.add(jsonNode.get("playerId").asInt()); // 필드 이름이 "playerId"인 값을 가져옴
        }
    }

    @Test
    @Order(3)
    public void 방_나가기_테스트() throws Exception {

        //given
        //when
        mockMvc.perform(delete("/Player/Exit/" + playerList.remove(playerList.size()-1))) //마지막 참가자 나가기.

        //then
                .andExpect(status().isOk());
    }


    @Test
    @Order(4)
    public void 게임시작_테스트() throws Exception {

        //given
        String roomId="3728";
        int round = 3;

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(Map.of(
                "roomId", roomId,
                "round", round
        ));

        //when
        MvcResult result = mockMvc.perform(patch("/Player/GameStart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))

        //then
                .andExpect(status().isOk())
                .andReturn();



        //술래 데이터 저장
        String jsonResponse = result.getResponse().getContentAsString();
        objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        currentTagger = jsonNode.get("playerId").asInt(); // 필드 이름이 "playerId"인 값을 가져옴

    }

    @Test
    @Order(5)
    public void 질문_가져오기() throws Exception {
        //given
        String roomNumber="3728";

        //when
        mockMvc.perform(get("/Question/RequestQuestion/" + roomNumber))


        //then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)); // 응답값이 JSON인지만 확인.
    }

    @Test
    @Order(6)
    public void 라운드1_답변완료() throws Exception {

        String roomId = "3728";
        String answer = "답변 Test입니다.";

        for(int playerId : playerList){
            //given
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(Map.of(
                    "answer", answer,
                    "playerId", playerId,
                    "roomId", roomId,
                    "roundCount", 1
            ));

            //when
            mockMvc.perform(post("/Game/CompleteAnswer")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))

            //then
                    .andExpect(status().is3xxRedirection());
        }
    }

    @Test
    @Order(7)
    public void 술래_답변_맞추기() throws Exception {

        //given
        String roomId = "3728";
        int playerId = currentTagger;
        List<Integer> playerIdList = playerList.stream()
                .filter(id -> id != playerId)
                .collect(Collectors.toList());;
        String[] selectAnswer = {"답변 Test입니다.", "답변 Test입니다.", "답변 Test입니다."};

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(Map.of(
                "roomId", roomId,
                "playerId", playerId,
                "playerIdList", playerIdList,
                "answerList", selectAnswer
        ));


        //when
        mockMvc.perform(patch("/Game/GuessPerson")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))

        //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.correctAnswer").value(2));
    }

    @Test
    @Order(8)
    public void 라운드_변환() throws Exception {

        //given
        String roomId = "3728";

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(Map.of(
                "roomId", roomId
        ));

        //when
        mockMvc.perform(post("/Game/ChangeIt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))

        //then
                .andExpect(status().isOk());
    }


    @Test
    @Order(9)
    public void 게임종료() throws Exception {

        //given
        String roomNumber="3728";

        //when
        mockMvc.perform(post("/Room/Finish/"+roomNumber))

        //then
                .andExpect(status().isOk());


    }
}

