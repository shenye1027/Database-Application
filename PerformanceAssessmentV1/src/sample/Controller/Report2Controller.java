package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.DBAccess.DBAppointments;
import sample.DBAccess.DBContacts;
import sample.Model.Appointments;
import sample.Model.Contacts;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Report2Controller implements Initializable {
    @FXML
    private TableView Report2Table;
    @FXML
    private TableColumn Report2AppointmentIDCol;
    @FXML
    private TableColumn Report2TitleCol;
    @FXML
    private TableColumn Report2TypeCol;
    @FXML
    private TableColumn Report2DescriptionCol;
    @FXML
    private TableColumn Report2StartCol;
    @FXML
    private TableColumn Report2EndCol;
    @FXML
    private TableColumn Report2CustomerIDCol;
    @FXML
    private ComboBox<Contacts> Report2ContactNameComboBox;
    @FXML
    private Button Report2BackButton;

    private ObservableList<Appointments> appointments = FXCollections.observableArrayList();
    private ObservableList<Contacts> contacts = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contacts = DBContacts.getAll();
        Report2ContactNameComboBox.setItems(contacts);
        Report2ContactNameComboBox.setVisibleRowCount(5);

        appointments = DBAppointments.getAll();
        Report2Table.setItems(appointments);
        Report2AppointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        Report2TitleCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        Report2TypeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        Report2DescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        Report2StartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        Report2EndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        Report2CustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
    }

    public void backButtonHandler(ActionEvent e) {
        try {
            URL url =new File( "src/sample/View/AllReport.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene CustomersScreen = new Scene(root, 400, 250);
            Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            stage.setScene(CustomersScreen);
            stage.show();
        }catch (IOException exc){
            exc.printStackTrace();
        }
    }

    public void contactNameComboBoxHandler(ActionEvent e) {
        int conID = Report2ContactNameComboBox.getSelectionModel().getSelectedItem().getContactID();
        ObservableList<Appointments>  alist = DBAppointments.getAppointmentsByContactID(conID);
        Report2Table.setItems(alist);
    }

}
