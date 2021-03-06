package imat.ui.views.browse.sidebars.cart;

import imat.enums.NavigationTarget;
import imat.interfaces.IShoppingListener;
import imat.model.FXMLController;
import imat.ui.controls.product.cart.CartItem;
import imat.utils.AnimationHandler;
import imat.utils.DelayedRunnable;
import imat.utils.FXMLLoader;
import imat.utils.MathUtils;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
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
    private ImageView trashButtonImageView;

    @FXML
    private AnchorPane regretPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Button regretButton;

    @FXML
    private StackPane stackPane;

    private final Map<Product, Node> productsInSidebar = new HashMap<>();

    private double cartPrice;

    private boolean shouldTrash;

    private final long millisBeforeTrash = 3000;

    private DelayedRunnable delayedRunnable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model.addShoppingListener(this);
        updateSumLabel();
        loadCart();
        disableCheckoutButtonIfPriceIsZero();
        switchView(scrollPane);
    }

    private void updateSumLabel() {
        sumLabel.setText(MathUtils.asPriceTag(cartPrice));
    }

    private void loadCart() {
        model.getProductsInCart().forEach(this::addCartNode);
        trashButton.setDisable(cartItemVBox.getChildren().size() <= 0);
    }

    private void addCartNode(Product product) {
        if (productsInSidebar.containsKey(product)) return;
        CartItem cartItemController = new CartItem(product, () -> {
            removeCartNode(product);
        });
        cartItemController.setModel(model);
        Node cartItemNode = FXMLLoader.loadFXMLNodeFromRootPackage(
                "../../../../controls/product/cart/cart_item.fxml", this, cartItemController);
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
        model.navigate(NavigationTarget.CHECKOUT);
    }

    @FXML
    private void trashButtonOnAction(Event event) {
        startTrashProcess();
    }

    private final Map<Product, Double> copiedCart = new HashMap<>();

    private void startTrashProcess() {

        shouldTrash = true;
        model.notifyCartTrashListenersOfStart();

        updateCartInfo(0);

        trashButton.setDisable(true);
        regretButton.setDisable(false);
        regretPane.setOpacity(1);
        switchView(regretPane);

        copiedCart.clear();
        model.getProductsInCart().forEach(product -> copiedCart.put(product, model.getProductAmount(product)));
        model.clearCartFast();

        delayedRunnable = new DelayedRunnable(new Runnable() {
            @Override
            public void run() {
                if (shouldTrash && delayedRunnable.getRunnable() == this) {

                    regretButton.setDisable(true);
                    shouldTrash = false;

                    Timeline fadeAnimation = AnimationHandler.getAnimation(
                            v -> {
                                model.notifyCartTrashListenersOfStop();
                                switchView(scrollPane);
                            },
                            AnimationHandler.getOpacityChangeKeyFrame(regretPane, 500, 0)
                    );
                    fadeAnimation.play();

                }
            }
        });
        delayedRunnable.runLater(millisBeforeTrash);

    }

    @FXML
    private void regretButtonOnAction(Event event) {
        shouldTrash = false;
        model.notifyCartTrashListenersOfStop();
        copiedCart.forEach((product, oldAmount) -> model.addToShoppingCart(product, oldAmount));
        regretButton.setDisable(true);
        trashButton.setDisable(false);
        updateCartInfo(model.getCartPrice());
        switchView(scrollPane);
    }

    private void updateCartInfo(double cartPrice) {
        this.cartPrice = cartPrice;
        updateSumLabel();
        disableCheckoutButtonIfPriceIsZero();
    }

    @Override
    public void onProductAdded(Product product, Double amount) {
        if (!shouldTrash) {
            trashButton.setDisable(false);
            addCartNode(product);
        }
    }

    @Override
    public void onProductRemoved(Product product, Double oldAmount) {

        if (model.getProductsInCart().size() == 0) {
            trashButton.setDisable(true);
        }

        updateCartInfo(model.getCartPrice());
    }

    @Override
    public void onProductUpdate(Product product, Double newAmount) {
        if (!shouldTrash) {
            updateCartInfo(model.getCartPrice());
        }
    }

    private void switchView(Node node) {
        stackPane.getChildren().forEach(child -> {
            child.setDisable(true);
            child.setVisible(false);
        });
        node.setDisable(false);
        node.setVisible(true);
        node.toFront();
    }

    @FXML
    public void trashButtonMouseEntered() {
        trashButtonImageView.setImage(new Image("/imat/resources/images/icons/trash/icon_trash_can_hover.png"));
    }

    @FXML
    public void trashButtonMousePressed() {
        trashButtonImageView.setImage(new Image("/imat/resources/images/icons/trash/icon_trash_can_pressed.png"));
    }

    @FXML
    public void trashButtonMouseExited() {
        trashButtonImageView.setImage(new Image("/imat/resources/images/icons/trash/icon_trash_can.png"));
    }

}
