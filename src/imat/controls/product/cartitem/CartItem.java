package imat.controls.product.cartitem;

import imat.controls.spinner.AmountSpinner;
import imat.interfaces.ChangeListener;
import imat.interfaces.RemoveRequestListener;
import imat.utils.FXMLLoader;
import imat.utils.IMatUtils;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CartItem extends AnchorPane implements Initializable {

    @FXML
    private Label nameLabel;

    @FXML
    private AmountSpinner amountSpinner;

    @FXML
    private Label priceLabel;

    @FXML
    private ImageView removeButton;

    private final ShoppingItem shoppingItem;

    private final List<RemoveRequestListener<CartItem>> removeRequestListeners;

    private final List<ChangeListener<Double>> priceChangeListeners;

    public CartItem(ShoppingItem shoppingItem) {
        this.shoppingItem = shoppingItem;
        FXMLLoader.loadFXMLFromRootPackage("cart_item.fxml", this, this);
        removeRequestListeners = new ArrayList<>(1);
        priceChangeListeners = new ArrayList<>(1);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        switch (shoppingItem.getProduct().getUnitSuffix()) {
            case "l":
            case "kg":
                amountSpinner.setAcceptDoubles(true);
                break;
        }
        amountSpinner.addChangeListener(this::onSpinnerChange);
        amountSpinner.setAmount(shoppingItem.getAmount());

        nameLabel.setText(this.shoppingItem.getProduct().getName());
        updatePriceLabel();
    }

    @FXML
    private void removeButtonOnAction(Event event) {
        sendRemoveRequest();
    }

    private void sendRemoveRequest() {
        for (RemoveRequestListener<CartItem> removeRequestListener : removeRequestListeners) {
            removeRequestListener.onRemoveRequest(this);
        }
    }

    private void sendPriceChangeNotification(double oldPrice, double newPrice) {
        for (ChangeListener<Double> changeListener : priceChangeListeners) {
            changeListener.onChange(oldPrice, newPrice);
        }
    }

    public void addRemoveRequestListener(RemoveRequestListener<CartItem> removeRequestListener) {
        removeRequestListeners.add(removeRequestListener);
    }

    public void addPriceChangeListener(ChangeListener<Double> priceChangeListener) {
        priceChangeListeners.add(priceChangeListener);
    }

    public void onSpinnerChange(Double oldValue, Double newValue) {
        if (newValue <= 0) {
            sendRemoveRequest();
        } else {
            double oldPrice = shoppingItem.getTotal();
            shoppingItem.setAmount(newValue);
            updatePriceLabel();
            sendPriceChangeNotification(oldPrice, shoppingItem.getTotal());
        }
    }

    private void updatePriceLabel() {
        priceLabel.setText(String.valueOf(this.shoppingItem.getTotal()));
    }

    public ShoppingItem getShoppingItem() {
        return IMatUtils.cloneShoppingItem(shoppingItem);
    }
}
