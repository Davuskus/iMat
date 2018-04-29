package imat.views.history;

import imat.controls.carthistory.CartHistoryItem;
import imat.controls.product.history.ProductHistoryItem;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HistoryController implements Initializable {

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

    private final List<CartHistoryItem> cartHistoryItems;

    public HistoryController() {
        cartHistoryItems = new ArrayList<>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Load all history from the file using the backend somehow and add it to cartHistoryItems
    }

    private void addAllPreviousCartsToList() {
        cartsFlowPane.getChildren().addAll(cartHistoryItems);
    }

    private void showProductsPane(CartHistoryItem cartHistoryItem) {
        populateProductList(cartHistoryItem);
        backButton.setFocusTraversable(true);
        copyCartButton.setFocusTraversable(true);
        productsFlowPane.toFront();
    }

    private void hideProductsPane() {
        productsFlowPane.getChildren().clear();
        backButton.setFocusTraversable(false);
        copyCartButton.setFocusTraversable(true);
        productsFlowPane.toBack();
    }

    private void populateProductList(CartHistoryItem cartHistoryItem) {
        for (Product product : cartHistoryItem.getProducts()) {
            productsFlowPane.getChildren().add(new ProductHistoryItem(product, this));
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
