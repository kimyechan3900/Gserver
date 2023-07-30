package com.example.Gserver;

import com.example.Gserver.Repository.CroomRepo;
import com.example.Gserver.Service.CroomService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class LocalTest {

	@Autowired
	CroomService croomService;
	@Autowired
	CroomRepo croomRepository;

	@Test
	@Order(1)
	public void 방생성 (){
		String roomNumber="3724";
		String hostName="tioon";
		String hostName2="participant3";
		String hostName3="participant4";
		croomService.CreateRoom(roomNumber,hostName);
		croomService.Participation(roomNumber,hostName2);
		croomService.Participation(roomNumber,hostName3);
		int result=croomService.RoomPersonnelCount(roomNumber);
		System.out.println("roomNumber("+roomNumber+") : "+result);
	}

	@Test
	@Order(2)
	public void 방생성2 (){
		String roomNumber="1234";
		String hostName="tioon2";
		croomService.CreateRoom(roomNumber,hostName);
		int result=croomService.RoomPersonnelCount(roomNumber);
		System.out.println("roomNumber("+roomNumber+") : "+result);
	}

	/*@Test
	public void 방참가(){
		String roomNumber="3724";
		String hostName="participant";
		croomService.Participation(roomNumber,hostName);
		int result=croomService.RoomPersonnelCount(roomNumber);
		System.out.println(result);
	}*/

	@Test
	@Order(3)
	public void 방참가2(){
		String roomNumber="1234";
		String hostName="participant2";
		croomService.Participation(roomNumber,hostName);
		int result=croomService.RoomPersonnelCount(roomNumber);
		System.out.println(result);
	}

	/*@Test
	public void 추가방참가(){
		String roomNumber="3724";
		String hostName="participant3";
		String hostName2="participant4";
		croomService.Participation(roomNumber,hostName);
		croomService.Participation(roomNumber,hostName2);
		int result=croomService.RoomPersonnelCount(roomNumber);
		System.out.println(result);
	}*/

	@Test
	@Order(4)
	public void 질문가져오기(){
		String roomNumber="3724";
		String Question=croomService.GetQuestion(roomNumber);
		System.out.println(Question);
	}

	@Test
	@Order(5)
	public void 질문답변완료(){
		String roomNumber="3724";
		String hostName="tioon";
		String Answer="답변 Test입니다.";
		String hostName2="participant3";
		String Answer2="답변 Test입니다.2";
		String hostName3="participant4";
		String Answer3="답변 Test입니다.3";
		croomService.completeAnswer(roomNumber,hostName,Answer);
		croomService.completeAnswer(roomNumber,hostName2,Answer2);
		croomService.completeAnswer(roomNumber,hostName3,Answer3);
	}

	@Test
	@Order(6)
	public void 답변맞추기(){
		String roomNumber="3724";
		String hostName="participant";
		String selectNickName[]={"tioon","participant3","participant4"};
		String selectAnswer[]={"답변 Test입니다.","hello","hello2"};
		croomService.CompareAnswer(roomNumber,hostName,selectNickName,selectAnswer);
	}

	@Test
	@Order(7)
	public void 질문답변완료2(){
		String roomNumber="3724";
		String hostName="tioon";
		String Answer="답변 Test입니다.";
		String hostName2="participant3";
		String Answer2="답변 Test입니다.2";
		String hostName3="participant4";
		String Answer3="hello";
		croomService.completeAnswer(roomNumber,hostName,Answer);
		croomService.completeAnswer(roomNumber,hostName2,Answer2);
		croomService.completeAnswer(roomNumber,hostName3,Answer3);
	}

	@Test
	@Order(8)
	public void 답변맞추기2(){
		String roomNumber="3724";
		String hostName="participant";
		String selectNickName[]={"tioon","participant3","participant4"};
		String selectAnswer[]={"답변 Test입니다.","답변 Test입니다.2","답변 Test입니다.3"};
		croomService.CompareAnswer(roomNumber,hostName,selectNickName,selectAnswer);
	}

	@Test
	@Order(9)
	public void 게임끝내기(){
		croomService.CurrentRoomCount();
		String roomNumber="3724";
		croomService.FinishGame(roomNumber);
		croomService.CurrentRoomCount();
	}






}
