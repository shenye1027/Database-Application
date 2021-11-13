package sample.Controller;

import com.mysql.cj.protocol.FullReadInputStream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.DBAccess.DBCountries;
import sample.DBAccess.DBCustomers;
import sample.DBAccess.DBFirstLevelDivisions;
import sample.Model.Countries;
import sample.Model.FirstLevelDivisions;

import javafx.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateCustomerController implements Initializable {
    @FXML
    private TextField CreateCustomerNameTextField;
    @FXML
    private TextField CreateCustomerAddressTextField;
    @FXML
    private TextField CreateCustomerPostalTextField;
    @FXML
    private TextField CreateCustomerPhoneTextField;
    @FXML
    private TextField CreateCustomerIDTextField;
    @FXML
    private ComboBox<Countries> CreateCustomerCountryComboBox;
    @FXML
    private ComboBox<FirstLevelDivisions> CreateCustomerDivisionComboBox;
    @FXML
    private Button CreateCustomerCreateButton;
    @FXML
    private Button CreateCustomerBackButton;

    ObservableList<Countries> countries = FXCollections.observableArrayList();
    ObservableList<FirstLevelDivisions> divisions = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countries = DBCountries.getAll();
        CreateCustomerCountryComboBox.setItems(countries);
        CreateCustomerCountryComboBox.setVisibleRowCount(5);

        divisions = DBFirstLevelDivisions.getAll();
        CreateCustomerDivisionComboBox.setItems(divisions);
        CreateCustomerDivisionComboBox.setVisibleRowCount(5);
    }

    public void backButtonHandler(ActionEvent e) {
        try {
            URL url =new File( "src/sample/View/Customers.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene CustomersScreen = new Scene(root, 600, 375);
            Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            stage.setScene(CustomersScreen);
            stage.show();
        }catch (IOException exc){
            exc.printStackTrace();
        }
    }

    public void countryComboBoxHandler(ActionEvent e) {
        ObservableList<FirstLevelDivisions> d = FXCollections.observableArrayList();
        Countries c = CreateCustomerCountryComboBox.getSelectionModel().getSelectedItem();
        d = DBFirstLevelDivisions.getDivisionsByCountry(c.getCountryID());
        if(d != null){
            CreateCustomerDivisionComboBox.setItems(d);
        }
        else {
            CreateCustomerDivisionComboBox.setPromptText("Country has no division");
        }
    }

    public void divisionComboBoxHandler(ActionEvent e){
        //FirstLevelDivisions d = CreateCustomerDivisionComboBox.getSelectionModel().getSelectedItem();
        //Countries c = DBCountries.getCountryByDivision(d.getCountryID());

        //CreateCustomerCountryComboBox.setValue(c);
        //CreateCustomerDivisionComboBox.setValue(d);
    }



    public void createButtonHandler(ActionEvent e){
        try {
            String cName = CreateCustomerNameTextField.getText();
            String cAddress = CreateCustomerAddressTextField.getText();
            String cPostal = CreateCustomerPostalTextField.getText();
            String cPhone = CreateCustomerPhoneTextField.getText();
            int cDivision = CreateCustomerDivisionComboBox.getSelectionModel().getSelectedItem().getDivisionID();
            if (cName.isEmpty() || cAddress.isEmpty() ||
                   cPostal.isEmpty() || cPhone.isEmpty() || CreateCustomerDivisionComboBox.getSelectionModel().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error! Please fill in all fields.");
                alert.showAndWait();
            } else {
                DBCustomers.createCustomer(cName, cAddress, cPostal, cPhone, cDivision);

                try {
                    URL url = new File("src/sample/View/Customers.fxml").toURI().toURL();
                    Parent root = FXMLLoader.load(url);
                    Scene CustomersScreen = new Scene(root, 600, 375);
                    Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
                    stage.setScene(CustomersScreen);
                    stage.show();
                } catch (IOException exc) {
                    exc.printStackTrace();
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
