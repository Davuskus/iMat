package imat.views.browse;


import imat.Model;
import imat.controls.cartsidebar.CartSidebar;
import imat.interfaces.IFXMLController;
import imat.views.history.OrderHistoryPane;
import imat.views.modal.Modal;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

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
