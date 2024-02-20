package com.example.quizapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class QuizList implements Initializable {
    Map<Quiz,Integer> quizzes=null;
    private NewScreenListener screenListener;
    private Set<Quiz> keys;
private Student student;

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setScreenListener(NewScreenListener screenListener) {
        this.screenListener = screenListener;
       // setCards();
    }

    @FXML
    private FlowPane QuizListContainer;

    public   void setCards(){ for(Quiz quiz:keys){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("quizcardlayout.fxml"));
            Node node=fxmlLoader.load();
            Quizcardlayout quizcardlayout=fxmlLoader.getController();
            quizcardlayout.setQuiz(quiz);
            quizcardlayout.setStudent(this.student);
            quizcardlayout.setNoQ(quizzes.get(quiz)+"");
            quizcardlayout.setScreenListener(this.screenListener);
            QuizListContainer.getChildren().add(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        quizzes=Quiz.getAllWithQuestionCount();
        keys=quizzes.keySet();


    }
}
