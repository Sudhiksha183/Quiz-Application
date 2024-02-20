package com.example.quizapp;

import javax.security.auth.login.LoginException;
import java.sql.*;
import java.util.ArrayList;

public class Student {
    private Integer id;
    private String firstName;
    private String lastName;
    private String mobile;
    private Character gender;
    private String email;
    private String password;
    private static Connection connect;
    private static PreparedStatement prepare;
    private static ResultSet result;
    private Statement statement;

    public static class MetaData {
        public static final String TABLE_NAME = "student";
        public static final String ID = "ID";
        public static final String MOBILE = "mobile";
        public static final String LAST_NAME = "lastname";
        public static final String FIRST_NAME = "firstName";
        public static final String GENDER = "gender";
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
    }

    public Student(String firstName, String lastName, String mobile, Character gender, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.gender = gender;
        this.email = email;
        this.password = password;
    }

    public Student(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Student(){

    }

    public Student(Integer id, String firstName, String lastName, String mobile, Character gender, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.gender = gender;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", gender=" + gender +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public Character getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }



    // save student

    public Student save(){
        String raw = "INSERT into student (%s , %s , %s , %s , %s ,%s )\n" +
                "values ( ? , ? , ? , ? , ? , ?);";

        String query = String.format(raw ,
                MetaData.FIRST_NAME ,
                MetaData.LAST_NAME ,
                MetaData.MOBILE ,
                MetaData.EMAIL ,
                MetaData.PASSWORD ,
                MetaData.GENDER
        );
        connect=Database.ConnectDb();
        try
        {
            prepare = connect.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
                prepare.setString(1 , this.firstName );
                prepare.setString(2 , this.lastName );
                prepare.setString(3 , this.mobile );
                prepare.setString(4 , this.email );
                prepare.setString(5 , this.password );
                prepare.setString(6 , String.valueOf(this.gender));
                int i = prepare.executeUpdate();
                ResultSet keys = prepare.getGeneratedKeys();
                if(keys.next()){
                    this.id  = keys.getInt(1);
                }
                //System.out.println("Updated Rows : " + i);
                return this;
            }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Student> getAll(){
        ArrayList<Student> students =  new ArrayList<>();
        String query = String.format("select %s ," +
                        " %s , " +
                        "%s ," +
                        " %s , " +
                        "%s , " +
                        "%s  , %s from %s ;" ,
                MetaData.ID ,
                MetaData.FIRST_NAME ,
                MetaData.LAST_NAME ,
                MetaData.MOBILE ,
                MetaData.EMAIL,
                MetaData.PASSWORD,
                MetaData.GENDER ,
                MetaData.TABLE_NAME
        );
        connect=Database.ConnectDb();
        try
        {
            prepare = connect.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            result = prepare.executeQuery();
                while(result.next()){
                    Student s = new Student();
                    s.setId(result.getInt(1));
                    s.setFirstName(result.getString(2));
                    s.setLastName(result.getString(3));
                    s.setMobile(result.getString(4));
                    s.setEmail(result.getString(5));
                    s.setPassword(result.getString(6));
                    s.setGender(result.getString(7).charAt(0));
                    students.add(s);
                }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return students;
    }


    public boolean isExists(){

        String query = String.format("select * from %s where %s = ?;" ,
                MetaData.TABLE_NAME ,
                MetaData.EMAIL
        );
        connect=Database.ConnectDb();
        try
        {
            prepare = connect.prepareStatement(query);
            prepare.setString(1 , this.email );
            ResultSet result = prepare.executeQuery();
                if(result.next()){
                    return true;
                }}catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }


    public void login() throws SQLException, ClassNotFoundException , LoginException {

        String query = String.format("select %s , %s , %s, %s , %s " +
                        " from %s where %s = ? and %s = ?;" ,
                MetaData.ID ,
                MetaData.FIRST_NAME ,
                MetaData.LAST_NAME ,
                MetaData.MOBILE ,
                MetaData.GENDER ,
                MetaData.TABLE_NAME ,
                MetaData.EMAIL,
                MetaData.PASSWORD
        );
        connect=Database.ConnectDb();
        try {
            prepare = connect.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            prepare.setString(1 , this.email );
            prepare.setString(2 , this.password );
            result = prepare.executeQuery();
            if(result.next()){
                this.setId(result.getInt(1));
                this.setFirstName(result.getString(2));
                this.setLastName(result.getString(3));
                this.setMobile(result.getString(4));
                this.setGender(result.getString(5).charAt(0));
            }else{
               AlertMessage alertMessage=new AlertMessage();
               alertMessage.errorMessage("Login Failed");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }



}
