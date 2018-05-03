package imat.controls.cartsidebar;

import imat.Model;
import imat.controls.product.cartitem.CartItem;
import imat.interfaces.IFXMLController;
import imat.interfaces.RemoveRequestListener;
import imat.interfaces.ShoppingListener;
import imat.utils.IMatUtils;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CartSidebar implements Initializable, ShoppingListener, RemoveRequestListener<CartItem>, IFXMLController {

    @FXML
    private VBox cartItemVBox;

    @FXML
    private Label sumLabel;

    @FXML
    private Button toCheckoutButton;

    @FXML
    private Button trashButton;

    private double cartPrice;

    private final List<CartItem> cartItems;

    private boolean isSavingCartAtShutdown;

    private boolean hasLoadedCartItems;

    private Model model;

    public CartSidebar() {
        super();
        cartItems = new ArrayList<>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model.addShoppingListener(this);
        updateSumLabel();
        loadCart();
        disableCheckoutButtonIfPriceIsZero();
    }

    public void setCartPrice(double cartPrice) {
        this.cartPrice = cartPrice;
        updateSumLabel();
    }

    public void setSavingCartAtShutdown(boolean savingCartAtShutdown) {
        this.isSavingCartAtShutdown = savingCartAtShutdown;
    }

    private void changeCartPrice(double change) {
        setCartPrice(cartPrice + change);
    }

    private void updateSumLabel() {
        sumLabel.setText(cartPrice + " kr");
    }

    private void loadCart() {
        if (!hasLoadedCartItems) {
            for (ShoppingItem shoppingItem : IMatDataHandler.getInstance().getShoppingCart().getItems()) {
                onAddShoppingItem(shoppingItem);
            }
            hasLoadedCartItems = true;
        }
    }

    private boolean isProductAlreadyInCart(Product product) {
        if (getProductIndexInCart(product) == -1) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isShoppingItemProductAlreadyInCart(ShoppingItem shoppingItem) {
        return isProductAlreadyInCart(shoppingItem.getProduct());
    }

    private int getProductIndexInCart(Product product) {
        for (int i = 0; i < cartItems.size(); i++) {
            if (cartItems.get(i).getShoppingItem().getProduct().equals(product)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onAddShoppingItem(ShoppingItem shoppingItem) {

        int productIndexInCart = getProductIndexInCart(shoppingItem.getProduct());
        boolean productIsInCart = productIndexInCart != -1;

        if (productIsInCart) {
            cartItems.get(productIndexInCart).changeAmount(shoppingItem.getAmount());
        } else {
            CartItem cartItem = new CartItem(IMatUtils.cloneShoppingItem(shoppingItem));
            cartItem.addRemoveRequestListener(this);
            cartItem.addPriceChangeListener(this::onCartItemPriceChanged);
            cartItemVBox.getChildren().add(cartItem);
            cartItems.add(cartItem);
            changeCartPrice(shoppingItem.getTotal());
            toCheckoutButton.setDisable(false);
            if (isSavingCartAtShutdown && hasLoadedCartItems)
                updateShoppingCart(); // Should be used if the cart SHOULD be saved at shutdown
        }

    }

    @Override
    public void onRemoveRequest(CartItem cartItem) {
        cartItemVBox.getChildren().remove(cartItem);
        cartItems.remove(cartItem);
        changeCartPrice(-cartItem.getShoppingItem().getTotal());
        disableCheckoutButtonIfPriceIsZero();
        if (isSavingCartAtShutdown)
            updateShoppingCart(); // Should be used if the cart SHOULD be saved at shutdown
    }

    private void disableCheckoutButtonIfPriceIsZero() {
        if (cartPrice <= 0) {
            toCheckoutButton.setDisable(true);
        }
    }

    private void clearCart() {
        cartItemVBox.getChildren().clear();
        cartItems.clear();
        setCartPrice(0);
        IMatDataHandler.getInstance().getShoppingCart().clear();
        toCheckoutButton.setDisable(true);
    }

    @FXML
    private void toCheckoutButtonOnAction(Event event) {
        if (!isSavingCartAtShutdown)
            updateShoppingCart(); // Should be used if the cart should NOT be saved at shutdown
        model.openCheckoutView();
    }

    @FXML
    private void trashButtonOnAction(Event event) {
        clearCart();
    }

    private void updateShoppingCart() {
        IMatDataHandler.getInstance().getShoppingCart().clear();
        for (CartItem cartItem : cartItems) {
            IMatDataHandler.getInstance().getShoppingCart().addItem(cartItem.getShoppingItem());
        }
    }

    private void onCartItemPriceChanged(double oldPrice, double newPrice) {
        changeCartPrice(newPrice - oldPrice);
        disableCheckoutButtonIfPriceIsZero();
        if (isSavingCartAtShutdown)
            updateShoppingCart(); // Should be used if the cart SHOULD be saved at shutdown
    }

    @Override
    public void setModel(Model m) {
        this.model = m;
    }
}
