package imat.views.browse;


import imat.Model;
import imat.interfaces.IFXMLController;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class Browse implements Initializable, IFXMLController {

    private Model model;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @Override
    public void setModel(Model m) {
        this.model = m;
    }
}
