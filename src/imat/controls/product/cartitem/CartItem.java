package imat.controls.product.cartitem;

import imat.FXMLController;
import imat.controls.spinner.AmountSpinner;
import imat.interfaces.ChangeListener;
import imat.interfaces.RemoveRequestListener;
import imat.interfaces.ShoppingListener;
import imat.utils.FXMLLoader;
import imat.utils.IMatUtils;
import imat.utils.MathUtils;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.net.URL;
import java.util.*;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class CartItem extends FXMLController implements ShoppingListener {

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

    private final boolean isAcceptingDoubles;

    private final long millisBeforeRemoval = 2000;

    private boolean shouldBeRemoved;

    private Pattern textPattern;

    public CartItem(Product product) {
        this.product = product;

        switch (product.getUnitSuffix()) {
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
    }

    private void updateInfo() {
        /*updateTextFieldValue();
        updatePriceLabel();*/
    }

    private void updateTextFieldValue(double value) {
        if (isAcceptingDoubles) {
            amountTextField.setText(String.valueOf(value));
        } else {
            amountTextField.setText(String.valueOf((int) value));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AmountSpinner spinnerController = new AmountSpinner(product);
        spinnerController.setModel(model);
        Node spinnerNode = FXMLLoader.loadFXMLNodeFromRootPackage("../../spinner/amount_spinner.fxml",this, spinnerController);
        itemHBox.getChildren().add(spinnerNode);

        nameLabel.setText(this.product.getName());

        if (!product.isEcological()) {
            infoVBox.getChildren().remove(ecoLabel);
        }

        unitLabel.setText("(" + product.getUnit() + ")");

        model.addShoppingListener(this);

        updateInfo();
    }

    @FXML
    private void onEnterPressed(Event event) {
        /*submitNewAmount(Double.valueOf(amountTextField.getText()));*/
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
                       // sendRemoveRequest();
                    }
                });
                timer.cancel();
            }
        }, millisBeforeRemoval);

    }

    private void updatePriceLabel(double total) {
        priceLabel.setText("Pris: " + String.valueOf(MathUtils.round(total, 2)) + " kr");
    }

    @Override
    public void onProductAdded(Product product, Double amount) {

    }

    @Override
    public void onProductRemoved(Product product, Double oldAmount) {

    }

    @Override
    public void onProductUpdate(Product product, Double newAmount) {
        if(product != this.product) return;
        updatePriceLabel(product.getPrice() * newAmount);
    }
}
