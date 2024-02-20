package com.example.quizapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.regex.Pattern;

public class AdminHome implements Initializable {
    @FXML
    private Button logoutbtn;
    @FXML
    private Button viewQuizBtn;
    @FXML
    private AnchorPane QuizTreeView;
    @FXML
    private TreeView<Object> treeView;
    @FXML
    private Button deleteStubtn;
    @FXML
    private Button updateStubtn;
    @FXML
    void DeleteStudent(ActionEvent event) {
    addUserSelect();
    add_users_delete();
    resetForm();
    }
    @FXML
    void updateStu(ActionEvent event) {
        addUserSelect();
     add_user_update();
    }
    @FXML
    void onLogoutPress(ActionEvent event) {
       logoutbtn.getScene().getWindow().hide();
    }
    @FXML
    private RadioButton male;
    @FXML
    private RadioButton female;
    @FXML
    private TableColumn<Student,Character> col_Gender;
    @FXML
    private TableColumn<Student, String> col_MobileNumber;
    @FXML
    private TableColumn<Student, String> col_firstName;
    @FXML
    private TableColumn<Student, String> col_lastName;
    @FXML
    private TableColumn<Student, String> col_studentId;
    @FXML
    private TextField firstName;
    private QuizResult quizResult;
    private static Connection connect;
    private static PreparedStatement prepare;
    private static ResultSet result;

    private Statement statement;
    @FXML
    private VBox formContainer;
    @FXML
    private TextField lastName;
    @FXML
    private TextField mobileNumber;
    @FXML
    private Button saveStudentBtn;
    @FXML
    private TableView<Student> studentTable;
    @FXML
    private Button addNextQuestion;
    @FXML
    private AnchorPane addQuizTab;
    @FXML
    private AnchorPane addStudentTab;

    ObservableList<Student> list;
    @FXML
    private TableColumn<Student,String> col_email;
    @FXML
    private TableColumn<Student,String> col_password;
    @FXML
    private Button addStudent_btn;
    @FXML
    private Button addTeacher_btn;
    @FXML
    private Button addTitleOk;

    @FXML
    private Label greet_username;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private TextField option1;
    @FXML
    private TextField option2;
    @FXML
    private TextField option3;
    @FXML
    private TextField option4;
    @FXML
    private TextArea question;
    @FXML
    private TextField quiztitle;
    @FXML
    private RadioButton radio1;
    @FXML
    private RadioButton radio2;
    @FXML
    private RadioButton radio3;
    @FXML
    private RadioButton radio4;
    private Quiz quiz=null;
    private ArrayList<Question> questions =new ArrayList<>();
    @FXML
    private Button submitQuiz;
    private  AlertMessage alert=new AlertMessage();
    private ToggleGroup radioGroup;
    private ToggleGroup toggleGroup;
    private void RadioButtonSetUp(){
        toggleGroup=new ToggleGroup();
        male.setToggleGroup(toggleGroup);
        female.setToggleGroup(toggleGroup);
    }

    private void radioButtonSetup(){
        radioGroup=new ToggleGroup();
        radio1.setToggleGroup(radioGroup);
        radio2.setToggleGroup(radioGroup);
        radio3.setToggleGroup(radioGroup);
        radio4.setToggleGroup(radioGroup);
    }

    @FXML
    void setQuizTitle(ActionEvent event) {
           String title=quiztitle.getText();
           if(title.trim().isEmpty()){
       alert.errorMessage("Enter valid Title");
    } else
    {
        quiztitle.setEditable(false);
        this.quiz=new Quiz(title);
    }
    }
    @FXML
    void SubmitQuiz(ActionEvent event) {
        boolean flag = addQuestion();
        if (flag) {
            flag =quiz.save(questions);
            if(flag)
            {
                alert.successMessage("Quiz saved Successfully");
            }
            else {
                alert.errorMessage("Quiz not Saved");
            }
        }
    }

    private boolean validatefields()
    {
        if(quiz==null)
        {
            alert.errorMessage("Please enter Quiz Title");
            return false;
        }
        String quest=this.question.getText();
        String op1=this.option1.getText();
        String op2=this.option2.getText();
        String op3=this.option3.getText();
        String op4=this.option4.getText();
        Toggle selectedRadio =radioGroup.getSelectedToggle();
        if(quest.trim().isEmpty()||op1.trim().isEmpty()||op2.trim().isEmpty()||op3.trim().isEmpty()||op4.trim().isEmpty()||selectedRadio==null){
            alert.errorMessage("Please fill all the fields");
            return false;
        }
        else {
             return  true;
        }
    }

