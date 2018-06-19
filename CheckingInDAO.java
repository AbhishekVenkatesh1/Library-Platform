package checkingin;

import java.sql.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CheckingInDAO {
    Connection connection;
    Statement stmt;
    
    String checkInSQL = "delete from borrow where (item) = (?)";
    PreparedStatement checkInStmt;
    public boolean checkInItem(int itemId) {
        connect();
        boolean success = false;
        try {
            checkInStmt.setInt(1, itemId);
            checkInStmt.executeUpdate();
            success = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }        
        return success;
    }
    
    public boolean checkItem(int itemId) {
        connect();
        try {
            String sql = "select title from item where iditem =" + itemId;
            ResultSet rset = stmt.executeQuery(sql);
            
            if (rset.next()) {
                return true;
            } 
        } catch (SQLException ex) {
            ex.printStackTrace();
        } 
        return false;
    }
    
    public boolean checkItemOut(int itemId) {
        connect();
        try {
            String sql = "select patron from borrow where item =" + itemId;
            ResultSet rset = stmt.executeQuery(sql);
            
            if(rset.next()) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            showMessageDialog("Unable to load JDBC driver. Application will exit.");
            System.exit(0);
        }

        // Establish a connection
        try {
            String url = "jdbc:mysql://localhost:3306/library?user=root&password=cmsc250";
            connection = DriverManager.getConnection(url);
            stmt = connection.createStatement();
            checkInStmt = connection.prepareStatement(checkInSQL);
        } catch (SQLException ex) {
            showMessageDialog("Unable to connect to database. Application will exit.");
            System.exit(0);
        }
    }
    
    public void showMessageDialog(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
    
}