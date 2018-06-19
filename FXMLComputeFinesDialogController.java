package administrative;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class FXMLComputeFinesDialogController implements Initializable {

    private AdministrativeDAO model;
    
    @FXML
    Label success;
    
    @FXML
    Label errorMessage;
    
    @FXML
    public void compute() {
        if (model.computeFines()) {
            success.setText("Fines for overdue items have been computed.");
        } else {
            errorMessage.setText("Fines for overdue items can not be computed.");
        }
    } 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    public void setModel(AdministrativeDAO dao) {
        model = dao;
    }
    
}
