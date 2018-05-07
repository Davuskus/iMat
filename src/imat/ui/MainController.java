package imat.ui;

import imat.interfaces.IProducDetailstListener;
import imat.model.FXMLController;
import imat.enums.NavigationTarget;
import imat.interfaces.INavigationListener;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class MainController extends FXMLController implements INavigationListener {

    // TODO: Add helpview to modal stackpane

    @FXML
    private AnchorPane rootPane;

    @FXML
    private StackPane viewsStackPane;

    @FXML
    private AnchorPane modalView;

    @FXML
    private AnchorPane checkoutPane;

    @FXML
    private AnchorPane browseView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // placeRandomOrder(getRandomInteger(1, 15));
        model.addNavigationListener(this);
    }

    // Temporary
    private int getRandomInteger(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    // Temporary
    private void placeRandomOrder(int numberOfPurchases) {
        if (!IMatDataHandler.getInstance().getProducts().isEmpty()) {
            for (int i = 0; i < numberOfPurchases; i++) {
                ShoppingItem shoppingItem = new ShoppingItem(getRandomProduct());
                shoppingItem.setAmount(getRandomInteger(1, 20));
                IMatDataHandler.getInstance().getShoppingCart().addItem(shoppingItem);
            }
            IMatDataHandler.getInstance().placeOrder(true);
        }
    }

    // Temporary
    private Product getRandomProduct() {
        int randomId = getRandomInteger(1, IMatDataHandler.getInstance().getProducts().size());
        return IMatDataHandler.getInstance().getProduct(randomId);
    }

    @Override
    public void navigateTo(NavigationTarget navigationTarget) {
        switch (navigationTarget) {
            case HELP:
            case COPY_ORDER:
            case PRODUCT_DETAILS:
                browseView.toFront();
                break;
            case CONFIRMATION:
                modalView.toFront();
                break;
            case PAYMENT:
                modalView.toFront();
                break;
            case CHECKOUT:
                checkoutPane.toFront();
                break;
            default:
                browseView.toFront();
                break;
        }
    }
}
