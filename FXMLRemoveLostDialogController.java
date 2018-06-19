package administrative;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FXMLRemoveLostDialogController implements Initializable {
    
    private AdministrativeDAO model;
    
    private int itemId;
    
    @FXML
    private TextField lostItem;
    
    @FXML
    Label success;
    
    @FXML
    Label errorMessage;
    
    @FXML
    private void removeLost(ActionEvent event) {
        
        if (model.checkItem(Integer.parseInt(lostItem.getText())) == false) {
            model.showMessageDialog("Invalid item id. Please type again.");
            lostItem.clear();
        } else {
                if(model.removeLostItem(Integer.parseInt(lostItem.getText()))) {
                    success.setText("The lost item has been removed from the system.");
                    lostItem.clear();
                }
                else {
                    errorMessage.setText("Can not remove item from the system.");
                }
        }
    }
    
    @FXML
    private void cancel(ActionEvent event) {
        lostItem.getScene().getWindow().hide();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    public void setModel(AdministrativeDAO dao) {
        model = dao;
    }
}
