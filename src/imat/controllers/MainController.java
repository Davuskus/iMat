package imat.controllers;

import imat.controls.product.cartitem.CartItem;
import imat.controls.product.menuitem.ProductMenuItem;
import imat.views.helpview.HelpView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private StackPane viewsStackPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    //Temporary for debugging the different scenes
    @FXML
    private void changeView() {
        viewsStackPane.getChildren().get(0).toFront();
    }

    // Temporary
    private Product getTestProduct() {
        Product product = new Product();
        product.setName("Product 1");
        product.setCategory(ProductCategory.BERRY);
        product.setIsEcological(true);
        product.setPrice(100);
        product.setProductId(1);
        product.setUnit("kr/kg");
        product.setImageName("product_1.jpg");
        return product;
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
