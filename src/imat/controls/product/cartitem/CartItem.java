package imat.controls.product.cartitem;

import imat.interfaces.ChangeListener;
import imat.interfaces.RemoveRequestListener;
import imat.utils.FXMLLoader;
import imat.utils.IMatUtils;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.net.URL;
import java.util.*;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class CartItem extends AnchorPane implements Initializable {

    @FXML
    private StackPane stackPane;

    @FXML
    private AnchorPane regretPane;

    @FXML
    private Button regretButton;

    @FXML
    private HBox itemHBox;

    @FXML
    private Button removeButton;

    @FXML
    private ImageView removeButtonImageView;

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private AnchorPane amountSpinnerPane;

    @FXML
    private TextField amountTextField;

    @FXML
    private Button addButton;

    @FXML
    private Button subtractButton;

    private final ShoppingItem shoppingItem;

    private final List<RemoveRequestListener<CartItem>> removeRequestListeners;

    private final List<ChangeListener<Double>> priceChangeListeners;

    private final boolean isAcceptingDoubles;

    private final long millisBeforeRemoval = 2000;

    private boolean shouldBeRemoved;

    public CartItem(ShoppingItem shoppingItem) {
        this.shoppingItem = shoppingItem;
        removeRequestListeners = new ArrayList<>(1);
        priceChangeListeners = new ArrayList<>(1);

        FXMLLoader.loadFXMLFromRootPackage("cart_item.fxml", this, this);

        nameLabel.setText(this.shoppingItem.getProduct().getName());

        Pattern textPattern;
        switch (shoppingItem.getProduct().getUnitSuffix()) {
            case "l":
            case "kg":
                textPattern = Pattern.compile("\\d*|\\d+\\.\\d*");
                isAcceptingDoubles = true;
                break;
            default:
                textPattern = Pattern.compile("\\d*");
                isAcceptingDoubles = false;
                break;
        }
        amountTextField.setTextFormatter(new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return textPattern.matcher(change.getControlNewText()).matches() ? change : null;
        }));

        updateInfo();
    }

    private void updateInfo() {
        updateTextFieldValue();
        updatePriceLabel();
    }

    private void updateTextFieldValue() {
        if (isAcceptingDoubles) {
            amountTextField.setText(String.valueOf(shoppingItem.getAmount()));
        } else {
            amountTextField.setText(String.valueOf((int) shoppingItem.getAmount()));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void onEnterPressed(Event event) {
        submitNewAmount(Double.valueOf(amountTextField.getText()));
    }

    @FXML
    private void regretButtonOnAction(Event event) {
        shouldBeRemoved = false;
        switchView();
    }

    @FXML
    private void removeButtonOnAction(Event event) {
        startRemovalProcess();
    }

    @FXML
    private void addButtonOnAction(Event event) {
        changeValue(1);
    }

    @FXML
    private void subtractButtonOnAction(Event event) {
        changeValue(-1);
    }

    private void changeValue(double value) {
        double oldValue = shoppingItem.getAmount();
        double newValue = oldValue + value;
        if (newValue >= 0) {
            submitNewAmount(newValue);
        }
    }

    private void submitNewAmount(double value) {
        double oldPrice = shoppingItem.getTotal();
        shoppingItem.setAmount(value);
        updateInfo();
        sendPriceChangeNotification(oldPrice, shoppingItem.getTotal());
        removeIfAmountIsZero();
    }

    private void removeIfAmountIsZero() {
        if (Double.valueOf(amountTextField.getText()) == 0 || amountTextField.getText().equals("")) {
            startRemovalProcess();
        }
    }

    private void switchView() {
        stackPane.getChildren().get(0).toFront();
    }

    private void startRemovalProcess() {

        switchView();

        shouldBeRemoved = true;

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (shouldBeRemoved) {
                        sendRemoveRequest();
                    }
                });
            }
        }, millisBeforeRemoval);


    }

    private void sendRemoveRequest() {
        for (RemoveRequestListener<CartItem> removeRequestListener : removeRequestListeners) {
            removeRequestListener.onRemoveRequest(this);
        }
    }

    public void changeAmount(double change) {
        submitNewAmount(shoppingItem.getAmount() + change);
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

    private void updatePriceLabel() {
        priceLabel.setText(String.valueOf(this.shoppingItem.getTotal()) + " kr");
    }

    public ShoppingItem getShoppingItem() {
        return IMatUtils.cloneShoppingItem(shoppingItem);
    }
}
