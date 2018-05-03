package imat.views.browse;

import imat.Model;
import imat.interfaces.IFXMLController;
import imat.views.modal.Modal;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Browse implements Initializable, IFXMLController {

    Model model;

    @FXML
    private AnchorPane cartSidebar;

    @FXML
    private AnchorPane orderHistoryPane;

    private Modal modal;

    private StackPane stackPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @Override
    public void setModel(Model m) {
        this.model = m;
    }
}
