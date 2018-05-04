package imat.controls.product.cartitem;

import imat.FXMLController;
import imat.controls.spinner.AmountSpinner;
import imat.interfaces.IRemoveEvent;
import imat.interfaces.IShoppingListener;
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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.ResourceBundle;

public class CartItem extends FXMLController implements IShoppingListener {

    @FXML
    private AnchorPane rootPane;

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

    @FXML
    private VBox infoVBox;

    @FXML
    private Label ecoLabel;

    @FXML
    private Label unitLabel;

    private final Product product;

    private final long millisBeforeRemoval = 3000;

    private boolean shouldBeRemoved;

    private final IRemoveEvent removeEvent;

    private double amountBeforeRemoveRequest;

    public CartItem(Product product, IRemoveEvent removeEvent) {
        this.removeEvent = removeEvent;
        this.product = product;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AmountSpinner spinnerController = new AmountSpinner(product);
        spinnerController.setModel(model);
        Node spinnerNode = FXMLLoader.loadFXMLNodeFromRootPackage("../../spinner/amount_spinner.fxml", this, spinnerController);
        itemHBox.getChildren().add(spinnerNode);

        nameLabel.setText(this.product.getName());

        if (!product.isEcological()) {
            infoVBox.getChildren().remove(ecoLabel);
        }

        unitLabel.setText("(" + product.getUnit() + ")");

        model.addShoppingListener(this);
    }

    @FXML
    private void onEnterPressed(Event event) {
        /*submitNewAmount(Double.valueOf(amountTextField.getText()));*/
    }

    @FXML
    private void regretButtonOnAction(Event event) {
        shouldBeRemoved = false;
        model.updateShoppingCart(product, amountBeforeRemoveRequest);
        switchView(itemHBox);
    }

    @FXML
    private void removeButtonOnAction(Event event) {
        setAmountBeforeRemoveRequest(model.getProductAmount(product));
        startRemovalProcess();
    }


    private void removeIfAmountIsZero() {
        if (Double.valueOf(amountTextField.getText()) == 0 || amountTextField.getText().equals("")) {
            startRemovalProcess();
        }
    }

    private void switchView(Node view) {
        view.toFront();
    }

    private void startRemovalProcess() {

        switchView(regretPane);

        shouldBeRemoved = true;

        DelayedRunnable delayedRunnable = new DelayedRunnable(() -> {
            if (shouldBeRemoved) {
                regretButton.setDisable(true);

                Timeline removalAnimation = AnimationHandler.getAnimation(
                        v -> {
                            removeEvent.execute();
                            model.updateShoppingCart(product, 0.0);
                        },
                        AnimationHandler.getOpacityChangeKeyFrame(regretButton, 250, 0),
                        AnimationHandler.getOpacityChangeKeyFrame(itemHBox, 250, 0),
                        AnimationHandler.getHeightChangeKeyFrame(rootPane, 500, 0),
                        AnimationHandler.getOpacityChangeKeyFrame(rootPane, 500, 0)
                );
                removalAnimation.play();
            }
        });
        delayedRunnable.runLater(millisBeforeRemoval);

    }

    private void updatePriceLabel(double total) {
        priceLabel.setText("Pris: " + String.valueOf(MathUtils.round(total, 2)) + " kr");
    }

    @Override
    public void onProductAdded(Product product, Double amount) {

    }

    @Override
    public void onProductRemoved(Product product, Double oldAmount) {
        if (product != this.product) return;
        setAmountBeforeRemoveRequest(oldAmount);
        startRemovalProcess();
    }

    private void setAmountBeforeRemoveRequest(double amount) {
        if (amount != 0)
            amountBeforeRemoveRequest = amount;
    }

    @Override
    public void onProductUpdate(Product product, Double newAmount) {
        if (product != this.product) return;
        setAmountBeforeRemoveRequest(model.getProductAmount(product));
        updatePriceLabel(product.getPrice() * newAmount);
    }
}
