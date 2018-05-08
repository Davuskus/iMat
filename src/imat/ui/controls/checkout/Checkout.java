package imat.ui.controls.checkout;

import imat.enums.NavigationTarget;
import imat.interfaces.IShoppingListener;
import imat.model.FXMLController;
import imat.ui.controls.checkout.item.CheckoutItem;
import imat.utils.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Checkout extends FXMLController implements IShoppingListener {

    @FXML
    private Label PriceLabel;

    @FXML
    private Label shippingCostLabel;

    @FXML
    private Label totalCostLable;

    @FXML
    private VBox VBoxflow;

    private Map<Product, Node> productsInCheckout = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model.addShoppingListener(this);
        model.getProductsInCart().forEach(this::addItemNode);
        updateLabels(model.getCartPrice());
    }


    private void updateLabels(double totalPrice) {

        String price = totalPrice + " kr";
        PriceLabel.setText(price);
        shippingCostLabel.setText("35 kr");
        double tot = totalPrice + 35;
        totalCostLable.setText(tot + " kr");

    }

    private void addItemNode(Product product) {
        if (productsInCheckout.containsKey(product)) return;
        CheckoutItem checkoutItemController = new CheckoutItem(product);
        checkoutItemController.setModel(model);
        Node checkoutItemNode = FXMLLoader.loadFXMLNodeFromRootPackage("item/checkoutItem.fxml", this, checkoutItemController);
        productsInCheckout.put(product, checkoutItemNode);
        VBoxflow.getChildren().add(checkoutItemNode);
    }

    private void removeItemNode(Product product) {
        VBoxflow.getChildren().remove(productsInCheckout.get(product));
        productsInCheckout.remove(product);
    }

    @FXML
    private void onPayButtonAction() {
        model.navigate(NavigationTarget.PAYMENT);
    }

    @Override
    public void onProductAdded(Product product, Double amount) {
        addItemNode(product);
    }

    @Override
    public void onProductRemoved(Product product, Double oldAmount) {
        removeItemNode(product);
    }

    @Override
    public void onProductUpdate(Product product, Double newAmount) {
        updateLabels(model.getCartPrice());
    }
}
