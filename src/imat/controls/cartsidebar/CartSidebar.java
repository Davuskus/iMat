package imat.controls.cartsidebar;

import imat.FXMLController;
import imat.controls.product.cartitem.CartItem;
import imat.enums.NavigationTarget;
import imat.interfaces.IShoppingListener;
import imat.utils.FXMLLoader;
import imat.utils.MathUtils;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class CartSidebar extends FXMLController implements IShoppingListener {

    @FXML
    private VBox cartItemVBox;

    @FXML
    private Label sumLabel;

    @FXML
    private Button toCheckoutButton;

    @FXML
    private Button trashButton;

    @FXML
    private AnchorPane regretPane;

    @FXML
    private ScrollPane scrollPane;

    private final Map<Product, Node> productsInSidebar = new HashMap<>();

    private final Map<Product, Node> productsInTrash = new HashMap<>();

    private double cartPrice;

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
        if (productsInSidebar.containsKey(product)) return;
        CartItem cartItemController = new CartItem(product, () -> {
            removeCartNode(product);
        });
        cartItemController.setModel(model);
        Node cartItemNode = FXMLLoader.loadFXMLNodeFromRootPackage("../product/cartitem/cart_item.fxml", this, cartItemController);
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
        model.navigate(NavigationTarget.PAY);
    }

    @FXML
    private void trashButtonOnAction(Event event) {
        productsInTrash.putAll(productsInSidebar);
        for (int i = 0; i < productsInSidebar.keySet().size(); i++) {
            removeCartNode(productsInSidebar.keySet().iterator().next());
        }
        switchView(regretPane);
        // TODO Add regret button animation
    }

    @FXML
    private void regretButtonOnAction(Event event) {
        for (Product product : productsInTrash.keySet()) {
            addCartNode(product);
        }
        productsInTrash.clear();
        switchView(scrollPane);
    }

    @Override
    public void onProductAdded(Product product, Double amount) {
        addCartNode(product);
    }

    @Override
    public void onProductRemoved(Product product, Double oldAmount) {
        cartPrice = model.getCartPrice();
        updateSumLabel();
        disableCheckoutButtonIfPriceIsZero();
    }

    @Override
    public void onProductUpdate(Product product, Double newAmount) {
        cartPrice = model.getCartPrice();
        updateSumLabel();
        disableCheckoutButtonIfPriceIsZero();
    }

    private void switchView(Node node) {
        node.toFront();
    }

}
