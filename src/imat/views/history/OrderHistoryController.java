package imat.views.history;

import imat.controls.history.order.OrderHistoryItem;
import imat.controls.history.shoppingitem.HistoryShoppingItem;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OrderHistoryController implements Initializable {

    @FXML
    private AnchorPane historyTitlePane;

    @FXML
    private Label historyTitleLabel;

    @FXML
    private AnchorPane productsPane;

    @FXML
    private AnchorPane cartTitlePane;

    @FXML
    private AnchorPane cartProductsPane;

    @FXML
    private Button copyCartButton;

    @FXML
    private FlowPane productsFlowPane;

    @FXML
    private AnchorPane cartsPane;

    @FXML
    private FlowPane cartsFlowPane;

    @FXML
    private Button backButton;

    private final List<OrderHistoryItem> orderHistoryItems;

    public OrderHistoryController() {
        orderHistoryItems = new ArrayList<>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        for (Order order : IMatDataHandler.getInstance().getOrders()) {
            orderHistoryItems.add(new OrderHistoryItem(order, this));
        }
        cartsFlowPane.getChildren().addAll(orderHistoryItems);

    }

    public void showProductsPane(OrderHistoryItem orderHistoryItem) {
        populateProductList(orderHistoryItem);
        backButton.setFocusTraversable(true);
        copyCartButton.setFocusTraversable(true);
        productsFlowPane.toFront();
    }

    private void hideProductsPane() {
        productsFlowPane.getChildren().clear();
        backButton.setFocusTraversable(false);
        copyCartButton.setFocusTraversable(false);
        productsFlowPane.toBack();
    }

    private void populateProductList(OrderHistoryItem orderHistoryItem) {
        for (ShoppingItem shoppingItem : orderHistoryItem.getShoppingItems()) {
            productsFlowPane.getChildren().add(new HistoryShoppingItem(shoppingItem, this));
        }
    }

    @FXML
    private void backButtonOnAction(Event event) {
        hideProductsPane();
    }

    @FXML
    private void copyCartButtonOnAction(Event event) {
        // TODO If no products in current cart: Copy all products from the relevant cart to the current cart
        // TODO If products in current cart: Open dialog asking "Replace" or"Add".
    }

}
