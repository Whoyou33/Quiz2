package com.quiz.myapplication;

public class Question {
    private int questionID;
    private boolean trueAnswer;

    public Question(int questionID, boolean trueAnswer) {
        this.questionID = questionID;
        this.trueAnswer = trueAnswer;
    }
    public boolean isTrueAnswer(){
        return trueAnswer;
    }
    public int getQuestionId(){
        return questionID;
    }
}
