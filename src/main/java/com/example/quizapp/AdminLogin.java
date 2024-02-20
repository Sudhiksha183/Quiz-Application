package com.example.quizapp;

import com.example.quizapp.Constants.AdminUsernamePassword;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.security.auth.login.LoginException;

public class AdminLogin {
    @FXML
    private Button stu_login_btn1;
    @FXML
    private PasswordField login_password1;
    @FXML
    private Hyperlink AdminLoginLabel;

    @FXML
    private AnchorPane Login_stu_password;

    @FXML
    private TextField Login_student_username;

    @FXML
    private Hyperlink StudentLoginLabel1;
    @FXML
    private Button login_btn;
    @FXML
    private AnchorPane studentLogin_form;
    @FXML
    private AnchorPane login_form;

    @FXML
    private PasswordField login_password;
    @FXML
    void switchForm(ActionEvent event) {
     login_form.setVisible(false);
     studentLogin_form.setVisible(true);
    }
   /* void switchAdmin(ActionEvent event) {
        login_form.setVisible(true);
        studentLogin_form.setVisible(false);
    }*/


    @FXML
    private TextField login_username;
    private  AlertMessage alert=new AlertMessage();
    @FXML
    void loginAdmin(ActionEvent event) {
     String username=login_username.getText();
     String password=login_password.getText();
     if(username.trim().equalsIgnoreCase(AdminUsernamePassword.username) && password.trim().equals(AdminUsernamePassword.password))
        {
           // System.out.println("Success");
            try {
                Parent root = FXMLLoader.load(getClass().getResource("adminHome.fxml"));

                Stage stage = new Stage();
                stage.setTitle("Quick Quiz | Admin Portal");
                stage.setScene(new Scene(root));
                stage.show();
                login_btn.getScene().getWindow().hide();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
         else
        {
            alert.errorMessage("Username Or Password Invalid");
        }
    }


    public void switchAdmin(ActionEvent event) {
        login_form.setVisible(true);
        studentLogin_form.setVisible(false);
    }

    @FXML
    void loginStudent(ActionEvent event) {
        Student s=new Student(this.Login_student_username.getText(), this.login_password1.getText());
        try{
            s.login();
            try {
                FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("studentmainscreen.fxml"));
                Parent root=fxmlLoader.load();
                StudentMainScreen controller=fxmlLoader.getController();
                controller.setStudent(s);
                Stage stage = new Stage();
                stage.setTitle("Quick Quiz | Admin Portal");
                stage.setScene(new Scene(root));
                stage.show();
                stu_login_btn1.getScene().getWindow().hide();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }catch (Exception e)
        {
            if(e instanceof LoginException)
            {
                alert.errorMessage("Login Password");
            }
        }
    }
}
