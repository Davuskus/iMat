package imat.ui.views.browse.history;

import imat.enums.NavigationTarget;
import imat.interfaces.IOrderListener;
import imat.model.FXMLController;
import imat.ui.controls.history.article.ArticleHistoryItem;
import imat.utils.DateUtils;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.net.URL;
import java.util.ResourceBundle;

public class HistoryArticlesPane extends FXMLController implements IOrderListener {

    @FXML
    private VBox articlesVBox;

    @FXML
    private Button backButton;

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
    }

    private void populateArticleList(Order order) {
        for (ShoppingItem shoppingItem : order.getItems()) {
            ArticleHistoryItem articleHistoryItem = new ArticleHistoryItem(model);
            articlesVBox.getChildren().add(articleHistoryItem);
            articleHistoryItem.setShoppingItem(shoppingItem);
        }
    }

    private double getOrderPrice() {
        double price = 0;
        for (ShoppingItem shoppingItem : order.getItems()) {
            price += shoppingItem.getTotal();
        }
        return price;
    }

    private void updateOrderInfo() {
        totalPriceLabel.setText(String.valueOf(getOrderPrice()));
        totalNumArticlesLabel.setText(String.valueOf(order.getItems().size()));
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
        populateArticleList(order);
        updateOrderInfo();
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

}
