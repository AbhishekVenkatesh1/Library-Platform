package checkingout;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FXMLCheckingOutController implements Initializable {

    private CheckingOutDAO model;
    
    @FXML 
    private TextField patronId;
    
    @FXML 
    private void clickEnter(ActionEvent event) {
        model.connect();
        Stage parent = (Stage) patronId.getScene().getWindow();
        int id = Integer.parseInt(patronId.getText());
        
        if (model.checkPatron(id) == false) {
            model.showMessageDialog("This is an invalid patron id. Please type again.");
            patronId.clear();
        } else {
            try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLPatronWindowDialog.fxml"));
            Parent root = (Parent)loader.load();
            Scene scene = new Scene(root);
            Stage dialog = new Stage();
            dialog.setScene(scene);
            dialog.setTitle("Patron Home");
            dialog.initOwner(parent);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initStyle(StageStyle.UTILITY);
            dialog.setX(parent.getX() + parent.getWidth()/4);
            dialog.setY(parent.getY() + parent.getHeight()/3);
            
            FXMLPatronWindowDialogController controller = (FXMLPatronWindowDialogController) loader.getController();
            controller.setModel(model);
            controller.setPatronId(id);
            dialog.show();
            } catch(Exception ex) {
            System.out.println("Could not open dialog.");
            ex.printStackTrace();
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model = new CheckingOutDAO();
    }    
    
}
