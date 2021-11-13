package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.DBAccess.DBAppointments;
import sample.DBAccess.DBContacts;
import sample.DBAccess.DBCustomers;
import sample.DBAccess.DBUsers;
import sample.Model.Appointments;
import sample.Model.Contacts;
import sample.Model.Customers;
import sample.Model.Users;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.util.ResourceBundle;

public class CreateAppointmentController implements Initializable {
    @FXML
    private ComboBox<Integer> CreateAppointmentCustomerComboBox;
    @FXML
    private ComboBox<Integer> CreateAppointmentUserComboBox;
    @FXML
    private Button CreateAppointmentCreateButton;
    @FXML
    private Button CreateAppointmentBackButton;
    @FXML
    private TextField CreateAppointmentIDTextField;
    @FXML
    private TextField CreateAppointmentTitleTextField;
    @FXML
    private TextField CreateAppointmentDescriptionTextField;
    @FXML
    private TextField CreateAppointmentLocationTextField;
    @FXML
    private TextField CreateAppointmentTypeTextField;
    @FXML
    private TextField CreateAppointmentCustomerTextField;
    @FXML
    private TextField CreateAppointmentUserTextField;
    @FXML
    private ComboBox<Contacts> CreateAppointmentContactComboBox;
    @FXML
    private DatePicker CreateAppointmentStartDatePicker;
    @FXML
    private DatePicker CreateAppointmentEndDatePicker;
    @FXML
    private ComboBox CreateAppointmentStartComboBox;
    @FXML
    private ComboBox CreateAppointmentEndComboBox;

    private ObservableList<Contacts> contacts = FXCollections.observableArrayList();
    private ObservableList<Customers> customers = FXCollections.observableArrayList();
    private ObservableList<Users> users = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contacts = DBContacts.getAll();
        CreateAppointmentContactComboBox.setItems(contacts);
        CreateAppointmentContactComboBox.setVisibleRowCount(5);

        LocalTime startBusiness = LocalTime.of(8, 0);
        LocalTime endBusiness = LocalTime.of(22,0);
        while(startBusiness.isBefore(endBusiness.plusSeconds(1))){
            CreateAppointmentStartComboBox.getItems().add(startBusiness);
            CreateAppointmentEndComboBox.getItems().add(startBusiness);
            startBusiness = startBusiness.plusMinutes(30);
        }

