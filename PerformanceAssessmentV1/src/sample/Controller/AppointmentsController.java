package sample.Controller;

import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.DBAccess.DBAppointments;
import sample.Model.Appointments;
import sample.Model.Customers;

import javafx.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentsController implements Initializable {
    @FXML
    private RadioButton AppointmentsMonthRButton;
    @FXML
    private RadioButton AppointmentsAllRadioButton;
    @FXML
    private TableView<Appointments> AppointmentsTable;
    @FXML
    private TableColumn AppointmentsIDCol;
    @FXML
    private TableColumn AppointmentsTitleCol;
    @FXML
    private TableColumn AppointmentsDescriptionCol;
    @FXML
    private TableColumn AppointmentsLocationCol;
    @FXML
    private TableColumn AppointmentsTypeCol;
    @FXML
    private TableColumn AppointmentsStartCol;
    @FXML
    private TableColumn AppointmentsEndCol;
    @FXML
    private TableColumn AppointmentsCustomerCol;
    @FXML
    private TableColumn AppointmentsUserCol;
    @FXML
    private TableColumn AppointmentsContactCol;
    @FXML
    private ToggleGroup ViewByToggleGroup;
    @FXML
    private RadioButton AppointmentsWeekRButton;
    @FXML
    private Button AppointmentsCreateButton;
    @FXML
    private Button AppointmentsUpdateButton;
    @FXML
    private Button AppointmentsDeleteButton;
    @FXML
    private Button AppointmentsBackButton;

    private ObservableList<Appointments> appointments = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointments = DBAppointments.getAll();
        AppointmentsTable.setItems(appointments);
        AppointmentsIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        AppointmentsTitleCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        AppointmentsDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        AppointmentsLocationCol.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        AppointmentsTypeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        AppointmentsStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        AppointmentsEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        AppointmentsCustomerCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        AppointmentsUserCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        AppointmentsContactCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
    }

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


    public void createButtonHandler(ActionEvent e) {
        try {
            URL url =new File( "src/sample/View/CreateAppointment.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene createAppointmentScreen = new Scene(root, 800, 400);
            Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            stage.setScene(createAppointmentScreen);
            stage.show();
        }catch (IOException exc){
            exc.printStackTrace();
        }
    }

    public void updateButtonHandler(ActionEvent e) {
        Appointments a = AppointmentsTable.getSelectionModel().getSelectedItem();
        if(a != null){
            try {
                URL url =new File( "src/sample/View/UpdateAppointment.fxml").toURI().toURL();

                FXMLLoader loader = new FXMLLoader(url);
                Parent root = loader.load();
                UpdateAppointmentController controller = loader.getController();
                controller.sendAppointmentData(a);
                Scene updateAppointmentScreen = new Scene(root, 800, 400);
                Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
                stage.setScene(updateAppointmentScreen);
                stage.show();
            }catch (IOException exc){
                exc.printStackTrace();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Confirmation Dialog with Modification Actions");
            alert.setHeaderText(null);
            alert.setContentText("Selection error, please try again");
            alert.showAndWait();
        }
    }

    public void deleteButtonHandler(ActionEvent e) {
        Appointments a = AppointmentsTable.getSelectionModel().getSelectedItem();
        if (a != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete the selected appointment?");
            ButtonType yesButton = new ButtonType("Yes");
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(yesButton, noButton);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == yesButton) {
                DBAppointments.deleteAppointment(a.getAppointmentID());
                appointments = DBAppointments.getAll();
                AppointmentsTable.setItems(appointments);
                Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                alert3.setTitle("Process Complete");
                alert3.setHeaderText(null);
                alert3.setContentText("Appointment has been deleted.");
            }

        }
        else{
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Error");
            alert2.setHeaderText(null);
            alert2.setContentText("Error! Please try again.");
            alert2.showAndWait();
        }


    }

    public void allRadioButtonHandler(ActionEvent e) {
        ObservableList<Appointments> ap = FXCollections.observableArrayList();
        ap = DBAppointments.getAll();
        AppointmentsTable.setItems(ap);
    }

    public void monthRadioButtonHandler(ActionEvent e) {
        ObservableList<Appointments> ap = FXCollections.observableArrayList();
        ap = DBAppointments.getLastMonth();
        AppointmentsTable.setItems(ap);
    }

    public void weekRadioButtonHandler(ActionEvent e) {
        ObservableList<Appointments> ap = FXCollections.observableArrayList();
        ap = DBAppointments.getLastWeek();
        AppointmentsTable.setItems(ap);
    }




}
