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
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.DBAccess.DBAppointments;
import sample.Model.Appointments;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Report1Controller implements Initializable {
    @FXML
    private ComboBox Report1MonthComboBox;
    @FXML
    private ComboBox Report1TypeComboBox;
    @FXML
    private Label Report1ResultLabel;
    @FXML
    private Button Report1CalculateButton;
    @FXML
    private Button Report1BackButton;

    private ObservableList<Appointments> appointments = FXCollections.observableArrayList();
    private ObservableList<Month> months = FXCollections.observableArrayList();
    //private ObservableList<LocalDateTime> dateTimes = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointments = DBAppointments.getAll();
        for(Appointments a : appointments) {
            Report1TypeComboBox.getItems().add(a.getAppointmentType());
            months.add(a.getStart().getMonth());
            //dateTimes.add(a.getStart());
        }
        List<Month> uniqueMonth = months.stream().distinct().collect(Collectors.toCollection(ArrayList::new));
        Report1MonthComboBox.setItems(FXCollections.observableArrayList(uniqueMonth));
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

    public void calculateButtonHandler(ActionEvent e) {
        String month = Report1MonthComboBox.getSelectionModel().getSelectedItem().toString();
        String type = Report1TypeComboBox.getSelectionModel().getSelectedItem().toString();
        int i = 0;
        for(Appointments a : appointments) {
            if(a.getStart().getMonth().toString().equals(month) && a.getAppointmentType().equals(type)) {
                i=+ 1;
            }
        }

        Report1ResultLabel.setText(Integer.toString(i));
    }
}
