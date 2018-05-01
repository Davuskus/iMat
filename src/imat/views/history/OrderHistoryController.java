package imat.views.history;

import imat.controls.history.article.ArticleHistoryItem;
import imat.controls.history.order.OrderHistoryItem;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OrderHistoryController implements Initializable {

    @FXML
    private Button copyToCartButton;

    @FXML
    private VBox articlesVBox;

    @FXML
    private VBox ordersVBox;

    @FXML
    private Button backButton;

    @FXML
    private StackPane stackPane;

    @FXML
    private Label dateLabel;

    @FXML
    private Button updateOrderListButton;

    @FXML
    private Label totalNumArticlesLabel;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private ScrollPane ordersScrollPane;

    @FXML
    private ScrollPane articlesScrollPane;

    private final List<OrderHistoryItem> orderHistoryItems;

    private final List<ArticleHistoryItem> articleHistoryItems;

    public OrderHistoryController() {
        orderHistoryItems = new ArrayList<>();
        articleHistoryItems = new ArrayList<>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        updateOrderList();

        articlesScrollPane.widthProperty().addListener((observable, oldValue, newValue) ->
                updateArticleHistoryItemWidths(newValue.doubleValue()));

        ordersScrollPane.widthProperty().addListener((observable, oldValue, newValue) ->
                updateOrderHistoryItemWidths(newValue.doubleValue()));

    }

    private void updateArticleHistoryItemWidths(double width) {
        for (ArticleHistoryItem articleHistoryItem : articleHistoryItems) {
            articleHistoryItem.setPrefWidth(width);
        }
    }

    private void updateOrderHistoryItemWidths(double width) {
        for (OrderHistoryItem orderHistoryItem : orderHistoryItems) {
            orderHistoryItem.setPrefWidth(width);
        }
    }

    /**
     * Updates the list containing previous orders.
     */
    public void updateOrderList() {
        ordersVBox.getChildren().clear();
        orderHistoryItems.clear();

        addOrdersToFlowPane();

        updateOrderHistoryItemWidths(ordersScrollPane.getWidth());
    }

    private void addOrdersToFlowPane() {

        // Reverse a copy of the iMat order list
        List<Order> iMatOrderList = IMatDataHandler.getInstance().getOrders();
        List<Order> orders = new ArrayList<>(IMatDataHandler.getInstance().getOrders().size());
        for (int i = iMatOrderList.size() - 1; i >= 0; i--) {
            orders.add(iMatOrderList.get(i));
        }

        for (Order order : orders) {
            OrderHistoryItem orderHistoryItem = new OrderHistoryItem(order, this);
            ordersVBox.getChildren().add(orderHistoryItem);
            orderHistoryItems.add(orderHistoryItem);
        }
    }

    public void showArticlesPane(OrderHistoryItem orderHistoryItem) {
        populateArticleList(orderHistoryItem);

        updateArticleHistoryItemWidths(articlesScrollPane.getWidth());

        backButton.setFocusTraversable(true);
        copyToCartButton.setFocusTraversable(true);
        updateOrderListButton.setFocusTraversable(false);
        dateLabel.setText(orderHistoryItem.getDate(orderHistoryItem.getDateFormat()));
        totalNumArticlesLabel.setText(orderHistoryItem.getNumShoppingItems() + " st");
        totalPriceLabel.setText(orderHistoryItem.getOrderPrice() + " kr");

        switchViews();
    }

    private void hideProductsPane() {
        articlesVBox.getChildren().clear();
        articleHistoryItems.clear();
        backButton.setFocusTraversable(false);
        copyToCartButton.setFocusTraversable(false);
        updateOrderListButton.setFocusTraversable(true);
        switchViews();
    }

    private void populateArticleList(OrderHistoryItem orderHistoryItem) {
        for (ShoppingItem shoppingItem : orderHistoryItem.getShoppingItems()) {
            ArticleHistoryItem articleHistoryItem = new ArticleHistoryItem(shoppingItem, this);
            articlesVBox.getChildren().add(articleHistoryItem);
            articleHistoryItems.add(articleHistoryItem);
        }
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
