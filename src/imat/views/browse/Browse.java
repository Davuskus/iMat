package imat.views.browse;

<<<<<<< HEAD
=======
import imat.Model;
import imat.controls.cartsidebar.CartSidebar;
import imat.interfaces.IFXMLController;
>>>>>>> c35e6e764d87c62c45aa16730efad305f0b3e37f
import imat.views.history.OrderHistoryPane;
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
    private OrderHistoryPane orderHistoryPane;

    private Modal modal;

    private StackPane stackPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // orderHistoryPane.addShoppingListener(cartSidebar);
        // cartSidebar.setSavingCartAtShutdown(true);
    }

    @Override
    public void setModel(Model m) {
        this.model = m;
    }
}
