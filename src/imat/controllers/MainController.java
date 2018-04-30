package imat.controllers;

import imat.controls.product.cartitem.CartItem;
import imat.views.helpview.HelpView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private StackPane viewsStackPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        placeRandomOrder(getRandomInteger(1, 15));
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
        // TODO load the help view and move to front
    }

    public void closeHelpView(HelpView helpView) {
        // TODO close the help view
    }
}
