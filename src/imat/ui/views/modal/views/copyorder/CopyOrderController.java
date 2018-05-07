package imat.ui.views.modal.views.copyorder;

import imat.interfaces.IOrderListener;
import imat.model.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import se.chalmers.cse.dat216.project.Order;

import java.net.URL;
import java.util.ResourceBundle;

public class CopyOrderController extends FXMLController implements IOrderListener {

    @FXML
    private Button addToButton;

    @FXML
    private Button replaceButton;

    @FXML
    private Button cancelButton;

    private Order order;

    @FXML
    private void addToButtonOnAction(ActionEvent event) {
        model.addOrderToCart(order);
        cancelButtonOnAction(null);
    }

    @FXML
    private void cancelButtonOnAction(ActionEvent event) {
        model.navigateBack();
    }

    @FXML
    private void replaceButtonOnAction(ActionEvent event) {
        model.clearCart();
        model.addOrderToCart(order);
        cancelButtonOnAction(null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model.addOrderListener(this);
    }

    @Override
    public void onOrderSelected(Order order) {
        this.order = order;
    }

}
