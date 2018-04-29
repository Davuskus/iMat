package imat.controls.product.cartitem;

import imat.controllers.MainController;
import imat.interfaces.ChangeListener;
import imat.utils.FXMLLoader;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.ShoppingItem;

public class CartItem extends AnchorPane implements ChangeListener<Integer> {

    @FXML
    private Label nameLabel;

    @FXML
    private AnchorPane productCountSpinner;

    @FXML
    private Label priceLabel;

    @FXML
    private ImageView removeButton;

    private final MainController mainController;

    private final ShoppingItem shoppingItem;

    public CartItem(ShoppingItem shoppingItem, MainController mainController) {
        this.shoppingItem = shoppingItem;
        this.mainController = mainController;
        FXMLLoader.loadFXMLFromRootPackage("cart_item.fxml", this, this);

        nameLabel.setText(this.shoppingItem.getProduct().getName());
        updatePriceLabel();
    }

    @FXML
    private void removeButtonOnAction(Event event) {
        mainController.removeCartItem(this);
    }

    @Override
    public void onChange(Integer oldValue, Integer newValue) {
        shoppingItem.setAmount(newValue);
        updatePriceLabel();
    }

    private void updatePriceLabel() {
        priceLabel.setText(String.valueOf(this.shoppingItem.getTotal()));
    }

    //    public void addChangeListener(ChangeListener<Integer> listener) {
//        productCountSpinner.addChangeListener(listener);
//    }

}
