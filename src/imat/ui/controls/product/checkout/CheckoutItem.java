package imat.ui.controls.product.checkout;

import imat.interfaces.IRemoveEvent;
import imat.interfaces.IShoppingListener;
import imat.model.FXMLController;
import imat.ui.controls.spinner.AmountSpinner;
import imat.utils.AnimationHandler;
import imat.utils.DelayedRunnable;
import imat.utils.MathUtils;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.ResourceBundle;

public class CheckoutItem extends FXMLController implements IShoppingListener {
    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label productName;

    @FXML
    private Label price;

    @FXML
    private Label total;

    @FXML
    private Button regretButton;

    @FXML
    private HBox infoHBox;

    @FXML
    private AnchorPane regretPane;

    @FXML
    private Button removeButton;

    @FXML
    private ImageView removeButtonImageView;

    @FXML
    private Label ecoLabel;

    private Product product;


    private final long millisBeforeRemoval = 3000;

    private boolean shouldBeRemoved;


    private final IRemoveEvent removeEvent;

    private double amountBeforeRemoveRequest;

    private DelayedRunnable delayedRunnable;


    @FXML
    private AmountSpinner amountSpinnerController;

    public CheckoutItem(Product product, IRemoveEvent removeEvent) {
        this.removeEvent = removeEvent;
        this.product = product;
    }

    private void updateTotal(double amount) {
        total.setText(MathUtils.asPriceTag(amount * product.getPrice()));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //  VBoxSpinner.getChildren().clear();
        amountSpinnerController.setModel(model);
        amountSpinnerController.setProduct(product);
        amountSpinnerController.setAmount(model.getProductAmount(product));
        model.addShoppingListener(this);
        model.addCheckoutItemRemoveEvent(removeEvent);

        productName.setText(product.getName());
        price.setText(MathUtils.asPriceTag(product.getPrice(), product.getUnit()));
        updateTotal(model.getProductAmount(product));

        ecoLabel.setVisible(product.isEcological());
    }


    private void switchView(Node view) {
        view.toFront();
    }

    @Override
    public void onProductAdded(Product product, Double amount) {
        if (shouldBeRemoved && product.equals(this.product)) {
            regretButtonOnAction(null);
        }
    }

    @Override
    public void onProductUpdate(Product product, Double newAmount) {
        if (product == this.product) updateTotal(newAmount);
    }


    @Override
    public void onProductRemoved(Product product, Double oldAmount) {
        if (product != this.product) return;
        setAmountBeforeRemoveRequest(oldAmount);
        startRemovalProcess();

    }

    @FXML
    private void regretButtonOnAction(Event event) {
        shouldBeRemoved = false;
        model.updateShoppingCart(product, amountBeforeRemoveRequest);
        switchView(infoHBox);

    }

    @FXML
    private void removeButtonOnAction(Event event) {
        setAmountBeforeRemoveRequest(model.getProductAmount(product));
        model.updateShoppingCart(product, 0);
    }

    private void setAmountBeforeRemoveRequest(double amount) {
        if (amount != 0)
            amountBeforeRemoveRequest = amount;
    }

    private void startRemovalProcess() {

        switchView(regretPane);

        shouldBeRemoved = true;

        delayedRunnable = new DelayedRunnable(new Runnable() {
            @Override
            public void run() {
                if (shouldBeRemoved && delayedRunnable.getRunnable() == this) {
                    shouldBeRemoved = false;
                    regretButton.setDisable(true);
                    Timeline removalAnimation = AnimationHandler.getAnimation(
                            v -> removeEvent.execute(),
                            AnimationHandler.getOpacityChangeKeyFrame(regretButton, 250, 0),
                            AnimationHandler.getOpacityChangeKeyFrame(infoHBox, 250, 0),
                            AnimationHandler.getHeightChangeKeyFrame(rootPane, 500, 0),
                            AnimationHandler.getOpacityChangeKeyFrame(rootPane, 500, 0),
                            AnimationHandler.getXTranslationKeyFrame(rootPane, 500, rootPane.getWidth())
                    );
                    removalAnimation.play();
                }
            }
        });
        delayedRunnable.runLater(millisBeforeRemoval);

    }

    @FXML
    public void removeButtonMouseEntered() {
        removeButtonImageView.setImage(new Image("/imat/resources/images/icons/close/icon_close_hover.png"));
    }

    @FXML
    public void removeButtonMousePressed() {
        removeButtonImageView.setImage(new Image("/imat/resources/images/icons/close/icon_close_pressed.png"));
    }

    @FXML
    public void removeButtonMouseExited() {
        removeButtonImageView.setImage(new Image("/imat/resources/images/icons/close/icon_close.png"));
    }

}
