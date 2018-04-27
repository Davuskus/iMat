package imat.controls.cartitem;

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

}
