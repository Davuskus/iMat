package imat.ui.views.browse.history;

import imat.enums.NavigationTarget;
import imat.interfaces.INavigationListener;
import imat.model.FXMLController;
import imat.model.Model;
import imat.ui.controls.history.article.ArticleHistoryItem;
import imat.ui.controls.history.order.OrderHistoryItem;
import imat.utils.MathUtils;
import javafx.event.Event;
import javafx.fxml.FXML;
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

public class OrderHistoryPane extends FXMLController implements INavigationListener {

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
    private Label totalNumArticlesLabel;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private AnchorPane articlesPane;

    @FXML
    private AnchorPane ordersPane;

    private final List<OrderHistoryItem> orderHistoryItems;

    private final List<ArticleHistoryItem> articleHistoryItems;

    private int numOrders;

    private OrderHistoryItem currentOrderHistoryItem;

    private Model model;

    private boolean showingArticlesPane;

    private boolean userWasInThisView;

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
            ordersVBox.getChildren().add(orderHistoryItem);
            orderHistoryItem.setOrder(order);
            orderHistoryItem.setOrderHistoryPane(this);
            orderHistoryItems.add(orderHistoryItem);
        }

    }

    public void showArticlesPane(OrderHistoryItem orderHistoryItem) {
        showingArticlesPane = true;
        populateArticleList(orderHistoryItem);

        currentOrderHistoryItem = orderHistoryItem;

        backButton.setFocusTraversable(true);
        copyToCartButton.setFocusTraversable(true);
        dateLabel.setText(orderHistoryItem.getDate(orderHistoryItem.getDateFormat()));
        totalNumArticlesLabel.setText(orderHistoryItem.getNumShoppingItems() + " st");
        totalPriceLabel.setText(String.valueOf(MathUtils.round(orderHistoryItem.getOrderPrice(), 2)) + " kr");

        switchView(articlesPane);
    }

    private void hideArticlesPane() {
        showingArticlesPane = false;
        articlesVBox.getChildren().clear();
        articleHistoryItems.clear();
        backButton.setFocusTraversable(false);
        copyToCartButton.setFocusTraversable(false);
        updateOrderList();
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

    private boolean numOrdersChanged() {
        return numOrders != IMatDataHandler.getInstance().getOrders().size();
    }

    @FXML
    private void backButtonOnAction(Event event) {
        hideArticlesPane();
    }

    @FXML
    private void copyOrderToCartButtonOnAction(Event event) {
        if (model.getProductsInCart().size() == 0) {
            model.addOrderToCart(currentOrderHistoryItem.getOrder());
        } else {
            model.selectOrder(currentOrderHistoryItem.getOrder());
            model.navigate(NavigationTarget.COPY_ORDER);
        }
    }

    @Override
    public void setModel(Model model) {
        this.model = model;
    }

    @Override
    public void navigateTo(NavigationTarget navigationTarget) {
        if (navigationTarget == NavigationTarget.HISTORY) {
            userWasInThisView = true;
            if (showingArticlesPane) {
                showArticlesPane(currentOrderHistoryItem);
            } else {
                hideArticlesPane();
            }
        } else if (userWasInThisView) {
            userWasInThisView = false;
            numOrders = 0;
            articlesVBox.getChildren().clear();
            ordersVBox.getChildren().clear();
        }
    }

}
