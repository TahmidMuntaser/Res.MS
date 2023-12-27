package com.example.rms;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;


import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private TextField fp_answer;

    @FXML
    private Button fp_back;

    @FXML
    private ComboBox<?> fp_question;

    @FXML
    private AnchorPane fp_questionForm;

    @FXML
    private Button np_back;

    @FXML
    private TextField fp_username;

    @FXML
    private Button np_changePassBtn;

    @FXML
    private PasswordField np_confirmPassword;

    @FXML
    private AnchorPane np_newPassForm;

    @FXML
    private PasswordField np_newPassword;

    @FXML
    private Hyperlink si_forgotPass;

    @FXML
    private Button si_loginBtn;

    @FXML
    private Button si_loginBtn11;

    @FXML
    private AnchorPane si_loginForm;

    @FXML
    private PasswordField si_password;

    @FXML
    private TextField si_username;

    @FXML
    private Button side_CreateBtn;

    @FXML
    private Button side_alreadyHave;

    @FXML
    private AnchorPane side_form;

    @FXML
    private TextField su_answer;

    @FXML
    private PasswordField su_password;

    @FXML
    private ComboBox<?> su_question;

    @FXML
    private Button su_signupBtn;

    @FXML
    private AnchorPane su_signupForm;

    @FXML
    private TextField su_username;

    private Connection connect;
    private PreparedStatement prepare;
    private  ResultSet result;

    private Alert alert;



    public void loginBtn() throws SQLException {
        if(si_username.getText().isEmpty() || si_password.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!!");
            alert.setHeaderText(null);
            alert.setContentText("Enter Username/Password");
            alert.showAndWait();
        }

        else{
            String selectData = "SELECT username, password FROM employee WHERE username = ? and password = ?";
            connect = database.connectDB();

            try{
                prepare = connect.prepareStatement(selectData);
                prepare.setString(1, si_username.getText());
                prepare.setString(2, si_password.getText());

                result = prepare.executeQuery();

                // successfully login
                if(result.next()){
                    // To get the username that user used.
                    data.username = si_username.getText();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Info!!");
                    alert.setHeaderText(null);
                    alert.setContentText("Login Successful!!");
                    alert.showAndWait();

                    // Link your Main Form.
                    Parent root = FXMLLoader.load(getClass().getResource("mainForm.fxml"));

                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    stage.setTitle("Restaurant Management System");
                    stage.setMinWidth(1100);
                    stage.setMinHeight(600);

                    stage.setScene(scene);
                    stage.show();

                    si_loginBtn.getScene().getWindow().hide();

                }

                else{
                    // failed to login

                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error!!");
                    alert.setHeaderText(null);
                    alert.setContentText("Login Failed!!, Incorrect Username/Password");
                    alert.showAndWait();


                }



            }

            catch(Exception e){
                e.printStackTrace();

            }

        }
    }

    public void reqBtn() throws SQLException {
        if(su_username.getText().isEmpty() || su_password.getText().isEmpty() || su_question.getSelectionModel().getSelectedItem() == null || su_answer.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.getHeaderText();
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        }
        else {
            String regData = "INSERT INTO employee (username, password, question, answer, date)" + "VALUES(?,?,?,?,?)";
            connect = database.connectDB();

            try {

                // Check if the username is already recorded

                String checkUsername = "SELECT username FROM employee WHERE username =  '" + su_username.getText() + "'";
                prepare = connect.prepareStatement(checkUsername);
                result = prepare.executeQuery();

                if (result.next()){

                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.getHeaderText();
                    alert.setContentText(su_username.getText()+" is already exist!!!");
                    alert.showAndWait();

                }


                else if(su_password.getText().length()<8){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.getHeaderText();
                    alert.setContentText("your password must be min 8 letters long!!!");
                    alert.showAndWait();
                }

                else{
                    prepare = connect.prepareStatement(regData);
                    prepare.setString(1, su_username.getText());
                    prepare.setString(2, su_password.getText());
                    prepare.setString(3, (String) su_question.getSelectionModel().getSelectedItem());
                    prepare.setString(4,su_answer.getText());

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare.setString(5,String.valueOf(sqlDate));

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.getHeaderText();
                    alert.setContentText("Successfully registered Account!");
                    alert.showAndWait();

                    su_username.setText("");
                    su_password.setText("");
                    su_question.getSelectionModel().clearSelection();
                    su_answer.setText("");

                    TranslateTransition slider = new TranslateTransition();

                    slider.setNode(side_form);
                    slider.setToX(0);
                    slider.setDuration(Duration.seconds(.5));
                    slider.setOnFinished((ActionEvent e) -> {
                        side_alreadyHave.setVisible(false);
                        side_CreateBtn.setVisible(true);
                    });

                    slider.play();

                }


            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private String[] questionList = {"What is your favautite color?", "What is your favourite food?", "what is your birth date?"};
    public void regLquestionList(){
        List<String> listQ = new ArrayList<>();

        for (String data: questionList){
            listQ.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listQ);
        su_question.setItems(listData);
    }



    public void switchForgotPass(){
        fp_questionForm.setVisible(true);
        si_loginForm.setVisible(false);

        forgotPassQuestionList();
    }

    public void proceedBtn() throws SQLException {

        if(fp_username.getText().isEmpty() || fp_question.getSelectionModel().getSelectedItem() == null || fp_answer.getText().isEmpty()){

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.getHeaderText();
            alert.setContentText("Please, fill all blank field!!!");
            alert.showAndWait();

        }

        else{

            String selectData = "SELECT username ,question, answer FROM employee WHERE username = ? AND question = ? AND answer = ?";
            connect = database.connectDB();
            try{
                prepare = connect.prepareStatement(selectData);
                prepare.setString(1, fp_username.getText());
                prepare.setString(2, (String) fp_question.getSelectionModel().getSelectedItem());
                prepare.setString(3, fp_answer.getText());

                result = prepare.executeQuery();
                if (result.next()){
                    np_newPassForm.setVisible(true);
                    fp_questionForm.setVisible(false);

                }
                else{

                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.getHeaderText();
                    alert.setContentText("Incorrect Info!!!");
                    alert.showAndWait();

                }


            }

            catch (Exception e){
               e.printStackTrace();

            }


        }


    }

    public void changePassBtn() throws SQLException {
        if(np_newPassword.getText().isEmpty() || np_confirmPassword.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.getHeaderText();
            alert.setContentText("Please, fill all blank field!!!");
            alert.showAndWait();
        }

        else{

            if(np_newPassword.getText().equals(np_confirmPassword.getText())){
                String getDate = "SELECT date FROM employee WHERE username = '" + fp_username.getText() + "'";
                connect = database.connectDB();

                try{

                    prepare = connect.prepareStatement(getDate);
                    result = prepare.executeQuery();
                    String date = "";

                    if(result.next()){
                        date = result.getString("date");
                    }


                    String updatePass = "UPDATE employee SET password = '" + np_newPassword.getText()+ "', question = '" +
                            fp_question.getSelectionModel().getSelectedItem() + "', answer = '" +
                            fp_answer.getText() + "', date = '" + date + "' WHERE username = '" +
                            fp_username.getText()+ "'";

                    prepare = connect.prepareStatement(updatePass);
                    prepare.executeUpdate();


                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Info Message");
                    alert.getHeaderText();
                    alert.setContentText("Password changed!!!");
                    alert.showAndWait();



                    si_loginForm.setVisible(true);
                    np_newPassForm.setVisible(false);

                    //clear field

                    np_confirmPassword.setText("");
                    np_newPassword.setText("");
                    fp_question.getSelectionModel().clearSelection();

                    fp_answer.setText("");
                    fp_username.setText("");




                }
                catch (Exception e){
                    e.printStackTrace();
                }


            }

            else{

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.getHeaderText();
                alert.setContentText("Not Match!!!");
                alert.showAndWait();

            }



        }
    }


    public void forgotPassQuestionList(){
        List<String> listQ = new ArrayList<>();

        for (String data: questionList){

            listQ.add(data);
        }

        ObservableList listdata = FXCollections.observableArrayList(listQ);
        fp_question.setItems(listdata);
    }



    public void backToLoginForm(){
        si_loginForm.setVisible(true);
        fp_questionForm.setVisible(false);


    }

    public void backToQuestionForm(){
        fp_questionForm.setVisible(true);
        np_newPassForm.setVisible(false);
    }


    public void switchForm(ActionEvent event){

        TranslateTransition slider = new TranslateTransition();

        if(event.getSource()  == side_CreateBtn){
            slider.setNode(side_form);
            slider.setToX(300);
            slider.setDuration(Duration.seconds(.5));
            slider.setOnFinished((ActionEvent e) -> {
                side_alreadyHave.setVisible(true);
                side_CreateBtn.setVisible(false);

                si_loginForm.setVisible(false);
                si_loginForm.setVisible(true);
                np_newPassForm.setVisible(false);

                regLquestionList();
            });

            slider.play();
        }

        else if (event.getSource() == side_alreadyHave){
            slider.setNode(side_form);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(.5));
            slider.setOnFinished((ActionEvent e) -> {
                side_alreadyHave.setVisible(false);
                side_CreateBtn.setVisible(true);

                si_loginForm.setVisible(false);
                si_loginForm.setVisible(true);
                np_newPassForm.setVisible(false);


            });

            slider.play();

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}