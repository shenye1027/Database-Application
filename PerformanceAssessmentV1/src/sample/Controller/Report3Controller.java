package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Report3Controller implements Initializable {
    @FXML
    private TableView Report3Table;
    @FXML
    private TableColumn Report3UserIDCol;
    @FXML
    private TableColumn Report3DateCol;
    @FXML
    private TableColumn Report3SuccessCol;
    @FXML
    private Button Report3BackButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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

}
