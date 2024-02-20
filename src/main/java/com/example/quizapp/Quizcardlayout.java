package com.example.quizapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class Quizcardlayout implements Initializable {
    @FXML
    private Label NoQ;
private Student student;

    public void setStudent(Student student) {
        this.student = student;
    }

    @FXML
    private Label QuizTitle;

    @FXML
    private Button startbtn;

    private Quiz quiz;

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
        this.QuizTitle.setText(this.quiz.getTitle());
    }

    @FXML
    void startQuiz(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("QuestionScreen.fxml"));
        try {
           Node node= fxmlLoader.load();
        QuestionScreen questionScreen= fxmlLoader.getController();
        questionScreen.setStudent(this.student);
        questionScreen.setQuiz(this.quiz);
           this.screenListener.ChangeScreen(node);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public  void setNoQ(String value)
    {
        this.NoQ.setText(value);
    }


    private NewScreenListener screenListener;

    public void setScreenListener(NewScreenListener screenListener) {
        this.screenListener = screenListener;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
