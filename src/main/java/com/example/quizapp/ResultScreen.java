package com.example.quizapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class ResultScreen implements Initializable {
    @FXML
    private Button logOutbtn;

    @FXML
    private TableColumn<?, ?> result_col_Remarks;

    @FXML
    private TableColumn<?, ?> result_col_quizId;

    @FXML
    private TableColumn<?, ?> result_col_stuid;

    @FXML
    private TableView<?> tableViewResults;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    void onLogout(ActionEvent event) {
    logOutbtn.getScene().getWindow().hide();
    }

    private Student student;

    public void setStudent(Student student) {
        this.student = student;
    }
    private Quiz quiz;

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
    private NewScreenListener screenListener;
}