package administrative;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FXMLAdministrativeController implements Initializable {
    
    private AdministrativeDAO model;
    
    @FXML 
    private Button newItem;
    
    @FXML
    private void clickNewItem(ActionEvent event) {
        Stage parent = (Stage) newItem.getScene().getWindow();
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLNewItemDialog.fxml"));
            Parent root = (Parent)loader.load();
            Scene scene = new Scene(root);
            Stage dialog = new Stage();
            dialog.setScene(scene);
            dialog.setTitle("New Item");
            dialog.initOwner(parent);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initStyle(StageStyle.UTILITY);
            dialog.setX(parent.getX() + parent.getWidth()/4);
            dialog.setY(parent.getY() + parent.getHeight()/3);
            
            FXMLNewItemDialogController controller = (FXMLNewItemDialogController) loader.getController();
            controller.setModel(model);
            dialog.show();
        } catch(Exception ex) {
            System.out.println("Could not open dialog.");
            ex.printStackTrace();
        }
    } 
    
    @FXML
    private void clickNewPatron(ActionEvent event) {
        Stage parent = (Stage) newItem.getScene().getWindow();
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLNewPatronDialog.fxml"));
            Parent root = (Parent)loader.load();
            Scene scene = new Scene(root);
            Stage dialog = new Stage();
            dialog.setScene(scene);
            dialog.setTitle("New Patron");
            dialog.initOwner(parent);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initStyle(StageStyle.UTILITY);
            dialog.setX(parent.getX() + parent.getWidth()/4);
            dialog.setY(parent.getY() + parent.getHeight()/3);
            
            FXMLNewPatronDialogController controller = (FXMLNewPatronDialogController) loader.getController();
            controller.setModel(model);
            dialog.show();
        } catch(Exception ex) {
            System.out.println("Could not open dialog.");
            ex.printStackTrace();
        }
        
    }
    
    @FXML
    private void clickRemoveLost(ActionEvent event) {
        Stage parent = (Stage) newItem.getScene().getWindow();
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLRemoveLostDialog.fxml"));
            Parent root = (Parent)loader.load();
            Scene scene = new Scene(root);
            Stage dialog = new Stage();
            dialog.setScene(scene);
            dialog.setTitle("Remove Lost Item");
            dialog.initOwner(parent);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initStyle(StageStyle.UTILITY);
            dialog.setX(parent.getX() + parent.getWidth()/4);
            dialog.setY(parent.getY() + parent.getHeight()/3);
            
            FXMLRemoveLostDialogController controller = (FXMLRemoveLostDialogController) loader.getController();
            controller.setModel(model);
            dialog.show();
        } catch(Exception ex) {
            System.out.println("Could not open dialog.");
            ex.printStackTrace();
        }
    }
    
    @FXML
    private void clickOverdueNotice(ActionEvent event) {
        model.overdue();
        
        Stage parent = (Stage) newItem.getScene().getWindow();
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLOverdueNoticeDialog.fxml"));
            Parent root = (Parent)loader.load();
            Scene scene = new Scene(root);
            Stage dialog = new Stage();
            dialog.setScene(scene);
            dialog.setTitle("Overdue Notices");
            dialog.initOwner(parent);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initStyle(StageStyle.UTILITY);
            dialog.setX(parent.getX() + parent.getWidth()/4);
            dialog.setY(parent.getY() + parent.getHeight()/3);
            
            FXMLOverdueNoticeDialogController controller = (FXMLOverdueNoticeDialogController) loader.getController();
            controller.setModel(model);
            controller.overdueNotice();
            dialog.show();
        } catch(Exception ex) {
            System.out.println("Could not open dialog.");
            ex.printStackTrace();
        } 
    }

    @FXML
    private void clickComputeFines(ActionEvent event) {
        model.computeFines();
        
        Stage parent = (Stage) newItem.getScene().getWindow();
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLComputeFinesDialog.fxml"));
            Parent root = (Parent)loader.load();
            Scene scene = new Scene(root);
            Stage dialog = new Stage();
            dialog.setScene(scene);
            dialog.setTitle("Compute Fines");
            dialog.initOwner(parent);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initStyle(StageStyle.UTILITY);
            dialog.setX(parent.getX() + parent.getWidth()/4);
            dialog.setY(parent.getY() + parent.getHeight()/3);
            
            FXMLComputeFinesDialogController controller = (FXMLComputeFinesDialogController) loader.getController();
            controller.setModel(model);
            controller.compute();
            dialog.show();
        } catch(Exception ex) {
            System.out.println("Could not open dialog.");
            ex.printStackTrace();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model = new AdministrativeDAO();
        model.connect();
    }    
    
}
