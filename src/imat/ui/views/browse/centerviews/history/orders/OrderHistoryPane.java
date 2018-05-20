package imat.ui.views.browse.centerviews.history.orders;

import imat.enums.NavigationTarget;
import imat.interfaces.INavigationListener;
import imat.model.FXMLController;
import imat.ui.controls.history.order.OrderHistoryItem;
import imat.utils.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.Order;

import java.net.URL;
import java.util.ResourceBundle;

public class OrderHistoryPane extends FXMLController implements INavigationListener {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private VBox ordersVBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model.addNavigationListener(this);
    }

    private void updateOrderFlowPane() {
        if (model.numOrdersChanged() || (ordersVBox.getChildren().isEmpty() && model.getNumOrders() > 0)) {
            ordersVBox.getChildren().clear();
            for (Order order : model.updateOrderList()) {
                OrderHistoryItem orderHistoryItem = new OrderHistoryItem();
                orderHistoryItem.setModel(model);

                Node historyItem = FXMLLoader.loadFXMLNodeFromRootPackage(
                        "../../../../../controls/history/order/order_history_item.fxml",
                        this, orderHistoryItem);
                orderHistoryItem.setOrder(order);
                if (ordersVBox.getChildren().size() % 2 == 1) {
                    orderHistoryItem.setColorScheme(OrderHistoryItem.ColorScheme.WHITE);
                } else {
                    orderHistoryItem.setColorScheme(OrderHistoryItem.ColorScheme.GRAY);
                }
                ordersVBox.getChildren().add(historyItem);
            }
        }
    }

    @Override
    public void navigateTo(NavigationTarget navigationTarget) {
        if (navigationTarget == NavigationTarget.ORDER_HISTORY) {
            rootPane.setDisable(false);
            updateOrderFlowPane();
        } else {
            rootPane.setDisable(true);
        }
    }

}
