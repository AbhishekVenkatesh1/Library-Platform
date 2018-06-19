package administrative;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FXMLNewPatronDialogController implements Initializable {
    
    private AdministrativeDAO model;
    
    @FXML
    private TextField patronName;
    
    @FXML
    private TextField patronEmail;
    
    @FXML
    Label printPatronId;
    
    @FXML
    Label errorMessage;
    
    @FXML
    private void newPatron(ActionEvent event) {
        int patronid = model.createNewPatron(patronName.getText(), patronEmail.getText());
        if (patronid > 0) {
            printPatronId.setText("The patron id for this patron is " + patronid + ".");
            patronName.clear();
            patronEmail.clear();
        } else {
            errorMessage.setText("There is an error in generating a patron id.");
        }
    }
    
    @FXML
    private void cancel(ActionEvent event) {
        patronName.getScene().getWindow().hide();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    public void setModel(AdministrativeDAO dao) {
        model = dao;
    }
    
}
