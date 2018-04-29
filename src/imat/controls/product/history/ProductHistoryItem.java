package imat.controls.product.history;

import imat.utils.FXMLLoader;
import imat.views.history.HistoryController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;

public class ProductHistoryItem extends AnchorPane {

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

    private final Product product;

    private final HistoryController historyController;

    public ProductHistoryItem(Product product, HistoryController historyController) {
        this.product = product;
        this.historyController = historyController;
        FXMLLoader.loadFXMLFromRootPackage("product_history_item.fxml", this, this);
        
        productImageView.setImage(new Image("/images/products/" + product.getImageName()));
        productNameLabel.setText(product.getName());
        priceLabel.setText(String.valueOf(product.getPrice()) + " kr");
        if (!product.isEcological()) {
            ecoLabel.setVisible(false);
        }
    }

    @FXML
    private void addToCartButtonOnAction(Event event) {
        // TODO Add the right number of the relevant product to the current cart
    }

}
