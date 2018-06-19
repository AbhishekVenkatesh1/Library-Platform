package administrative;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FXMLNewItemDialogController implements Initializable {
    
    private AdministrativeDAO model;
    
    @FXML
    private TextField itemType;
    
    @FXML
    private TextField title;
    
    @FXML
    private TextField value;
    
    @FXML
    Label printItemId;
    
    @FXML
    Label errorMessage;
    
    @FXML
    private void newItem(ActionEvent event) {
        int itemid = model.createNewItem(itemType.getText(), title.getText(), Double.parseDouble(value.getText()));
        if (itemid > 0) {
            printItemId.setText("The item id for this item is " + itemid + ".");
            itemType.clear();
            title.clear();
            value.clear();
        }
        else {
            errorMessage.setText("There was an error generating an item id.");
        }
    }
    
    @FXML
    private void cancel(ActionEvent event) {
        itemType.getScene().getWindow().hide();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void setModel(AdministrativeDAO dao) {
        model = dao;
    }
    
}
