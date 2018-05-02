package imat.views.browse;

import imat.controls.cartsidebar.CartSidebar;
import imat.views.history.OrderHistoryPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class Browse implements Initializable {

    @FXML
    private CartSidebar cartSidebar;

    @FXML
    private OrderHistoryPane orderHistoryPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        orderHistoryPane.addShoppingListener(cartSidebar);
        cartSidebar.setSavingCartAtShutdown(false);
    }
}
