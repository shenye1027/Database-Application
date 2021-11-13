package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import sample.Model.Customers;
import sample.Model.FirstLevelDivisions;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateCustomerController implements Initializable {
    @FXML
    private TextField UpdateCustomerNameTextField;
    @FXML
    private TextField UpdateCustomerAddressTextField;
    @FXML
    private TextField UpdateCustomerPostalTextField;
    @FXML
    private TextField UpdateCustomerPhoneTextField;
    @FXML
    private TextField UpdateCustomerIDTextField;
    @FXML
    private ComboBox<Countries> UpdateCustomerCountryComboBox;
    @FXML
    private ComboBox<FirstLevelDivisions> UpdateCustomerDivisionComboBox;
    @FXML
    private Button UpdateCustomerUpdateButton;
    @FXML
    private Button UpdateCustomerBackButton;

    ObservableList<Countries> countries = FXCollections.observableArrayList();
    ObservableList<FirstLevelDivisions> divisions = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countries = DBCountries.getAll();
        UpdateCustomerCountryComboBox.setItems(countries);
        UpdateCustomerCountryComboBox.setVisibleRowCount(5);

        divisions = DBFirstLevelDivisions.getAll();
        UpdateCustomerDivisionComboBox.setItems(divisions);
        UpdateCustomerDivisionComboBox.setVisibleRowCount(5);
    }




    public void backButtonHandler(ActionEvent e) {
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

    public void countryComboBoxHandler(ActionEvent e) {
        ObservableList<FirstLevelDivisions> d = FXCollections.observableArrayList();
        Countries c = UpdateCustomerCountryComboBox.getSelectionModel().getSelectedItem();
        d = DBFirstLevelDivisions.getDivisionsByCountry(c.getCountryID());
        if(d != null){
            UpdateCustomerDivisionComboBox.setItems(d);
        }
        else {
            UpdateCustomerDivisionComboBox.setPromptText("Country has no division");
        }
    }

    public void updateButtonHandler(ActionEvent e){
        try {
            int cID = Integer.parseInt(UpdateCustomerIDTextField.getText());
            String cName = UpdateCustomerNameTextField.getText();
            String cAddress = UpdateCustomerAddressTextField.getText();
            String cPostal = UpdateCustomerPostalTextField.getText();
            String cPhone = UpdateCustomerPhoneTextField.getText();
            int cDivision = UpdateCustomerDivisionComboBox.getSelectionModel().getSelectedItem().getDivisionID();
           // if (cName.isEmpty() || cAddress.isEmpty() ||
            //        cPostal.isEmpty() || cPhone.isEmpty() || UpdateCustomerDivisionComboBox.getSelectionModel().isEmpty()) {
             //   Alert alert = new Alert(Alert.AlertType.ERROR);
              //  alert.setTitle("Error");
              //  alert.setHeaderText(null);
             //   alert.setContentText("Error! Please fill in all fields.");
               // alert.showAndWait();
           // } else {
                DBCustomers.updateCustomer(cID, cName, cAddress, cPostal, cPhone, cDivision);

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
           // }
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

    public void sendCustomerData(Customers c){
        UpdateCustomerIDTextField.setText(Integer.toString(c.getCustomerID()));
        UpdateCustomerNameTextField.setText(c.getCustomerName());
        UpdateCustomerAddressTextField.setText(c.getCustomerAddress());
        UpdateCustomerPostalTextField.setText(c.getCustomerPostalCode());
        UpdateCustomerPhoneTextField.setText(c.getCustomerPhone());

        UpdateCustomerDivisionComboBox.setValue(DBFirstLevelDivisions.getDivision(c.getDivisionID()));
        UpdateCustomerCountryComboBox.setValue(DBCountries.getCountryByDivision(UpdateCustomerDivisionComboBox.getSelectionModel().getSelectedItem().getCountryID()));
        UpdateCustomerDivisionComboBox.setItems(DBFirstLevelDivisions.getDivisionsByCountry(UpdateCustomerCountryComboBox.getSelectionModel().getSelectedItem().getCountryID()));
    }


}