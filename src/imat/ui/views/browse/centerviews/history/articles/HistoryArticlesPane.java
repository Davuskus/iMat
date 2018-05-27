package imat.ui.views.browse.centerviews.history.articles;

import imat.enums.NavigationTarget;
import imat.interfaces.ICartTrashListener;
import imat.interfaces.INavigationListener;
import imat.interfaces.IOrderListener;
import imat.model.FXMLController;
import imat.ui.controls.history.article.ArticleHistoryItem;
import imat.utils.DateUtils;
import imat.utils.FXMLLoader;
import imat.utils.MathUtils;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.net.URL;
import java.util.ResourceBundle;

public class HistoryArticlesPane extends FXMLController implements IOrderListener, ICartTrashListener, INavigationListener {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private VBox articlesVBox;

    @FXML
    private Button backButton;

    @FXML
    private Button olderOrderButton;

    @FXML
    private Button newerOrderButton;

    @FXML
    private Button copyToCartButton;

    @FXML
    private Label dateLabel;

    @FXML
    private Label totalNumArticlesLabel;

    @FXML
    private Label totalPriceLabel;

    private Order order;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model.addOrderListener(this);
        model.addCartTrashListener(this);
        model.addNavigationListener(this);
    }

    private void populateArticleList(Order order) {
        for (ShoppingItem shoppingItem : order.getItems()) {
            ArticleHistoryItem articleHistoryItem = new ArticleHistoryItem();
            articleHistoryItem.setModel(model);
            Node articleNode = FXMLLoader.loadFXMLNodeFromRootPackage(
                    "../../../../../controls/history/article/article_history_item.fxml",
                    this,
                    articleHistoryItem);
            articlesVBox.getChildren().add(articleNode);
            articleHistoryItem.setShoppingItem(shoppingItem);
        }
    }

    private double getOrderPrice() {
        double price = 0;
        for (ShoppingItem shoppingItem : order.getItems()) {
            price += shoppingItem.getTotal();
        }
        return MathUtils.round(price, 2);
    }

    private void updateOrderInfo(Order order) {
        totalPriceLabel.setText(MathUtils.asPriceTag(getOrderPrice()));
        totalNumArticlesLabel.setText(String.valueOf(order.getItems().size()) + " st");
        dateLabel.setText(DateUtils.getFormattedDate(order.getDate(), "yyyy/MM/dd - HH:mm"));
    }

    @FXML
    private void backButtonOnAction(Event event) {
        articlesVBox.getChildren().clear();
        model.navigate(NavigationTarget.ORDER_HISTORY);
    }

    @Override
    public void onOrderSelected(Order order) {
        this.order = order;
        articlesVBox.getChildren().clear();
        populateArticleList(this.order);
        updateOrderInfo(this.order);
        updateOrderScrollingButtons();
    }

    @FXML
    private void copyOrderToCartButtonOnAction(Event event) {
        if (model.getProductsInCart().size() == 0) {
            model.addOrderToCart(order);
        } else {
            model.selectOrder(order);
            model.navigate(NavigationTarget.COPY_ORDER);
        }
    }

    private void updateOlderOrderButton() {
        olderOrderButton.setDisable(model.getOlderOrder(order) == null);
    }

    private void updateNewerOrderButton() {
        newerOrderButton.setDisable(model.getNewerOrder(order) == null);
    }

    private void updateOrderScrollingButtons() {
        updateOlderOrderButton();
        updateNewerOrderButton();
    }

    @FXML
    private void showOlderOrderButtonOnAction(Event event) {
        model.selectOrder(model.getOlderOrder(this.order));
    }


    @FXML
    private void showNewerOrderButtonOnAction(Event event) {
        model.selectOrder(model.getNewerOrder(this.order));
    }

    @Override
    public void onCartTrashStarted() {
        copyToCartButton.setDisable(true);
    }

    @Override
    public void onCartTrashStopped() {
        copyToCartButton.setDisable(false);
    }

    @Override
    public void navigateTo(NavigationTarget navigationTarget) {
        rootPane.setDisable(navigationTarget != NavigationTarget.ORDER_HISTORY_ARTICLE);
    }
}
