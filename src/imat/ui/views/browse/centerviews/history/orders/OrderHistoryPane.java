package imat.ui.views.browse.centerviews.history.orders;

import imat.enums.NavigationTarget;
import imat.interfaces.INavigationListener;
import imat.model.FXMLController;
import imat.ui.controls.history.order.OrderHistoryItem;
import imat.utils.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OrderHistoryPane extends FXMLController implements INavigationListener {

    @FXML
    private VBox ordersVBox;

    private final List<OrderHistoryItem> orderHistoryItems;

    private int numOrders;

    public OrderHistoryPane() {
        super();
        orderHistoryItems = new ArrayList<>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model.addNavigationListener(this);
    }

    private void updateOrderList() {
        if (numOrdersChanged()) {
            numOrders = IMatDataHandler.getInstance().getOrders().size();
            ordersVBox.getChildren().clear();
            orderHistoryItems.clear();
            addOrdersToFlowPane();
        }
    }

    private void addOrdersToFlowPane() {

        // Reverse a copy of the iMat order list
        List<Order> iMatOrderList = IMatDataHandler.getInstance().getOrders();
        List<Order> orders = new ArrayList<>(IMatDataHandler.getInstance().getOrders().size());
        for (int i = iMatOrderList.size() - 1; i >= 0; i--) {
            orders.add(iMatOrderList.get(i));
        }

        for (Order order : orders) {
            OrderHistoryItem orderHistoryItem = new OrderHistoryItem();
            orderHistoryItem.setModel(model);
            orderHistoryItems.add(orderHistoryItem);
            Node historyItem = FXMLLoader.loadFXMLNodeFromRootPackage(
                    "../../../../../controls/history/order/order_history_item.fxml",
                    this, orderHistoryItem);
            orderHistoryItem.setOrder(order);
            orderHistoryItem.setOrderHistoryPane(this);
            ordersVBox.getChildren().add(historyItem);
        }

    }

    private boolean numOrdersChanged() {
        return numOrders != IMatDataHandler.getInstance().getOrders().size();
    }

    @Override
    public void navigateTo(NavigationTarget navigationTarget) {
        if (navigationTarget == NavigationTarget.ORDER_HISTORY) {
            updateOrderList();
        }
    }

}
