package administrative;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class FXMLOverdueNoticeDialogController implements Initializable {
    
    private AdministrativeDAO model;
    
    @FXML
    Label success;
    
    @FXML
    Label errorMessage;
    
    @FXML
    public void overdueNotice() {
        if (model.overdue()) {
            success.setText("Overdue Notice file has been generated.");
        } else {
            errorMessage.setText("Overdue Notice file can not be generated.");
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
