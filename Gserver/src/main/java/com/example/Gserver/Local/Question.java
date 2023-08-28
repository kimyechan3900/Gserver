package com.example.Gserver.Local;

import java.util.Random;

public class Question {
    private String[] questionArray;
    private String[] customQuestionArray;

    public Question() {
        this.questionArray = new String[] {
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
        this.customQuestionArray = new String[0];
    }

    // Getter and Setter methods

    public String randomQuestionArray() {
        Random random = new Random();
        int randomIndex = random.nextInt(questionArray.length);
        return questionArray[randomIndex];
    }

    public void setQuestionArray(String[] questionArray) {
        this.questionArray = questionArray;
    }

    public String getCustomQuestionArray() {
        Random random = new Random();
        int randomIndex = random.nextInt(customQuestionArray.length);
        return customQuestionArray[randomIndex];
    }

    public void setCustomQuestionArray(String[] customQuestionArray) {
        this.customQuestionArray = customQuestionArray;
    }
}

