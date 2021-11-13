package sample.DBAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Database.DatabaseConnection;
import sample.Model.Customers;

import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBCustomers implements AccessInterface {

    public static ObservableList<Customers> getAll() {
        ObservableList<Customers> list = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM customers";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ps.execute();
            ResultSet rs = ps.getResultSet();

            while (rs.next()) {
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String customerPostalCode = rs.getString("Postal_Code");
                String customerPhone = rs.getString("Phone");
                int divisionID = rs.getInt("Division_ID");
                Customers c = new Customers(customerID, customerName, customerAddress,
                        customerPostalCode, customerPhone, divisionID);
                list.add(c);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public static void createCustomer(String cName, String cAddress,
                                      String cPostalCode, String cPhone, int dID) {
        try {
            String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES(?, ?, ?, ?, ?)";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ps.setString(1, cName);
            ps.setString(2, cAddress);
            ps.setString(3, cPostalCode);
            ps.setString(4, cPhone);
            ps.setInt(5, dID);

            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void updateCustomer(int cID, String cName, String cAddress,
                                       String cPostalCode, String cPhone, int dID) {
        try{
            String sql = "UPDATE customers SET Customer_Name = ?, Address = ?," +
                    "Postal_Code = ?, Phone = ?,Division_ID = ? WHERE Customer_ID =?";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);

            ps.setString(1, cName);
            ps.setString(2, cAddress);
            ps.setString(3, cPostalCode);
            ps.setString(4, cPhone);
            ps.setInt(5, dID);
            ps.setInt(6, cID);
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public static boolean deleteCustomer(int cID) {
        try{
            String sql = "DELETE FROM customers WHERE Customer_ID = ?";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ps.setInt(1,cID);
            ps.execute();
            return true;

        } catch (SQLException throwables) {

            throwables.printStackTrace();
            return false;
        }
    }



}



