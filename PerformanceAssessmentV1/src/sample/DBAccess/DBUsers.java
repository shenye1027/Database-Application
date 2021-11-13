package sample.DBAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Database.DatabaseConnection;
import sample.Model.Users;

import javax.swing.text.html.HTMLDocument;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUsers implements AccessInterface {

    public static ObservableList<Users> getAll() {
        ObservableList<Users> list = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM users";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ps.execute();
            ResultSet rs = ps.getResultSet();

            while (rs.next()) {
                int userID = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String userPassword = rs.getString("Password");
                Users u = new Users(userID, userName, userPassword);
                list.add(u);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }


    public static String getPassword(String username) {
            String s = null;
        try {
            String sql = "SELECT Password FROM users WHERE User_Name = ?";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ps.setString(1,username);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while(rs.next()) {
                s = rs.getString(1);
            }
            return s;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return s;
    }


    public static int getIDByUsername(String uname) {
        int uid = 0;
        try{
            String sql = "SELECT User_ID FROM users WHERE User_Name = ?";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ps.setString(1, uname);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while(rs.next()) {
                uid = rs.getInt(1);
            }
            return uid;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return uid;
    }
}
