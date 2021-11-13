package sample.Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Database.DatabaseConnection;

import java.io.File;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        URL url =new File( "src/sample/View/Login.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("Database Login");
        primaryStage.setScene(new Scene(root, 400, 200));
        primaryStage.show();
    }


    public static void main(String[] args) {
        DatabaseConnection.openConnection();
        launch(args);
        DatabaseConnection.closeConnection();
    }
}
