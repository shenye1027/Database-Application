package sample.DBAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Database.DatabaseConnection;
import sample.Model.Countries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBCountries implements AccessInterface{

    public static ObservableList<Countries> getAll() {
        ObservableList<Countries> list = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM countries";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ps.execute();
            ResultSet rs = ps.getResultSet();

            while(rs.next()) {
                int countryID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");

                Countries c = new Countries(countryID, countryName);
                list.add(c);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public static Countries getCountryByDivision(int cID){
        Countries c = null;
        try{
            String sql = "SELECT * FROM countries WHERE Country_ID = ?";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, cID);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while(rs.next()) {
                int countryID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");

                c = new Countries(countryID, countryName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

}
