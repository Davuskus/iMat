package imat.views.browse;

import imat.views.history.OrderHistoryPane;
import imat.views.modal.Modal;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Browse implements Initializable {

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

}
