package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class AllReportController {
    @FXML
    private Button AllReportReport1Button;
    @FXML
    private Button AllReportReport3Button;
    @FXML
    private Button AllReportBackButton;
    @FXML
    private Button AllReportReport2Button;

    public void backButtonHandler(ActionEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to go back to home page?");
        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yesButton,noButton);
        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == yesButton) {
            try {
                URL url =new File( "src/sample/View/Home.fxml").toURI().toURL();
                Parent root = FXMLLoader.load(url);
                Scene homeScreen = new Scene(root, 400, 250);
                Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
                stage.setScene(homeScreen);
                stage.show();
            }catch (IOException exc){
                exc.printStackTrace();
            }
        }
        else
            return;
    }

    public void report1ButtonHandler(ActionEvent e){
        try {
            URL url =new File( "src/sample/View/Report1.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene homeScreen = new Scene(root, 400, 300);
            Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            stage.setScene(homeScreen);
            stage.show();
        }catch (IOException exc){
            exc.printStackTrace();
        }
    }

    public void report2ButtonHandler(ActionEvent e){
        try {
            URL url =new File( "src/sample/View/Report2.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene homeScreen = new Scene(root, 800, 400);
            Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            stage.setScene(homeScreen);
            stage.show();
        }catch (IOException exc){
            exc.printStackTrace();
        }
    }

    public void report3ButtonHandler(ActionEvent e) {
        //show report 3
        try {
            URL url =new File( "src/sample/View/Report3.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene homeScreen = new Scene(root, 450, 300);
            Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            stage.setScene(homeScreen);
            stage.show();
        }catch (IOException exc){
            exc.printStackTrace();
        }
    }
}
