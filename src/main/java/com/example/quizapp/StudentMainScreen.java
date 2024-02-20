package com.example.quizapp;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentMainScreen implements Initializable {
    @FXML
    private StackPane StackPanel;
    private Student student;

    public void setStudent(Student student) {
        this.student = student;
        addQuizListScreen();
    }

    @FXML
    private Button back;
    @FXML
    void backCode(ActionEvent event) {
        ObservableList <Node> nodes=this.StackPanel.getChildren();
        if(nodes.size()==1)
        {
            return;
        }
    this.StackPanel.getChildren().remove(nodes.size()-1);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private  void addScreenToStackPane(Node node){
        this.StackPanel.getChildren().add(node);
    }

    private void addQuizListScreen() {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("quizlist.fxml"));
           try{
            Node node=fxmlLoader.load();
            QuizList quizList=fxmlLoader.getController();
            quizList.setStudent(this.student);
            quizList.setScreenListener(new NewScreenListener() {
                @Override
                public void ChangeScreen(Node node) {
                    addScreenToStackPane(node);
                }

                @Override
                public void handle(Event event) {

                }
            });
           quizList.setCards();
            StackPanel.getChildren().add(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}