    private boolean addQuestion()
    {
        boolean valid=validatefields();
        Question question=new Question();
        if(valid)
        {
            question.setOption1(option1.getText().trim());
            question.setOption2(option2.getText().trim());
            question.setOption3(option3.getText().trim());
            question.setOption4(option4.getText().trim());

            Toggle selected=radioGroup.getSelectedToggle();
            String ans=null;
            if(selected==radio1)
            {
                ans=option1.getText().trim();
            } else if (selected==radio2) {
                ans=option2.getText().trim();
            }
            else if (selected==radio3) {
                ans=option3.getText().trim();
            } else if (selected==radio4) {
                ans=option4.getText().trim();
            }
            question.setAnswer(ans);
            question.setQuestion(this.question.getText().trim());

            this.question.clear();
            option1.clear();
            option2.clear();
            option3.clear();
            option4.clear();

            questions.add(question);
            question.setQuiz(quiz);
        }
        return valid;
    }
    @FXML
    void addNextQuestion(ActionEvent event) {
       addQuestion();
    }
    @FXML
  public void switchAddStudent(ActionEvent event) {
     addStudentTab.setVisible(true);
     addQuizTab.setVisible(false);
     QuizTreeView.setVisible(false);
    }

    public void switchAddQuiz(ActionEvent event)
    {
        addStudentTab.setVisible(false);
        addQuizTab.setVisible(true);
        QuizTreeView.setVisible(false);
    }

    public void switchViewQuiz(ActionEvent event)
    {
        addStudentTab.setVisible(false);
        addQuizTab.setVisible(false);
        QuizTreeView.setVisible(true);
    }

    private  void resetForm()
    {
        this.firstName.clear();
        this.lastName.clear();
        this.mobileNumber.clear();
        this.email.clear();
        this.password.clear();
    }

    @FXML
    void saveStudent(ActionEvent event) {
    String firstName=this.firstName.getText().trim();
    String lastName=this.lastName.getText().trim();
    String mobile=this.mobileNumber.getText().trim();
    String email=this.email.getText().trim();
    String password=this.password.getText().trim();
    Character stringGender='M';
    Toggle gender =toggleGroup.getSelectedToggle();
    if(gender!=null)
    {
        if(gender==female)
        {
            stringGender='F';
        }
    }
        Pattern p=Pattern.compile("^(\\D)+(\\w)*((\\.(\\w)+)?)+@(\\D)+(\\w)*((\\.(\\D)+(\\w)*)+)?(\\.)[a-z]{2,}$");
        if(firstName.isEmpty())
        {
            alert.errorMessage("FirstName Cannot be Empty");
        }
       else if(mobile.length()!=10 )
        {
            alert.errorMessage("Mobile number should be 10 digits");
        }
        else if(!p.matcher(email).matches())
        {
            alert.errorMessage("Please Enter Valid Email");
        }
        else if(password.length()<8)
        {
            alert.errorMessage("Password should be more than 8 characters");
        }
        Student s=new Student(firstName,lastName,mobile,stringGender,email,password);
        if(s.isExists()){
         alert.errorMessage("Student Already Registered");
         return;
        }
        s=s.save();
        if(s!=null)
        {
             alert.successMessage("Student Registered");
            this.resetForm();
            studentTable.getItems().add(0,s);
        }
         else {
            alert.errorMessage("Student Registration Failed ");
         }
    }

