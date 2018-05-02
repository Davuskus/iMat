package imat.controls.product.cartitem;

import imat.interfaces.ChangeListener;
import imat.interfaces.RemoveRequestListener;
import imat.utils.FXMLLoader;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.util.ArrayList;
import java.util.List;

public class CartItem extends AnchorPane implements ChangeListener<Integer> {

    @FXML
    private Label nameLabel;

    @FXML
    private AnchorPane productCountSpinner;

    @FXML
    private Label priceLabel;

    @FXML
    private ImageView removeButton;

    private final ShoppingItem shoppingItem;

    private final List<RemoveRequestListener<CartItem>> removeRequestListeners;

    public CartItem(ShoppingItem shoppingItem) {
        this.shoppingItem = shoppingItem;
        FXMLLoader.loadFXMLFromRootPackage("cart_item.fxml", this, this);

        removeRequestListeners = new ArrayList<>(1);
        nameLabel.setText(this.shoppingItem.getProduct().getName());
        updatePriceLabel();
    }

    @FXML
    private void removeButtonOnAction(Event event) {
        for (RemoveRequestListener<CartItem> removeRequestListener : removeRequestListeners) {
            removeRequestListener.onRemoveRequest(this);
        }
    }

    public void addRemoveRequestListener(RemoveRequestListener<CartItem> removeRequestListener) {
        removeRequestListeners.add(removeRequestListener);
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
