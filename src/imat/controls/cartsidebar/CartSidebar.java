package imat.controls.cartsidebar;

import imat.controls.product.cartitem.CartItem;
import imat.interfaces.RemoveRequestListener;
import imat.interfaces.ShoppingListener;
import imat.utils.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.ShoppingItem;

public class CartSidebar extends AnchorPane implements ShoppingListener, RemoveRequestListener<CartItem> {

    @FXML
    private VBox cartItemVBox;

    public CartSidebar() {
        super();
        FXMLLoader.loadFXMLFromRootPackage("cart_sidebar.fxml", this, this);
    }

    @Override
    public void onAddShoppingItem(ShoppingItem shoppingItem) {
        CartItem cartItem = new CartItem(shoppingItem);
        cartItem.addRemoveRequestListener(this);
        cartItemVBox.getChildren().add(cartItem);
    }

    @Override
    public void onRemoveRequest(CartItem cartItem) {
        cartItemVBox.getChildren().remove(cartItem);
    }

}
