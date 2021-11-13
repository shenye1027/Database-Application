package sample.DBAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Database.DatabaseConnection;
import sample.Model.Contacts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBContacts {
    public static ObservableList<Contacts> getAll () {
        ObservableList<Contacts> list = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM contacts";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String contactEmail = rs.getString("Email");

                Contacts c = new Contacts(contactID, contactName, contactEmail);
                list.add(c);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public static Contacts getContact(int contactID) {
        Contacts c = null;
        try{
            String sql = "SELECT * FROM contacts WHERE Contact_ID = ?";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, contactID);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                int cID = rs.getInt("Contact_ID");
                String cName = rs.getString("Contact_Name");
                String email = rs.getString("Email");

                c = new Contacts(cID, cName, email);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return c;
    }
}
