package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import sample.DBAccess.DBAppointments;
import sample.DBAccess.DBUsers;
import sample.Model.Appointments;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    private Label HomeAppointmentAlert;
    @FXML
    private Button HomeCustomerButton;
    @FXML
    private Button HomeAppointmentButton;
    @FXML
    private Label HomeUsernameDisplayer;
    @FXML
    private Button HomeLogoutButton;
    @FXML
    private Button HomeReportButton;

    public void logoutButtonHandler(ActionEvent e){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to logout?");
        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yesButton,noButton);
        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == yesButton) {
            System.exit(0);
        }
        else
            return;
    }

    public void userNameDisplayer(){
        HomeUsernameDisplayer.setText(LoginController.getUserName());
    }

    public void customerButtonHandler(ActionEvent e){
        //go to customer
        try {
            URL url =new File( "src/sample/View/Customers.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene customerScreen = new Scene(root, 600, 375);
            Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            stage.setScene(customerScreen);
            stage.show();
        }catch (IOException exc){
            exc.printStackTrace();
        }
    }

    public void appointmentButtonHandler(ActionEvent e) {
        // go to appointment
        try{
            URL url =new File( "src/sample/View/Appointments.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene appointmentScreen = new Scene(root,800,375);
            Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            stage.setScene(appointmentScreen);
            stage.show();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void reportButtonHandler(ActionEvent e){
        try {
            URL url =new File( "src/sample/View/AllReport.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene homeScreen = new Scene(root, 400, 250);
            Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            stage.setScene(homeScreen);
            stage.show();
        }catch (IOException exc){
            exc.printStackTrace();
        }
    }

    public void appointmentAlert() {
        LocalDateTime now = LocalDateTime.now();
        LocalDate nowDate = now.toLocalDate();
        LocalTime nowTime = now.toLocalTime();
        ObservableList<Appointments> app = FXCollections.observableArrayList();
        app = DBAppointments.getAll();
        ObservableList<Appointments> appForTheDay = FXCollections.observableArrayList();
        for(Appointments a : app) {
            if(a.getUserID() == DBUsers.getIDByUsername(LoginController.getUserName())
            && a.getStart().toLocalDate() == nowDate) {
                appForTheDay.add(a);
            }
        }
        if(!appForTheDay.isEmpty()) {
            for(Appointments a : appForTheDay) {
                if(ChronoUnit.MINUTES.between(nowTime, a.getStart().toLocalTime()) <= 15
                && ChronoUnit.MINUTES.between(nowTime, a.getStart().toLocalTime()) >= -15) {
                    HomeAppointmentAlert.setText("You have an appointment within 15 minutes"
                    + "Appointment_ID: " + a.getAppointmentID() + "\nAppointment Time: "
                    + a.getStart());
                }
                else{
                    HomeAppointmentAlert.setText("You have no upcoming appointment.");
                }
            }
        }
        else{
            HomeAppointmentAlert.setText("You have no upcoming appointment.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userNameDisplayer();
        appointmentAlert();
    }
}
