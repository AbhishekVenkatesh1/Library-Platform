package administrative;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AdministrativeDAO {
    Connection connection;
    Statement stmt;
    
    String newItemSQL = "insert into item (kind,title,cost) values(?,?,?)";
    PreparedStatement newItemStmt;
    public int createNewItem(String itemType, String title, double value) {
        connect();
        int itemId = -1;
        try {
            newItemStmt.setString(1, itemType);
            newItemStmt.setString(2, title);
            newItemStmt.setDouble(3, value);
            newItemStmt.executeUpdate();
            String query = "select iditem from item where kind=\'"
                + itemType + "\' and title=\'" + title + "\' and cost = " + value;
            ResultSet rset = stmt.executeQuery(query);
            
            if (rset.next()) {
                itemId = rset.getInt(1);
            }       
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return itemId;
    }
    
    String newPatronSQL = "insert into patron (name,email) values(?,?)";
    PreparedStatement newPatronStmt;
    public int createNewPatron(String name, String email) {
        connect();
        int patronId = -1;
        try {
            newPatronStmt.setString(1, name);
            newPatronStmt.setString(2, email);
            newPatronStmt.executeUpdate();
            String query = "select idpatron from patron where name=\'"
                + name + "\' and email=\'" + email + "\'";
            ResultSet rset = stmt.executeQuery(query);
            
            if (rset.next()) {
                patronId = rset.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return patronId;
    }
    
    String removeLostSQL = "delete from item where (iditem) = (?)";
    String removeLostSQL1 = "delete from borrow where (item) = (?)";
    PreparedStatement removeLostStmt;
    PreparedStatement removeLostStmt1;
    public boolean removeLostItem(int itemId) {
        connect();
        boolean success = false;
        try {
            removeLostStmt.setInt(1, itemId);
            removeLostStmt.executeUpdate();
            removeLostStmt1.setInt(1, itemId);
            removeLostStmt1.executeUpdate();
            success = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return success;
    }
    
    public boolean computeFines() {
        connect();
        boolean success = false;
        try {
            String computeFineBSQL = "UPDATE borrow INNER JOIN item ON borrow.item=item.iditem "
                                + "SET borrow.pending = datediff(curdate(),borrow.due)*0.5 - borrow.fines "
                                + "WHERE item.kind='B' AND borrow.due < curdate();";
            stmt.execute(computeFineBSQL);
            String computeFineVSQL = "UPDATE borrow INNER JOIN item ON borrow.item=item.iditem "
                                + "SET borrow.pending = datediff(curdate(),borrow.due) - borrow.fines "
                                + "WHERE item.kind='V' AND borrow.due < curdate();";
            stmt.execute(computeFineVSQL);
            String checkFineSQL = "update borrow inner join item on borrow.item=item.iditem "
                                + "set borrow.pending = (item.cost + 5) - borrow.fines where (borrow.fines + borrow.pending) > (item.cost + 5)";
            stmt.execute(checkFineSQL);
            String updatePendingSQL = "UPDATE patron INNER JOIN (SELECT patron, sum(pending) as p FROM borrow GROUP BY patron) AS fines "
                                +    "ON patron.idpatron=fines.patron " 
                                +    "SET patron.fine = patron.fine + fines.p";
            stmt.execute(updatePendingSQL);
            String updateFineSQL = "UPDATE borrow SET fines=fines+pending WHERE pending > 0;";
            stmt.execute(updateFineSQL);
            String updateFineSQL1 = "UPDATE borrow SET pending=0.0 WHERE pending > 0;";
            stmt.execute(updateFineSQL1);
            
            success = true;
            } 
        catch (SQLException ex) {
            ex.printStackTrace(); 
        }
        return success;
    }
    
    public boolean overdue() {
        connect();
        try {
            String query = "select name, email, item from item join borrow join patron on item.iditem = borrow.item and patron.idpatron = borrow.patron where kind = 'B' and datediff(curdate(),due) > 14";
            String query1 = "select name, email, item from item join borrow join patron on item.iditem = borrow.item and patron.idpatron = borrow.patron where kind = 'V' and datediff(curdate(),due) > 7";
            ResultSet rset = stmt.executeQuery(query);
            
            try {
                PrintWriter overdue = new PrintWriter(new File("overdue.txt"));
                while (rset.next()) {
                    String printName = rset.getString(1);
                    String printEmail = rset.getString(2);
                    String printItem = rset.getString(3);
                    overdue.println(printName);
                    overdue.println(printEmail);
                    overdue.println(printItem);
                }
                ResultSet rset1 = stmt.executeQuery(query1);
                while (rset1.next()) {
                    String printName = rset1.getString(1);
                    String printEmail = rset1.getString(2);
                    String printItem = rset1.getString(3);
                    overdue.println(printName);
                    overdue.println(printEmail);
                    overdue.println(printItem);
                }
                overdue.close();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                return false;
            }
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
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
            newItemStmt = connection.prepareStatement(newItemSQL);
            newPatronStmt = connection.prepareStatement(newPatronSQL);
            removeLostStmt = connection.prepareStatement(removeLostSQL);
            removeLostStmt1 = connection.prepareStatement(removeLostSQL1);
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
