package checkingout;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FXMLCheckingOutDialogController implements Initializable {

    private CheckingOutDAO model;
    
    private int patronId;
    
    @FXML
    private TextField itemId;
    
    @FXML
    Label checkOutMessage;
    
    @FXML
    Label errorMessage;
    
    @FXML
    private void checkOut(ActionEvent event) {
        if (model.amountItems(patronId) >= 10 || model.getFines(patronId) >= 10) {
            itemId.setDisable(true);
        }
        
        if (model.checkItem(Integer.parseInt(itemId.getText())) == false) {
            model.showMessageDialog("Invalid item number. Please type again.");
            itemId.clear();
        } else if (model.checkItemOut(Integer.parseInt(itemId.getText()))) {
            model.showMessageDialog("This item is already checked out. Please type again.");
            itemId.clear();
        } else {
            if (model.checkOutItem(Integer.parseInt(itemId.getText()), patronId)) {
            checkOutMessage.setText("The item of id " + Integer.parseInt(itemId.getText()) + " has been checked out.");
            itemId.clear();
            } else {
                errorMessage.setText("The item of id " + Integer.parseInt(itemId.getText()) + " can not be checked out.");
            }
        }
    }
    
    @FXML
    private void cancel(ActionEvent event) {
        itemId.getScene().getWindow().hide();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    public void setPatronId(int id) {
        patronId = id;
        if (model.amountItems(patronId) >= 10 || model.getFines(patronId) >= 10) {
            itemId.setDisable(true);
        }
    }
    
    public void setModel(CheckingOutDAO dao) {
        model = dao;
    }
    
}
