package checkingin;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FXMLCheckingInController implements Initializable {

    private CheckingInDAO model;
    
    @FXML
    private TextField itemId;
    
    @FXML
    Label success;
    
    @FXML
    Label errorMessage;
    
    @FXML
    private void clickCheckIn(ActionEvent event) {
        if (model.checkItem(Integer.parseInt(itemId.getText())) == false) {
            model.showMessageDialog("Invalid item number. Please type again.");
            itemId.clear();
        } else if (model.checkItemOut(Integer.parseInt(itemId.getText())) == false) {
            model.showMessageDialog("This item is already checked in. Please type again.");
            itemId.clear();
        } else {
            if(model.checkInItem(Integer.parseInt(itemId.getText()))) {
                success.setText("The item of id " + Integer.parseInt(itemId.getText()) + " has been checked in.");
                itemId.clear();
            }
            else {
                errorMessage.setText("The item can not be checked in.");
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model = new CheckingInDAO();
    }    
    
}
