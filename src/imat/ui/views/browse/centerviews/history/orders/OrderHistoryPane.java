package imat.ui.views.browse.centerviews.history.orders;

import imat.enums.NavigationTarget;
import imat.interfaces.INavigationListener;
import imat.interfaces.IOrderHistoryRequestListener;
import imat.model.FXMLController;
import imat.ui.controls.history.order.OrderHistoryItem;
import imat.utils.FXMLLoader;
import imat.utils.ListUtils;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.net.URL;
import java.util.*;

public class OrderHistoryPane extends FXMLController implements INavigationListener, IOrderHistoryRequestListener {

    @FXML
    private VBox ordersVBox;

    private int numOrders;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model.addNavigationListener(this);
        model.addOrderHistoryRequestListener(this);
    }

    private void updateOrderList() {
        if (numOrdersChanged()) {
            numOrders = IMatDataHandler.getInstance().getOrders().size();
            ordersVBox.getChildren().clear();
            addOrdersToFlowPane();
        }
    }

    private void removePotentialDuplicateProducts(Order order) {
        List<ShoppingItem> shoppingItems = getUniqueShoppingItems(order);
        order.getItems().clear();
        order.setItems(shoppingItems);
    }

    private List<ShoppingItem> getUniqueShoppingItems(Order order) {
        Map<Product, Double> shoppingItemMap = new HashMap<>();
        order.getItems().forEach(shoppingItem -> {
            Product product = shoppingItem.getProduct();
            double amount = shoppingItem.getAmount();
            if (shoppingItemMap.keySet().contains(product)) {
                shoppingItemMap.put(product, shoppingItemMap.get(product) + amount);
            } else {
                shoppingItemMap.put(product, amount);
            }
        });
        List<ShoppingItem> shoppingItems = new ArrayList<>(shoppingItemMap.keySet().size());
        shoppingItemMap.forEach(((product, amount) -> shoppingItems.add(new ShoppingItem(product, amount))));

        return shoppingItems;
    }

    private void addOrdersToFlowPane() {
        for (Order order : ListUtils.getReversedList(IMatDataHandler.getInstance().getOrders())) {
            removePotentialDuplicateProducts(order);
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

    private boolean numOrdersChanged() {
        return numOrders != IMatDataHandler.getInstance().getOrders().size();
    }

    @Override
    public void navigateTo(NavigationTarget navigationTarget) {
        if (navigationTarget == NavigationTarget.ORDER_HISTORY) {
            updateOrderList();
        }
    }

    @Override
    public Order onOlderOrderRequest(Order sourceOrder) {

        return null;
    }

    @Override
    public Order onNewerOrderRequest(Order sourceOrder) {
        return null;
    }

}
