package sample.DBAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public interface AccessInterface {
    public static ObservableList<?> getAll() {
        return FXCollections.observableArrayList();
    }
}
