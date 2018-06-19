package checkingout;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FXMLPayFinesDialogController implements Initializable {

    private CheckingOutDAO model;
    
    private int patronId;
    
    @FXML
    private TextField fineAmount;
    
    @FXML
    Label success;
    
    @FXML
    Label errorMessage;
    
    @FXML
    private void clickPayFine(ActionEvent event) {
        double fineRemaining = model.payFine(Double.parseDouble(fineAmount.getText()), patronId);
        if (fineRemaining > 0) {
            success.setText("The fine of $" + Double.parseDouble(fineAmount.getText()) + " has been paid. There is still $" + fineRemaining + " left to pay.");
            fineAmount.clear();
        } else if (fineRemaining == 0){
            success.setText("The fine of $" + Double.parseDouble(fineAmount.getText()) + " has been paid. There is no fines remaining.");
            fineAmount.clear();
        } else {
            errorMessage.setText("The fine can not be paid.");
            fineAmount.clear();
        }
    }
    
    @FXML
    private void cancel(ActionEvent event) {
        fineAmount.getScene().getWindow().hide();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setPatronId(int id) {
        patronId = id;
    }
    
    public void setModel(CheckingOutDAO dao) {
        model = dao;
    }
    
}
