package sample.Controller;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.DBAccess.DBUsers;
import sample.Model.LoginAttempts;
import sample.Model.Users;

import javafx.event.ActionEvent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Label LoginUsernameLabel;
    @FXML
    private Label LoginPasswordLabel;
    @FXML
    private Label LoginLocationLabel;
    @FXML
    private TextField LoginUserNameTextField;
    @FXML
    private TextField LoginPasswordTextField;
    @FXML
    private Button LoginLoginButton;
    @FXML
    private Button LoginExitButton;
    @FXML
    private Label LoginLocationDisplayer;

    private static String userName = null;

    ResourceBundle rb = ResourceBundle.getBundle("sample/Language", Locale.getDefault());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        locationDisplayer();
        if(languageChecker()) {
            LoginLocationLabel.setText(rb.getString("Location"));
            LoginUsernameLabel.setText(rb.getString("Username"));
            LoginPasswordLabel.setText(rb.getString("Password"));
            LoginLoginButton.setText(rb.getString("Login"));
            LoginExitButton.setText(rb.getString("Exit"));
        }
    }

    private boolean languageChecker() {
        if(Locale.getDefault().getLanguage().equals("fr")
                || Locale.getDefault().getLanguage().equals("en")){
            return true;
        }
        else return false;
    }

    public void exitButtonHandler(ActionEvent e){
        if(languageChecker()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(rb.getString("ConfirmationDialog"));
            alert.setHeaderText(null);
            alert.setContentText(rb.getString("AreYouSureYouWantToExit?"));
            ButtonType yesButton = new ButtonType(rb.getString("Yes"));
            ButtonType noButton = new ButtonType(rb.getString("No"), ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(yesButton, noButton);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == yesButton) {
                System.exit(0);
            } else
                return;
        }
    }



    private boolean passwordChecker(String u) {
        String entry = LoginPasswordTextField.getText();
        String p = DBUsers.getPassword(u);
        if(entry.equals(p)){
            return true;
        }
        else {
            return false;
        }
    }


    public void loginButtonHandler(ActionEvent e) {
        String username = LoginUserNameTextField.getText();
        ObservableList<Users> u = FXCollections.observableArrayList();
        u = DBUsers.getAll();
        String matchingUserName = "";
        for(Users user : u){
            if(username.equals(user.getUserName())){
                matchingUserName = username;
            }
        }
        if(matchingUserName.isEmpty()){
            if(languageChecker()) {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Error");
                alert2.setHeaderText(null);
                alert2.setContentText("InvalidUsername");
                alert2.showAndWait();
            }
        }
        else{

            if(passwordChecker(matchingUserName)) {
                //Login successful
                userName = matchingUserName;
                loginAttempts(true);
                try {
                    URL url =new File( "src/sample/View/Home.fxml").toURI().toURL();
                    Parent root = FXMLLoader.load(url);
                    Scene homeScreen = new Scene(root, 400, 250);
                    Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
                    stage.setScene(homeScreen);
                    stage.show();
                }catch (IOException ex){
                    ex.printStackTrace();
                }
            }
            else{
                loginAttempts(false);
                if(languageChecker()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("InvalidPassword");
                    alert.showAndWait();
                }
            }
        }



    }

    public void locationDisplayer(){
        Locale locale = Locale.getDefault();
        if(locale.toString().equals("en_CA")){
            LoginLocationDisplayer.setText("Canada");
        }
        else if(locale.toString().equals("en_GB")){
            LoginLocationDisplayer.setText("Great Britain");
        }
        else if(locale.toString().equals("en_US")){
            LoginLocationDisplayer.setText("United States");
        }
        else if(locale.toString().equals("fr_CA")){
            LoginLocationDisplayer.setText("Canada");
        }
        else if(locale.toString().equals("fr_FR")){
            LoginLocationDisplayer.setText("France");
        }
        else{
            LoginLocationDisplayer.setText("Unknown");
        }
    }

    public static String getUserName(){
        return userName;
    }


    public void loginAttempts(boolean loginSuccess) {
        try {
            FileWriter fileWriter = new FileWriter("login_activity.txt", true);
            BufferedWriter loginAttempt = new BufferedWriter(fileWriter);
            LocalDateTime loginDateTime = LocalDateTime.now();
            if(loginSuccess == true) {
                LoginAttempts success = new LoginAttempts(DBUsers.getIDByUsername(userName),
                        loginDateTime, true);
                loginAttempt.append(success.toString());
            }
            else{
                LoginAttempts failure = new LoginAttempts(-1, loginDateTime, false);
                loginAttempt.append(failure.toString());
            }
        }catch(IOException exc) {
            exc.printStackTrace();
        }
    }
}
