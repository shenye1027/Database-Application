package sample.DBAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Database.DatabaseConnection;
import sample.Model.FirstLevelDivisions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBFirstLevelDivisions implements AccessInterface{

    public static ObservableList<FirstLevelDivisions> getAll(){
        ObservableList<FirstLevelDivisions> list = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM first_level_divisions";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ps.execute();
            ResultSet rs = ps.getResultSet();

            while(rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryID = rs.getInt("Country_ID");

                FirstLevelDivisions f = new FirstLevelDivisions(divisionID, divisionName, countryID);
                list.add(f);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return list;
    }

    public static ObservableList<FirstLevelDivisions> getDivisionsByCountry(int countryID) {
        ObservableList<FirstLevelDivisions> list = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM first_level_divisions WHERE Country_ID =?";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, countryID);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while(rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int cID = rs.getInt("Country_ID");

                FirstLevelDivisions f = new FirstLevelDivisions(divisionID, divisionName, cID);
                list.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static FirstLevelDivisions getDivision(int dID) {
        FirstLevelDivisions d = null;
        try{
            String sql = "SELECT * FROM first_level_divisions WHERE Division_ID =?";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, dID);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while(rs.next()){
                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int cID = rs.getInt("Country_ID");

                d = new FirstLevelDivisions(divisionID, divisionName, cID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return d;
    }
}
