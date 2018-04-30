package imat.controls.history.order;

import imat.utils.FXMLLoader;
import imat.views.history.OrderHistoryController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderHistoryItem extends AnchorPane {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label dateLabel;

    private final OrderHistoryController orderHistoryController;

    private final List<ShoppingItem> shoppingItems;

    public OrderHistoryItem(Order order, OrderHistoryController orderHistoryController) {
        this.orderHistoryController = orderHistoryController;
        this.shoppingItems = new ArrayList<>();
        FXMLLoader.loadFXMLFromRootPackage("order_history_item.fxml", this, this);

        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        dateLabel.setText(formatter.format(order.getDate()));

        shoppingItems.addAll(order.getItems());

    }

    public List<ShoppingItem> getShoppingItems() {
        List<ShoppingItem> copiedShoppingItemList = new ArrayList<>(shoppingItems.size());
        copiedShoppingItemList.addAll(shoppingItems);
        return copiedShoppingItemList;
    }

    @FXML
    private void onAction(Event Event) {
        orderHistoryController.showProductsPane(this);
    }

    @FXML
    private void onMouseEntered(Event event) {
        // TODO Change the background color
    }

    @FXML
    private void onMouseExited(Event event) {
        // TODO Change the background color
    }

    @FXML
    private void onMousePressed(Event event) {
        // TODO Change the background color
    }

}
