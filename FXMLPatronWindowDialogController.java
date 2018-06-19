package checkingout;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FXMLPatronWindowDialogController implements Initializable {

    private CheckingOutDAO model;
    
    @FXML
    private Button patronStatus;
    
    private int patronid;
    
    @FXML
    private ListView itemsList;
    
    @FXML
    private void clickPatronStatus(ActionEvent event) {
        Stage parent = (Stage) patronStatus.getScene().getWindow();
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLPatronStatusDialog.fxml"));
            Parent root = (Parent)loader.load();
            Scene scene = new Scene(root);
            Stage dialog = new Stage();
            dialog.setScene(scene);
            dialog.setTitle("Patron Status");
            dialog.initOwner(parent);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initStyle(StageStyle.UTILITY);
            dialog.setX(parent.getX() + parent.getWidth()/4);
            dialog.setY(parent.getY() + parent.getHeight()/3);
            
            FXMLPatronStatusDialogController controller = (FXMLPatronStatusDialogController) loader.getController();
            controller.setModel(model);
            controller.setPatronId(patronid);
            controller.setList(patronid);
            dialog.show();
        } catch(Exception ex) {
            System.out.println("Could not open dialog.");
            ex.printStackTrace();
        }
    }
    
    @FXML
    private void clickCheckOut(ActionEvent event) {
        Stage parent = (Stage) patronStatus.getScene().getWindow();
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLCheckingOutDialog.fxml"));
            Parent root = (Parent)loader.load();
            Scene scene = new Scene(root);
            Stage dialog = new Stage();
            dialog.setScene(scene);
            dialog.setTitle("Checking Out");
            dialog.initOwner(parent);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initStyle(StageStyle.UTILITY);
            dialog.setX(parent.getX() + parent.getWidth()/4);
            dialog.setY(parent.getY() + parent.getHeight()/3);
            
            FXMLCheckingOutDialogController controller = (FXMLCheckingOutDialogController) loader.getController();
            controller.setModel(model);
            controller.setPatronId(patronid);
            dialog.show();
        } catch(Exception ex) {
            System.out.println("Could not open dialog.");
            ex.printStackTrace();
        }
    }
    
    @FXML
    private void clickPayFines(ActionEvent event) {
        Stage parent = (Stage) patronStatus.getScene().getWindow();
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLPayFinesDialog.fxml"));
            Parent root = (Parent)loader.load();
            Scene scene = new Scene(root);
            Stage dialog = new Stage();
            dialog.setScene(scene);
            dialog.setTitle("Pay Fines");
            dialog.initOwner(parent);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initStyle(StageStyle.UTILITY);
            dialog.setX(parent.getX() + parent.getWidth()/4);
            dialog.setY(parent.getY() + parent.getHeight()/3);
            
            FXMLPayFinesDialogController controller = (FXMLPayFinesDialogController) loader.getController();
            controller.setModel(model);
            controller.setPatronId(patronid);
            dialog.show();
        } catch(Exception ex) {
            System.out.println("Could not open dialog.");
            ex.printStackTrace();
        }
    }
    
    public void setPatronId(int id) {
        patronid = id;
    }
    
    public void setModel(CheckingOutDAO dao) {
        model = dao;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model = new CheckingOutDAO();
    }    
    
}
