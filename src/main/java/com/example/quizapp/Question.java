package com.example.quizapp;

import javafx.scene.control.Alert;

import java.sql.*;

public class Question {

    private Quiz quiz;
    private Integer questionId;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String answer;
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;

    public static class MetaData {
        public static final String TABLE_NAME = "QUESTION";
        public static final String OPTION1 = "option1";
        public static final String QUESTION = "question";
        public static final String QUESTION_ID = "ID";
        public static final String OPTION2 = "option2";
        public static final String OPTION3 = "option3";
        public static final String OPTION4 = "option4";
        public static final String ANSWER = "answer";
        public static final String QUIZ_ID = "quiz_id";

    }

    // Constructers
    public Question() {
    }

    public Question(Quiz quiz, String question, String option1, String option2, String option3, String option4, String answer) {
        this.quiz = quiz;
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer = answer;
    }

    @Override
    public String toString() {
        return this.question;
    }


    public Quiz getQuiz() {
        return quiz;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public String getQuestion() {
        return question;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return option4;
    }

    public String getAnswer() {
        return answer;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    // Other Methods

    public boolean save() {
        boolean flag=false;
        String raw = "INSERT INTO %s (%s,%s,%s,%s,%s,%s,%s) VALUES (? , ? ,? , ? , ? , ? , ?)";
        String query = String.format(raw, MetaData.TABLE_NAME,
                MetaData.QUESTION, MetaData.OPTION1, MetaData.OPTION2
                , MetaData.OPTION3, MetaData.OPTION4, MetaData.ANSWER, MetaData.QUIZ_ID);
        //System.out.println("Actual Query = " + query);
            try{
            connect = Database.ConnectDb();
            prepare = connect.prepareStatement(query);
            prepare.setString(1, this.question);
            prepare.setString(2, this.option1);
            prepare.setString(3, this.option2);
            prepare.setString(4, this.option3);
            prepare.setString(5, this.option4);
            prepare.setString(6, this.answer);
            prepare.setInt(7, this.quiz.getQuizId());
            int i = prepare.executeUpdate();
           // System.out.println("Updated Rows : " + i);
           flag=true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return  false;
        }
       return  flag;
    }
}
