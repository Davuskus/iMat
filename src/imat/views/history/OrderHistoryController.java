package imat.views.history;

import imat.controls.history.order.OrderHistoryItem;
import imat.controls.history.shoppingitem.HistoryShoppingItem;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
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
    private AnchorPane ordersPane;

    @FXML
    private FlowPane ordersFlowPane;

    @FXML
    private Button backButton;

    @FXML
    private StackPane stackPane;

    @FXML
    private Label dateLabel;

    @FXML
    private Button updateOrderListButton;

    private final Insets separatorPaddingInsets;

    public OrderHistoryController() {
        separatorPaddingInsets = new Insets(0.5, 0, 0, 0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateOrderList();
    }

    /**
     * Updates the list containing previous orders.
     */
    public void updateOrderList() {
        ordersFlowPane.getChildren().clear();
        addOrdersToFlowPane();
    }

    private void addOrdersToFlowPane() {

        List<Order> iMatOrderList = IMatDataHandler.getInstance().getOrders();
        List<Order> orders = new ArrayList<>(IMatDataHandler.getInstance().getOrders().size());
        for (int i = iMatOrderList.size() - 1; i >= 0; i--) {
            orders.add(iMatOrderList.get(i));
        }

        int index = 0;

        for (Order order : orders) {
            OrderHistoryItem orderHistoryItem = new OrderHistoryItem(order, this);
            ordersFlowPane.getChildren().add(orderHistoryItem);

            // Add separators between the "list" items
            if (index < orders.size() - 1) {
                ordersFlowPane.getChildren().add(createSeparator(orderHistoryItem.getPrefWidth(), false));
            }

            index++;
        }
    }

    public void showProductsPane(OrderHistoryItem orderHistoryItem) {
        populateProductList(orderHistoryItem);
        backButton.setFocusTraversable(true);
        copyCartButton.setFocusTraversable(true);
        updateOrderListButton.setFocusTraversable(false);
        dateLabel.setText(orderHistoryItem.getDate(orderHistoryItem.getDateFormat()));
        switchViews();
    }

    private void hideProductsPane() {
        productsFlowPane.getChildren().clear();
        backButton.setFocusTraversable(false);
        copyCartButton.setFocusTraversable(false);
        updateOrderListButton.setFocusTraversable(true);
        switchViews();
    }

    private void populateProductList(OrderHistoryItem orderHistoryItem) {

        int index = 0;

        for (ShoppingItem shoppingItem : orderHistoryItem.getShoppingItems()) {
            HistoryShoppingItem historyShoppingItem = new HistoryShoppingItem(shoppingItem, this);
            productsFlowPane.getChildren().add(historyShoppingItem);

            // Add separators between the "list" items
            if (index < orderHistoryItem.getShoppingItems().size() - 1) {
                productsFlowPane.getChildren().add(createSeparator(historyShoppingItem.getPrefWidth(), false));
            }

            index++;
        }

    }

    private Separator createSeparator(double prefWidth, boolean visible) {
        Separator separator = new Separator();
        separator.setPrefWidth(prefWidth);
        separator.setPadding(separatorPaddingInsets);
        separator.setVisible(visible);
        return separator;
    }

    private void switchViews() {
        stackPane.getChildren().get(0).toFront();
    }

    @FXML
    private void updateOrderListButtonOnAction(Event event) {
        updateOrderList();
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
