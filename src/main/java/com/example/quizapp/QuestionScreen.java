package com.example.quizapp;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

public class QuestionScreen implements Initializable {

    @FXML
    private Button NextQuestionbtn;

    @FXML
    private Button SubmitQuixbtn;

    @FXML
    private Label question;

    @FXML
    private RadioButton radio1;

    @FXML
    private RadioButton radio2;
    @FXML
    private RadioButton radio3;
    @FXML
    private FlowPane progressPane;

    @FXML
    private RadioButton radio4;
    public ToggleGroup options;
    private void radioButtonSetup(){
        options=new ToggleGroup();
        radio1.setToggleGroup(options);
        radio2.setToggleGroup(options);
        radio3.setToggleGroup(options);
        radio4.setToggleGroup(options);
    }

    @FXML
    private Label timerLabel;

    private NewScreenListener screenListener;
    @FXML
    private Label titleLabel;
    private Quiz quiz;
    private  Student student;

    public void setStudent(Student student) {
        this.student = student;
    }

    private List<Question> questionList;
    private Integer numberOfRightAnswers = 0;
    private class QuestionsObservable {
        Property<String> question = new SimpleStringProperty();
        Property<String> option1 = new SimpleStringProperty();
        Property<String> option2 = new SimpleStringProperty();
        Property<String> option3 = new SimpleStringProperty();
        Property<String> option4 = new SimpleStringProperty();
        Property<String> answer = new SimpleStringProperty();

        public void setQuestion(Question question) {
            this.question.setValue(question.getQuestion());
            this.option1.setValue(question.getOption1());
            this.option2.setValue(question.getOption2());
            this.option3.setValue(question.getOption3());
            this.option4.setValue(question.getOption4());
            this.answer.setValue(question.getAnswer());
        }
    }

    private Question currentQuestion;
    private QuestionsObservable questionsObservable;
    private Map<Question, String> studentAnswers = new HashMap<>();
    int currentIndex=0;

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
        this.titleLabel.setText(this.quiz.getTitle());
        this.getData();
    }
    private void getData(){
        if(quiz!=null)
        {
            this.questionList=quiz.getQuestions();
            Collections.shuffle(this.questionList);
            renderProgress();
            setNextQuestion();
        }

    }

    @FXML
    void Submit(ActionEvent event) {

        QuizResult quizResult = new QuizResult(this.quiz, student, numberOfRightAnswers);
        boolean result = quizResult.save(this.studentAnswers);
        if (result) {
            AlertMessage alertMessage=new AlertMessage();
            alertMessage.successMessage("You Succesfully Attemped Quiz...");

            //timer.cancel();
            //openResultScreen();
        } else {
           AlertMessage alertMessage=new AlertMessage();
           alertMessage.errorMessage("Something went wrong");
        }
    }


    private void renderProgress() {
        for (int i = 0; i < this.questionList.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass()
                            .getResource("progressCircle.fxml"));
            try {
                Node node = fxmlLoader.load();
                ProgressCircle progressCircleFXMLController = fxmlLoader.getController();
                progressCircleFXMLController.setNumber(i + 1);
                progressCircleFXMLController.setDefaultColor();
                progressPane.getChildren().add(node);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void nextQuestion(ActionEvent actionEvent) {
        boolean isRight = false;
        {
            // checking answer
            RadioButton selectedButton = (RadioButton) options.getSelectedToggle();
            String userAnswer = selectedButton.getText();
            String rightAnswer = this.currentQuestion.getAnswer();
            if (userAnswer.trim().equalsIgnoreCase(rightAnswer.trim())) {
                isRight = true;
                this.numberOfRightAnswers++;
            }

            // saving Answer to hashMap
            studentAnswers.put(this.currentQuestion, userAnswer);
        }
        Node circleNode = this.progressPane.getChildren().get(currentIndex - 1);
        ProgressCircle controller = (ProgressCircle) circleNode.getUserData();

        if (isRight) {
            controller.setRightAnsweredColor();
        } else {
            controller.setWrongAnsweredColor();
        }
        this.setNextQuestion();
    }

    private void setNextQuestion() {
        if (!(currentIndex >= questionList.size())) {

            {
                Node circlenode = this.progressPane.getChildren().get(currentIndex);
                ProgressCircle controller = (ProgressCircle) circlenode.getUserData();
                controller.setCurrentQuestionColor();
            }

            this.currentQuestion = this.questionList.get(currentIndex);
            List<String> option=new ArrayList<>();
            option.add(this.currentQuestion.getOption1());
            option.add(this.currentQuestion.getOption2());
            option.add(this.currentQuestion.getOption3());
            option.add(this.currentQuestion.getOption4());
            Collections.shuffle(option);
            this.currentQuestion.setOption1(option.get(0));
            this.currentQuestion.setOption2(option.get(1));
            this.currentQuestion.setOption3(option.get(2));
            this.currentQuestion.setOption4(option.get(3));

        //    this.question.setText(this.currentQuestion.getQuestion());
        //    this.radio1.setText(option.get(0));
        //    this.radio2.setText(option.get(1));
        //    this.radio3.setText(option.get(2));
            //   this.radio4.setText(option.get(3));

            this.questionsObservable.setQuestion(this.currentQuestion);
            currentIndex++;
        }
        else {
            hideNextQuestion();
            showSubmitQuiz();
        }
    }

    private void hideNextQuestion(){
        this.NextQuestionbtn.setVisible(false);
    }
    private void hideSubmitQuiz(){
        this.SubmitQuixbtn.setVisible(false);
    }
    private void showNextQuestion(){
        this.NextQuestionbtn.setVisible(true);
    }
    private void showSubmitQuiz(){
        this.SubmitQuixbtn.setVisible(true);
    }
    private void bindFields() {
        this.question.textProperty().bind(this.questionsObservable.question);
        this.radio4.textProperty().bind(this.questionsObservable.option4);
        this.radio3.textProperty().bind(this.questionsObservable.option3);
        this.radio2.textProperty().bind(this.questionsObservable.option2);
        this.radio1.textProperty().bind(this.questionsObservable.option1);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        radioButtonSetup();
    this.hideSubmitQuiz();
    this.showNextQuestion();
    this.questionsObservable = new QuestionsObservable();
    bindFields();
    }



}
