package imat.controls.cartsidebar;

import imat.controls.product.cartitem.CartItem;
import imat.interfaces.RemoveRequestListener;
import imat.interfaces.ShoppingListener;
import imat.utils.FXMLLoader;
import imat.utils.IMatUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.ShoppingItem;

public class CartSidebar extends AnchorPane implements ShoppingListener, RemoveRequestListener<CartItem> {

    @FXML
    private VBox cartItemVBox;

    @FXML
    private Label sumLabel;

    private double cartPrice;

    public CartSidebar() {
        super();
        FXMLLoader.loadFXMLFromRootPackage("cart_sidebar.fxml", this, this);
        updateSumLabel();
    }

    public void setCartPrice(double cartPrice) {
        this.cartPrice = cartPrice;
        updateSumLabel();
    }

    private void changeCartPrice(double change) {
        setCartPrice(cartPrice + change);
    }

    private void updateSumLabel() {
        sumLabel.setText(cartPrice + " kr");
    }

    @Override
    public void onAddShoppingItem(ShoppingItem shoppingItem) {
        CartItem cartItem = new CartItem(IMatUtils.cloneShoppingItem(shoppingItem));
        cartItem.addRemoveRequestListener(this);
        cartItem.addPriceChangeListener(this::onCartItemPriceChanged);
        cartItemVBox.getChildren().add(cartItem);
        changeCartPrice(shoppingItem.getTotal());
    }

    @Override
    public void onRemoveRequest(CartItem cartItem) {
        cartItemVBox.getChildren().remove(cartItem);
        changeCartPrice(-cartItem.getShoppingItem().getTotal());
    }

    private void onCartItemPriceChanged(double oldPrice, double newPrice) {
        changeCartPrice(newPrice - oldPrice);
    }

}
