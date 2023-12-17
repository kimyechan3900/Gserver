package com.example.Gserver;

/*@SpringJUnitConfig
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

        String roomNumber2="3724";
        String hostName2="tioon";
        groomService.CreateRoom(roomNumber2,hostName2);
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

        String roomNumber2="3724";
        String hostName12="participant2";
        String hostName22="participant3";
        String hostName32="participant4";
        groomService.Participation(roomNumber2,hostName12);
        groomService.Participation(roomNumber2,hostName22);
        groomService.Participation(roomNumber2,hostName32);

    }

    @Test
    @Order(3)
    public void 게임시작(){
        String roomNumber="3726";
        int count = 3;
        groomService.GameStart(roomNumber,count);

        String roomNumber2="3724";
        int count2 = 2;
        groomService.GameStart(roomNumber2,count2);
    }

    @Test
    @Order(5)
    public void 기본질문저장(){
        String[] questionArray = new String[] {
                "처음 우리가 만났을 때, 저에 대한 첫인상은 어땠나요?",
                "우리가 처음 상호작용할 때, 가장 눈에 띄었던 점은 무엇이었나요?",
                "처음에는 저에 대해 어떤 가정이나 고정관념을 가지고 있었는데, 그 가정들이 바뀌었나요?",
                "우리의 관계/우정에 대한 첫 느낌이 어땠나요?",
                "저의 성격이나 행동에 대한 첫 인상은 어땠나요?",
                "우리의 처음 상호작용 중에 놀라운 점이 있었나요?",
                "저의 외모나 스타일에 대한 처음 생각은 어땠나요?",
                "처음에는 저에 대해 어떤 망설임이나 우려가 있었나요?",
                "우리의 첫 대화나 상호작용에 대한 전반적인 인상은 어땠나요?",
                "서로를 더 알아가면서, 저에 대한 처음 인상이 어떻게 변화했나요?"
        };

        for(int i=0;i< questionArray.length;i++){
            groomService.SaveDefaultQuestion(questionArray[i]);
        }
    }

    @Test
    @Order(6)
    public void 랜덤질문가져오기(){
        String roomNumber="3726";
        String question = groomService.GetQuestion(roomNumber);
        System.out.println(question);

        String roomNumber2="3724";
        String question2 = groomService.GetQuestion(roomNumber2);
        System.out.println(question2);
    }

    @Test
    @Order(7)
    public void 술래정하기(){
        String roomNumber="3724";
        String ItName = groomService.ChangeIt(roomNumber);
        System.out.println(ItName);

    }

    @Test
    @Order(8)
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

        String roomNumber2="3724";
        String hostName21="tioon";
        String Answer21="답변 Test입니다.";
        String hostName22="participant3";
        String Answer22="답변 Test입니다.";
        String hostName32="participant4";
        String Answer32="답변 Test입니다.";

        groomService.CompleteAnswer(roomNumber2,hostName21,Answer21);
        groomService.CompleteAnswer(roomNumber2,hostName22,Answer22);
        groomService.CompleteAnswer(roomNumber2,hostName32,Answer32);
    }

    @Test
    @Order(9)
    public void 답변맞추기(){
        String roomNumber="3726";
        String hostName="participant2";
        String selectNickName[]={"tioon","participant3","participant4"};
        String selectAnswer[]={"답변 Test입니다.","hello","hello2"};
        groomService.CompareAnswer(roomNumber,hostName,selectNickName,selectAnswer);

        String roomNumber2="3724";
        String hostName2="participant3";
        String selectNickName2[]={"tioon","participant2","participant4"};
        String selectAnswer2[]={"답변 Test입니다.","답변 Test입니다.","hello2"};
        groomService.CompareAnswer(roomNumber2,hostName2,selectNickName2,selectAnswer2);
    }

    @Test
    @Order(10)
    public void 게임끝내기(){
        String roomNumber="3726";
        groomService.FinishGame(roomNumber);
    }
}*/
