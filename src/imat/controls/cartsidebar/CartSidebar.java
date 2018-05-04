package imat.controls.cartsidebar;

import imat.Model;
import imat.controls.product.cartitem.CartItem;
import imat.enums.NavigationTarget;
import imat.interfaces.IFXMLController;
import imat.interfaces.RemoveRequestListener;
import imat.interfaces.IShoppingListener;
import imat.utils.FXMLLoader;
import imat.utils.MathUtils;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.*;

// TODO Show a regret-button when the trash-button has been pressed. The same principle as for CartItem.

public class CartSidebar implements Initializable, IShoppingListener, IFXMLController {

    @FXML
    private VBox cartItemVBox;

    @FXML
    private Label sumLabel;

    @FXML
    private Button toCheckoutButton;

    @FXML
    private Button trashButton;

    private Map<Product, Node>  productsInSidebar = new HashMap<>();

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
        sumLabel.setText(MathUtils.round(cartPrice, 2) + " kr");
    }

    private void loadCart() {
        model.getProductsInCart().forEach(this::addCartNode);
    }

    private void addCartNode(Product product) {
        if(productsInSidebar.containsKey(product)) return;
        CartItem cartItemController = new CartItem(product, () -> {
            removeCartNode(product);
        });
        cartItemController.setModel(model);
        Node cartItemNode = FXMLLoader.loadFXMLNodeFromRootPackage("../product/cartitem/cart_item.fxml",this,cartItemController);
        productsInSidebar.put(product, cartItemNode);
        cartItemVBox.getChildren().add(cartItemNode);
    }

    private void removeCartNode(Product product) {
        cartItemVBox.getChildren().remove(productsInSidebar.get(product));
        productsInSidebar.remove(product);
    }

    private void disableCheckoutButtonIfPriceIsZero() {
        toCheckoutButton.setDisable(cartPrice <= 0);
    }

    @FXML
    private void toCheckoutButtonOnAction(Event event) {
        if (!isSavingCartAtShutdown)
           // updateShoppingCart(); // Should be used if the cart should NOT be saved at shutdown
        model.navigate(NavigationTarget.PAY);
    }

    @FXML
    private void trashButtonOnAction(Event event) {
        model.clearCart();
    }

    @FXML
    private void regretButtonOnAction(Event event) {

    }

    @Override
    public void setModel(Model m) {
        this.model = m;
    }

    @Override
    public void onProductAdded(Product product, Double amount) {
        addCartNode(product);
    }

    @Override
    public void onProductRemoved(Product product, Double oldAmount) {
        // removeCartNode(product);
        cartPrice = model.getCartPrice();
        updateSumLabel();
        disableCheckoutButtonIfPriceIsZero();
    }

    @Override
    public void onProductUpdate(Product product, Double newAmount) {
        cartPrice = model.getCartPrice();
        updateSumLabel();
        disableCheckoutButtonIfPriceIsZero();
        if (isSavingCartAtShutdown) {}
          //  updateShoppingCart(); // Should be used if the cart SHOULD be saved at shutdown
    }
}