    private void renderTable()
    {
        List<Student> students=Student.getAll();
        studentTable.getItems().clear();
        this.col_studentId.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        this.col_MobileNumber.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        this.col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        this.col_password.setCellValueFactory(new PropertyValueFactory<>("password"));
        this.col_Gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        studentTable.getItems().addAll(students);
    }
    private void renderTreeView(){
        Map<Quiz , List<Question>> data = Quiz.getAll();
        Set<Quiz> quizzes = data.keySet();

        TreeItem<Object> root = new TreeItem<>("Quizzes");
        for(Quiz q : quizzes){
            TreeItem<Object> quizTreeItem = new TreeItem<>(q);

            List<Question> questions = data.get(q);
            for(Question question : questions){
                TreeItem<Object> questionTreeItem = new TreeItem<>(question);
                questionTreeItem.getChildren().add(new TreeItem<>("A : " + question.getOption1()));
                questionTreeItem.getChildren().add(new TreeItem<>("B : " +question.getOption2()));
                questionTreeItem.getChildren().add(new TreeItem<>("C : " +question.getOption3()));
                questionTreeItem.getChildren().add(new TreeItem<>("D : " +question.getOption4()));
                questionTreeItem.getChildren().add(new TreeItem<>("Ans : " +question.getAnswer()));
                quizTreeItem.getChildren().add(questionTreeItem);
            }

            quizTreeItem.setExpanded(true);
            root.getChildren().add(quizTreeItem);
        }

        root.setExpanded(true);
        this.treeView.setRoot(root);
    }

    public  void addUserSelect()
    {
        Student studentData=studentTable.getSelectionModel().getSelectedItem();
        int num=studentTable.getSelectionModel().getSelectedIndex();

        if((num-1)<-1){return;}

        firstName.setText(studentData.getFirstName());
        lastName.setText(studentData.getLastName());
        mobileNumber.setText(studentData.getMobile());
        email.setText(studentData.getEmail());
        password.setText(studentData.getPassword());
        //radioGroup.selectToggle(studentData.getGender());


    }
   public void add_users_delete()
    {
        String sql="DELETE FROM student WHERE email='"+email.getText()+"'";
        connect=Database.ConnectDb();
        try
        {
            if (firstName.getText().isEmpty())
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Please select student data first");
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete Student " + firstName.getText());
                Optional<ButtonType> option = alert.showAndWait();
                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setContentText("Successfully Deleted");
                    alert.showAndWait();
                    this.resetForm();
                    list=getDatausers();
                    studentTable.setItems(list);

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public  void add_user_update() {
        String sql = "update student set firstName='" + firstName.getText() + "',lastName='" + lastName.getText() +
                "',mobile='" + mobileNumber.getText() + "',email='" + email.getText() + "',password='" + password.getText() + "',gender='" + toggleGroup.getToggles() + "' where email='" + email.getText() + "'";

        connect = Database.ConnectDb();
        try {

            Character stringGender = 'M';
            if (toggleGroup.getToggles() != null) {
                if (toggleGroup.getToggles() == female) {
                    stringGender = 'F';
                }
            }
            Pattern p = Pattern.compile("^(\\D)+(\\w)*((\\.(\\w)+)?)+@(\\D)+(\\w)*((\\.(\\D)+(\\w)*)+)?(\\.)[a-z]{2,}$");
            if (firstName.getText().isEmpty()) {
                alert.errorMessage("FirstName Cannot be Empty");
            } else if (mobileNumber.getText().length() != 10) {
                alert.errorMessage("Mobile number should be 10 digits");
            } else if (!p.matcher(email.getText()).matches()) {
                alert.errorMessage("Please Enter Valid Email");
            } else if (password.getText().length() < 8) {
                alert.errorMessage("Password should be more than 8 characters");
            }
            Student s = new Student(firstName.getText(), lastName.getText(), mobileNumber.getText(), stringGender, email.getText(), password.getText());
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to Update Student  "+email.getText());
                Optional<ButtonType> option=alert.showAndWait();
                if(option.get().equals(ButtonType.OK))
                {
                    statement=connect.createStatement();
                    statement.executeUpdate(sql);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setContentText("Successfully updated ");
                    alert.showAndWait();
                    list=getDatausers();
                    studentTable.setItems(list);
                    resetForm();
                }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static ObservableList<Student> getDatausers()
    {
        connect=Database.ConnectDb();
        ObservableList<Student> list= FXCollections.observableArrayList();
        try
        {
            PreparedStatement ps=connect.prepareStatement("select * from student");
            ResultSet result=ps.executeQuery();
            while(result.next())
            {
                Student s = new Student();
                s.setId(result.getInt(1));
                s.setFirstName(result.getString(2));
                s.setLastName(result.getString(3));
                s.setMobile(result.getString(4));
                s.setEmail(result.getString(5));
                s.setPassword(result.getString(6));
                s.setGender(result.getString(7).charAt(0));
                list.add(s);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return  list;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        radioButtonSetup();
        RadioButtonSetUp();
        renderTable();
        renderTreeView();

    }
}

