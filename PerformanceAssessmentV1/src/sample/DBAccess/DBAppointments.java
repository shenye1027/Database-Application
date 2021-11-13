package sample.DBAccess;

import com.mysql.cj.x.protobuf.MysqlxPrepare;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Database.DatabaseConnection;
import sample.Model.Appointments;

import java.sql.*;
import java.time.LocalDateTime;

public class DBAppointments implements AccessInterface {

    public static ObservableList<Appointments> getAll() {
        ObservableList<Appointments> list = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM appointments";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ps.execute();
            ResultSet rs = ps.getResultSet();

            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
                String appointmentDescription = rs.getString("Description");
                String appointmentLocation = rs.getString("Location");
                String appointmentType = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");

                Appointments a = new Appointments(appointmentID, appointmentTitle, appointmentDescription,
                        appointmentLocation, appointmentType, start, end, customerID, userID, contactID);

                list.add(a);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public static void createAppointment(String aTitle, String aDescription, String aLocation,
                                         String aType, LocalDateTime aStart, LocalDateTime aEnd,
                                         int customerID, int userID, int contactID) {
        try {
            String sql = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);

            ps.setString(1, aTitle);
            ps.setString(2, aDescription);
            ps.setString(3, aLocation);
            ps.setString(4, aType);
            ps.setTimestamp(5, Timestamp.valueOf(aStart));
            ps.setTimestamp(6, Timestamp.valueOf(aEnd));
            ps.setInt(7, customerID);
            ps.setInt(8, userID);
            ps.setInt(9, contactID);
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    public static void updateAppointment(int aID, String aTitle, String aDescription, String aLocation,
                                  String aType, LocalDateTime aStart, LocalDateTime aEnd,
                                  int customerID, int userID, int contactID) {
        try {
            String sql = "UPDATE appointments SET Title = ?, Description = ?," +
                    "Location = ?, Type = ?,Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID =?";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);

            ps.setString(1, aTitle);
            ps.setString(2, aDescription);
            ps.setString(3, aLocation);
            ps.setString(4, aType);
            ps.setTimestamp(5, Timestamp.valueOf(aStart));
            ps.setTimestamp(6, Timestamp.valueOf(aEnd));
            ps.setInt(7, customerID);
            ps.setInt(8, userID);
            ps.setInt(9, contactID);
            ps.setInt(10, aID);
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static boolean deleteAppointment(int aID) {
        try{
            String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, aID);
            ps.execute();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public static ObservableList<Appointments> getLastMonth(){
        ObservableList<Appointments> list = FXCollections.observableArrayList();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lastMonth = now.minusDays(30);
        try{
            String sql = "SELECT * FROM appointments WHERE Start > ? AND Start < ?";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(lastMonth));
            ps.setTimestamp(2, Timestamp.valueOf(now));
            ps.execute();
            ResultSet rs = ps.getResultSet();

            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
                String appointmentDescription = rs.getString("Description");
                String appointmentLocation = rs.getString("Location");
                String appointmentType = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");

                Appointments a = new Appointments(appointmentID, appointmentTitle, appointmentDescription,
                        appointmentLocation, appointmentType, start, end, customerID, userID, contactID);

                list.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;

    }

    public static ObservableList<Appointments> getLastWeek(){
        ObservableList<Appointments> list = FXCollections.observableArrayList();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lastWeek = now.minusDays(7);
        try{
            String sql = "SELECT * FROM appointments WHERE Start > ? AND Start < ?";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(lastWeek));
            ps.setTimestamp(2, Timestamp.valueOf(now));
            ps.execute();
            ResultSet rs = ps.getResultSet();

            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
                String appointmentDescription = rs.getString("Description");
                String appointmentLocation = rs.getString("Location");
                String appointmentType = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");

                Appointments a = new Appointments(appointmentID, appointmentTitle, appointmentDescription,
                        appointmentLocation, appointmentType, start, end, customerID, userID, contactID);

                list.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ObservableList<Appointments> getAppointmentsByContactID(int contactID) {
        ObservableList<Appointments> list = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM appointments WHERE Contact_ID = ?";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, contactID);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
                String appointmentDescription = rs.getString("Description");
                String appointmentLocation = rs.getString("Location");
                String appointmentType = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int conID = rs.getInt("Contact_ID");

                Appointments a = new Appointments(appointmentID, appointmentTitle, appointmentDescription,
                        appointmentLocation, appointmentType, start, end, customerID, userID, conID);

                list.add(a);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }


}