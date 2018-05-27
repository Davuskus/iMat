package imat.ui.controls.history.article;

import imat.interfaces.ICartTrashListener;
import imat.model.FXMLController;
import imat.ui.controls.spinner.AmountSpinner;
import imat.utils.IMatUtils;
import imat.utils.ImageUtils;
import imat.utils.MathUtils;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.net.URL;
import java.util.ResourceBundle;

public class ArticleHistoryItem extends FXMLController implements Initializable, ICartTrashListener {

    @FXML
    private ImageView productImageView;

    @FXML
    private Label productNameLabel;

    @FXML
    private Label ecoLabel;

    @FXML
    private Label countLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private VBox articleInfoVBox;

    @FXML
    private AnchorPane spinner;

    @FXML
    private AmountSpinner spinnerController;

    @FXML
    private Button addToCartButton;

    private ShoppingItem shoppingItem;

    @FXML
    private void copyToCartButtonOnAction(Event event) {
        model.addToShoppingCart(shoppingItem.getProduct(), shoppingItem.getAmount());
    }

    public ShoppingItem getShoppingItem() {
        return IMatUtils.cloneShoppingItem(shoppingItem);
    }

    public void setShoppingItem(ShoppingItem shoppingItem) {
        this.shoppingItem = IMatUtils.cloneShoppingItem(shoppingItem);

        Product product = this.shoppingItem.getProduct();

        spinnerController.setProduct(product);

        productImageView.setImage(ImageUtils.getSquareImage(new Image("/imat/resources/images/products/" + product.getImageName())));

        productNameLabel.setText(product.getName());

        if (!product.isEcological()) {
            articleInfoVBox.getChildren().remove(ecoLabel);
        }

        priceLabel.setText(MathUtils.asPriceTag(this.shoppingItem.getTotal()));

        boolean suffixIsKg = shoppingItem.getProduct().getUnitSuffix().equals("kg");
        boolean suffixIsL = shoppingItem.getProduct().getUnitSuffix().equals("l");
        if (suffixIsKg || suffixIsL) {
            countLabel.setText(String.valueOf(MathUtils.round(shoppingItem.getAmount(), 1)) + " " + product.getUnitSuffix());
        } else {
            countLabel.setText(String.valueOf((int) this.shoppingItem.getAmount()) + " " + product.getUnitSuffix());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        spinnerController.setModel(model);
        model.addCartTrashListener(this);
        setDisableOnControls(model.isCartBeingThrownInTheTrash());

    }

    private void setDisableOnControls(boolean disable) {
        addToCartButton.setDisable(disable);
        addToCartButton.setOpacity(disable ? 0.5 : 1);
    }

    @Override
    public void onCartTrashStarted() {
        setDisableOnControls(true);
    }

    @Override
    public void onCartTrashStopped() {
        setDisableOnControls(false);
    }

}
