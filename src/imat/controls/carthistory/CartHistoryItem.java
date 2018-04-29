package imat.controls.carthistory;

import imat.utils.FXMLLoader;
import imat.views.history.HistoryController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CartHistoryItem extends AnchorPane {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label dateLabel;

    private final HistoryController historyController;

    private final List<Product> products;

    public CartHistoryItem(HistoryController historyController) {
        this.historyController = historyController;
        this.products = new ArrayList<>();
        FXMLLoader.loadFXMLFromRootPackage("cart_history_item.fxml", this, this);
        // TODO Set the date text
    }

    public void addProducts(Product... products) {
        this.products.addAll(Arrays.asList(products));
    }

    public List<Product> getProducts() {
        List<Product> copiedProductsList = new ArrayList<>(products.size());
        copiedProductsList.addAll(products);
        return copiedProductsList;
    }

    @FXML
    private void onAction(Event Event) {
        // TODO Switch to the product list pane in the purchase history stack pane
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
