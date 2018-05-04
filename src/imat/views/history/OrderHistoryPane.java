package imat.views.history;

import imat.Model;
import imat.controls.history.article.ArticleHistoryItem;
import imat.controls.history.order.OrderHistoryItem;
import imat.enums.NavigationTarget;
import imat.interfaces.IFXMLController;
import imat.interfaces.INavigationListener;
import imat.utils.MathUtils;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OrderHistoryPane extends AnchorPane implements Initializable, IFXMLController, INavigationListener {

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
    private AnchorPane articlesPane;

    @FXML
    private AnchorPane ordersPane;

    private final List<OrderHistoryItem> orderHistoryItems;

    private final List<ArticleHistoryItem> articleHistoryItems;

    private Order currentOrder;

    private Model model;

    public OrderHistoryPane() {
        super();
        orderHistoryItems = new ArrayList<>();
        articleHistoryItems = new ArrayList<>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model.addNavigationListener(this);
    }

    /**
     * Updates the list containing previous orders.
     */
    public void updateOrderList() {
        ordersVBox.getChildren().clear();
        orderHistoryItems.clear();
        addOrdersToFlowPane();
    }

    private void copyOrderToCart(Order order) {
        for (ShoppingItem shoppingItem : order.getItems()) {
            copyArticleToCart(shoppingItem);
        }
    }

    private void copyArticleToCart(ShoppingItem shoppingItem) {
        model.addToShoppingCart(shoppingItem.getProduct(), shoppingItem.getAmount());
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
            ordersVBox.getChildren().add(orderHistoryItem);
            orderHistoryItem.setOrder(order);
            orderHistoryItem.setOrderHistoryPane(this);
            orderHistoryItems.add(orderHistoryItem);
        }
    }

    public void showArticlesPane(OrderHistoryItem orderHistoryItem) {
        populateArticleList(orderHistoryItem);

        currentOrder = orderHistoryItem.getOrder();

        backButton.setFocusTraversable(true);
        copyToCartButton.setFocusTraversable(true);
        updateOrderListButton.setFocusTraversable(false);
        dateLabel.setText(orderHistoryItem.getDate(orderHistoryItem.getDateFormat()));
        totalNumArticlesLabel.setText(orderHistoryItem.getNumShoppingItems() + " st");
        totalPriceLabel.setText(String.valueOf(MathUtils.round(orderHistoryItem.getOrderPrice(), 2)) + " kr");

        switchView(articlesPane);
    }

    private void hideArticlesPane() {
        articlesVBox.getChildren().clear();
        articleHistoryItems.clear();
        backButton.setFocusTraversable(false);
        copyToCartButton.setFocusTraversable(false);
        updateOrderListButton.setFocusTraversable(true);
        switchView(ordersPane);
    }

    private void populateArticleList(OrderHistoryItem orderHistoryItem) {
        for (ShoppingItem shoppingItem : orderHistoryItem.getShoppingItems()) {
            ArticleHistoryItem articleHistoryItem = new ArticleHistoryItem(model);
            articlesVBox.getChildren().add(articleHistoryItem);
            articleHistoryItem.setShoppingItem(shoppingItem);
            articleHistoryItems.add(articleHistoryItem);
        }
    }

    private void switchView(Node node) {
        node.toFront();
    }

    @FXML
    private void updateOrderListButtonOnAction(Event event) {
        updateOrderList();
    }

    @FXML
    private void backButtonOnAction(Event event) {
        hideArticlesPane();
    }

    @FXML
    private void copyOrderToCartButtonOnAction(Event event) {
        // TODO If no products in current cart: Copy all products from the relevant cart to the current cart
        // TODO If products in current cart: Open dialog asking "Replace" or "Add".
        copyOrderToCart(currentOrder);
    }

    @Override
    public void setModel(Model model) {
        this.model = model;
    }


    @Override
    public void navigateTo(NavigationTarget navigationTarget) {
        if (navigationTarget == NavigationTarget.HISTORY) {
            updateOrderList();
        } else {
            hideArticlesPane();
            ordersVBox.getChildren().clear();
        }
    }

}
