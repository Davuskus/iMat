package imat.controllers;

import imat.FXMLController;
import imat.controls.product.cartitem.CartItem;
import imat.enums.NavigationTarget;
import imat.interfaces.INavigationListener;
import imat.views.helpview.HelpView;
import imat.views.modal.Modal;
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
    private Modal modalViewController;

    @FXML
    private AnchorPane payView;

    @FXML
    private AnchorPane helpView;

    @FXML
    private AnchorPane browseView;

    @FXML
    private AnchorPane paymentView;

    @FXML HelpView helpViewController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        placeRandomOrder(getRandomInteger(1, 15));

        model.addNavigationListener(this);

        // This is temporary, to test out the html loading
        helpViewController.setMainController(this);
        helpView.toBack();
    }

    //Temporary for debugging the different scenes
    @FXML
    private void changeView() {
       // viewsStackPane.getChildren().get(0).toFront();
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

    public void removeCartItem(CartItem cartItem) {
        // TODO Remove the cart item from the cart list.
        // TODO Show a "regret"-button after removal
    }

    public void showHelpView() {
        helpView.toFront();
    }

    public void closeHelpView() {
        helpView.toBack();
    }

    @Override
    public void navigateTo(NavigationTarget navigationTarget) {
        switch (navigationTarget) {
            case HELP:
                helpView.toFront(); break;
            case PAY:
                payView.toFront(); break;
            case PAYMENT:
                paymentView.toFront(); break;
            default:
                browseView.toFront(); break;
        }
    }
}