        customers = DBCustomers.getAll();
        users = DBUsers.getAll();
        for(Customers c: customers){
            CreateAppointmentCustomerComboBox.getItems().add(c.getCustomerID());
        }
        for(Users u: users){
            CreateAppointmentUserComboBox.getItems().add(u.getUserID());
        }
    }
    private boolean timeChecker(LocalDateTime st, LocalDateTime en){
        boolean checker = true;
        ZonedDateTime startInEst = ZonedDateTime.of(st, ZoneId.of("America/New_York"));
        ZonedDateTime endInEst = ZonedDateTime.of(en, ZoneId.of("America/New_York"));
        LocalTime startTimeInEst = startInEst.toLocalTime();
        LocalTime endTimeInEst = endInEst.toLocalTime();
        LocalDate startDateInEst = startInEst.toLocalDate();
        LocalDate endDateInEst = endInEst.toLocalDate();
        final LocalTime startBusiness = LocalTime.of(8,0);
        final LocalTime endBusiness = LocalTime.of(22,0);
        ObservableList<Appointments> appointments = FXCollections.observableArrayList();
        appointments = DBAppointments.getAll();

        //checking for overlapping appointments
        for(Appointments a : appointments) {
            if(a.getCustomerID() == (int) CreateAppointmentCustomerComboBox.getSelectionModel().getSelectedItem()) {
                ZonedDateTime t1 = ZonedDateTime.of(a.getStart(), ZoneId.of("America/New_York"));
                ZonedDateTime t2 = ZonedDateTime.of(a.getEnd(), ZoneId.of("America/New_York"));
                if((startInEst.isAfter(t1) || startInEst.equals(t1)) && startInEst.isBefore(t2)) {
                    checker = false;
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Overlapping schedule.");
                    alert.showAndWait();
                }
                else if(endInEst.isAfter(t1) && (endInEst.isBefore(t2) || endInEst.equals(t2))) {
                    checker = false;
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Overlapping schedule.");
                    alert.showAndWait();
                }
                else if((startInEst.isBefore(t1) || startInEst.equals(t1)) &&
                        (endInEst.isAfter(t2) || endInEst.equals(t2))) {
                    checker = false;
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Overlapping schedule.");
                    alert.showAndWait();
                }
                else {
                    checker = true;
                }
            }
        }

        if(st.isAfter(en)){
            checker = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("End time needs to be after start time.");
            alert.showAndWait();
        }
        if(!startDateInEst.equals(endDateInEst)){
            checker = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Start date and end date need to be the same.");
            alert.showAndWait();
        }
        if(startTimeInEst.isBefore(startBusiness) || endTimeInEst.isAfter(endBusiness) ||
        startTimeInEst.isAfter(endBusiness) || endTimeInEst.isBefore(startBusiness)) {
            checker = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please schedule appointment within 8:00 AM to 10:00 PM EST.");
            alert.showAndWait();
        }
        return checker;
    }

    public void backButtonHandler(ActionEvent e) {
        try {
            URL url =new File( "src/sample/View/Appointments.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene CustomersScreen = new Scene(root, 800, 375);
            Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            stage.setScene(CustomersScreen);
            stage.show();
        }catch (IOException exc){
            exc.printStackTrace();
        }
    }

    public void createAppointment(ActionEvent e){
        try {
            String aTitle = CreateAppointmentTitleTextField.getText();
            String aDescription = CreateAppointmentDescriptionTextField.getText();
            String aLocation = CreateAppointmentLocationTextField.getText();
            String aType = CreateAppointmentTypeTextField.getText();
            int custID = CreateAppointmentCustomerComboBox.getSelectionModel().getSelectedItem();
            int uID = CreateAppointmentUserComboBox.getSelectionModel().getSelectedItem();
            int conID = CreateAppointmentContactComboBox.getSelectionModel().getSelectedItem().getContactID();
            LocalTime startTime = LocalTime.parse(CreateAppointmentStartComboBox.getSelectionModel().getSelectedItem().toString());
            LocalTime endTime = LocalTime.parse(CreateAppointmentEndComboBox.getSelectionModel().getSelectedItem().toString());
            LocalDate startDate = CreateAppointmentStartDatePicker.getValue();
            LocalDate endDate = CreateAppointmentEndDatePicker.getValue();
            LocalDateTime start = LocalDateTime.of(startDate, startTime);
            LocalDateTime end = LocalDateTime.of(endDate, endTime);
            LocalDateTime st = ZonedDateTime.of(start, ZoneId.of("UTC")).toLocalDateTime();
            LocalDateTime en = ZonedDateTime.of(end, ZoneId.of("UTC")).toLocalDateTime();

            //nullpointerexception has to be handled
            if (aTitle.isEmpty() || aDescription.isEmpty() || aLocation.isEmpty()
                    || aType.isEmpty() || CreateAppointmentCustomerComboBox.getSelectionModel().isEmpty()
                    || CreateAppointmentUserComboBox.getSelectionModel().isEmpty() || CreateAppointmentContactComboBox.getSelectionModel().isEmpty()
                    || CreateAppointmentStartComboBox.getSelectionModel().isEmpty() || CreateAppointmentEndComboBox.getSelectionModel().isEmpty()
                    || CreateAppointmentStartDatePicker.getValue() == null || CreateAppointmentEndDatePicker.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error! Please fill in all fields.");
                alert.showAndWait();
            } else {
                if (timeChecker(start, end)) {
                    DBAppointments.createAppointment(aTitle, aDescription, aLocation, aType, st, en, custID, uID, conID);
                    try {
                        URL url = new File("src/sample/View/Appointments.fxml").toURI().toURL();
                        Parent root = FXMLLoader.load(url);
                        Scene CustomersScreen = new Scene(root, 800, 375);
                        Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
                        stage.setScene(CustomersScreen);
                        stage.show();
                    } catch (IOException exc) {
                        exc.printStackTrace();
                    }
                }
            }
        }catch (NumberFormatException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Entry error. Please make sure all fields are entered with correct data type.");
            alert.showAndWait();
        }catch (NullPointerException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Entry error. Please make sure all fields are entered with correct data type.");
            alert.showAndWait();
        }
    }


}
