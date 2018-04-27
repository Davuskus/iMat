package imat.controls.cartitem;

import imat.util.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class CartItem extends AnchorPane {

    @FXML
    private Label nameLabel;

    @FXML
    private Label countLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private ImageView removeButton;

    public CartItem() {

        FXMLLoader.loadFXMLFromRootPackage("cart_item.fxml", this, this);

    }

}
