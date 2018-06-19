package checkingout;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;


public class FXMLPatronStatusDialogController implements Initializable {

    private CheckingOutDAO model;
    
    private int patronId;
    
    @FXML
    Label name;
    
    @FXML
    Label email;
    
    @FXML
    private ListView itemsList;
    
    @FXML
    Label totalFines;
    
    @FXML
    Label privilege;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void setModel(CheckingOutDAO dao) {
        model = dao;
    }
    
    public void setList(int patronId) {
        name.setText(model.getName(patronId));
        email.setText(model.getEmail(patronId));
        itemsList.setItems(model.getHandles(patronId));
        totalFines.setText("The total fines for this customer is $" + model.getFines(patronId));
        if (model.getFines(patronId) >= 10 || model.amountItems(patronId) >= 10) {
            privilege.setText("This patron has lost check out privileges");
        } else {
            privilege.setText("This patron has not lost check out privileges");
        }
    }
    
    public void setPatronId(int id) {
        patronId = id;
    }
    
}
