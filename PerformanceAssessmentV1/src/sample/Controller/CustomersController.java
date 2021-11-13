package sample.Controller;

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
import sample.DBAccess.DBCustomers;
import sample.Model.Appointments;
import sample.Model.Customers;

import javafx.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomersController implements Initializable {
    @FXML
    private TableView<Customers> CustomersTable;
    @FXML
    private TableColumn CustomersIDCol;
    @FXML
    private TableColumn CustomersNameCol;
    @FXML
    private TableColumn CustomersAddressCol;
    @FXML
    private TableColumn CustomersPostalCol;
    @FXML
    private TableColumn CustomersPhoneCol;
    @FXML
    private TableColumn CustomersDivisionCol;
    @FXML
    private Button CustomersCreateButton;
    @FXML
    private Button CustomersUpdateButton;
    @FXML
    private Button CustomersBackButton;
    @FXML
    private Button CustomersDeleteButton;

    private ObservableList<Customers> customers = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            customers = DBCustomers.getAll();
            CustomersTable.setItems(customers);
            CustomersIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            CustomersNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            CustomersAddressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
            CustomersPostalCol.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
            CustomersPhoneCol.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
            CustomersDivisionCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));

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
            URL url =new File( "src/sample/View/CreateCustomer.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene createCustomerScreen = new Scene(root, 500, 500);
            Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            stage.setScene(createCustomerScreen);
            stage.show();
        }catch (IOException exc){
            exc.printStackTrace();
        }
    }

    public void updateButtonHandler(ActionEvent e) {
        Customers c = CustomersTable.getSelectionModel().getSelectedItem();
        if(c != null){
            try {
                URL url =new File( "src/sample/View/UpdateCustomer.fxml").toURI().toURL();
                FXMLLoader loader = new FXMLLoader(url);
                Parent root = loader.load();
                UpdateCustomerController controller = loader.getController();
                controller.sendCustomerData(c);
                Scene updateCustomerScreen = new Scene(root, 500, 500);
                Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
                stage.setScene(updateCustomerScreen);
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

    public void deleteButtonHandler(ActionEvent e){
        Customers c = CustomersTable.getSelectionModel().getSelectedItem();
        if(c != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete the selected customer?");
            ButtonType yesButton = new ButtonType("Yes");
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(yesButton,noButton);
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == yesButton) {
                //check for appointments
                boolean hasAppointment = false;
                int id = c.getCustomerID();
                ObservableList<Appointments> a = FXCollections.observableArrayList();
                a = DBAppointments.getAll();
                for(Appointments ap : a) {
                    if(id == ap.getCustomerID()){
                        hasAppointment = true;
                        Alert alert1 = new Alert(Alert.AlertType.ERROR);
                        alert1.setTitle("Error!");
                        alert1.setHeaderText(null);
                        alert1.setContentText("Cannot delete a customer with appointment scheduled.");
                        alert1.showAndWait();
                        break;
                    }
                }
                if(hasAppointment == false) {
                    DBCustomers.deleteCustomer(id);
                    customers = DBCustomers.getAll();
                    CustomersTable.setItems(customers);
                    Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                    alert3.setTitle("Process Complete");
                    alert3.setHeaderText(null);
                    alert3.setContentText("Customer has been deleted.");
                    alert3.showAndWait();
                }
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



}
