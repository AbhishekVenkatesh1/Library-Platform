package checkingout;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CheckingOutDAO {
    Connection connection;
    Statement stmt;
    
    Date borrowed;
    Date due;
    int itemid;
    int patronid;
    
    ObservableList<Handle> handles;
    
    public CheckingOutDAO() {
        handles = FXCollections.observableArrayList();
    }
    
    String checkOutSQL = "insert into borrow (borrowed, due, item, patron) values (curdate(),adddate(curdate(),INTERVAL ? DAY),?,?)";
    PreparedStatement checkOutStmt;
    public boolean checkOutItem(int itemId, int patronId) {
        connect();
        boolean success = false;
        try {
            String query = "select kind from item where iditem=\'" + itemId + "\'";
            ResultSet rset = stmt.executeQuery(query);
            String kind = null;
            if(rset.next()) {
                kind = rset.getString(1);
            }
            if (kind.equals("B")) {
                checkOutStmt.setInt(1,14);
            } else {
                checkOutStmt.setInt(1,7);
            }
            checkOutStmt.setInt(2, itemId);
            checkOutStmt.setInt(3, patronId);
            checkOutStmt.executeUpdate();
            success = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return success;
    }
    
    public int amountItems(int patronId) {
        connect();
        try {
            String query = "select count(item) from borrow where patron= " + patronId + " group by patron";
            ResultSet rset = stmt.executeQuery(query);
            if (rset.next()) {
                return rset.getInt(1);
            } else {
                return -1;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }
    
    String payFineSQL = "update patron set fine = fine - (?) where idpatron = (?) and (?) <= fine";
    PreparedStatement payFineStmt;
    public double payFine(double paid, int patronId) {
        connect();
        double fine = -1;
        try {
            payFineStmt.setDouble(1, paid);
            payFineStmt.setInt(2, patronId);
            payFineStmt.setDouble(3, paid);
            payFineStmt.executeUpdate();
            String query = "select fine from patron where idpatron = " + patronId;
            ResultSet rset = stmt.executeQuery(query);
            
            if (rset.next()) {
                fine = rset.getDouble(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return fine;
    }
    
    public ObservableList<Handle> getHandles(int patronId) { 
        refreshHandles(patronId);
        return handles; 
    }
    
    public void refreshHandles(int patronId) {
        handles.clear();
        try {
            String sqlString1 = "SELECT item.iditem, concat (item.iditem,': ',item.title,' (Overdue)') from item join borrow "
                                + "on item.iditem = borrow.item where borrow.patron =" + patronId + " and datediff(curdate(),borrow.due) > 14 "
                                + "and item.kind = 'B'";
            String sqlString2 = "SELECT item.iditem, concat (item.iditem,': ',item.title,' (Overdue)') from item join borrow "
                                + "on item.iditem = borrow.item where borrow.patron =" + patronId + " and datediff(curdate(),borrow.due) > 7 "
                                + "and item.kind = 'V'";
            String sqlString3 = "SELECT item.iditem, concat (item.iditem,'. ',item.title) from item join borrow "
                                + "on item.iditem = borrow.item where borrow.patron =" + patronId + " and datediff(curdate(),borrow.due) <= 14 "
                                + "and item.kind = 'B'";
            String sqlString4 = "SELECT item.iditem, concat (item.iditem,'. ',item.title) from item join borrow "
                                + "on item.iditem = borrow.item where borrow.patron =" + patronId + " and datediff(curdate(),borrow.due) <= 7 "
                                + "and item.kind = 'V'";
            ResultSet rset = stmt.executeQuery(sqlString1);
            while (rset.next()) {
                handles.add(new Handle(rset.getInt(1), rset.getString(2)));
                }
            ResultSet rset1 = stmt.executeQuery(sqlString2);
            while (rset1.next()) {
                handles.add(new Handle(rset1.getInt(1), rset1.getString(2)));
                }
            ResultSet rset2 = stmt.executeQuery(sqlString3);
            while (rset2.next()) {
                handles.add(new Handle(rset2.getInt(1), rset2.getString(2)));
                }
            ResultSet rset3 = stmt.executeQuery(sqlString4);
            while (rset3.next()) {
                handles.add(new Handle(rset3.getInt(1), rset3.getString(2)));
                }
        } catch (SQLException ex) {
            showMessageDialog("Could not fetch handles: " + ex.getMessage());
        }
    }
    
    public double getFines(int patronId) {
        connect();
        double fine = -1;
        try {
            String selectFineSQL = "select fine from patron where idpatron =" + patronId;
            ResultSet rset = stmt.executeQuery(selectFineSQL);
            
            if(rset.next()) {
                fine = rset.getDouble(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } 
        return fine;
    }
    
    public boolean checkPatron(int patronId) {
        connect();
        try {
            String sql = "select name from patron where idpatron =" + patronId;
            ResultSet rset = stmt.executeQuery(sql);
            
            if (rset.next()) {
                return true;
            } 
        } catch (SQLException ex) {
            ex.printStackTrace();
        } 
        return false;
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
    
    public String getName(int patronId) {
        connect();
        String name = null;
        try {
            String sql = "select name from patron where idpatron =" + patronId;
            ResultSet rset = stmt.executeQuery(sql);
            
            if(rset.next()) {
                name = rset.getString(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return name;
    }
    
    public String getEmail(int patronId) {
        connect();
        String email = null;
        try {
            String sql = "select email from patron where idpatron =" + patronId;
            ResultSet rset = stmt.executeQuery(sql);
            
            if(rset.next()) {
                email = rset.getString(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return email;
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
            checkOutStmt = connection.prepareStatement(checkOutSQL);
            payFineStmt = connection.prepareStatement(payFineSQL);
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