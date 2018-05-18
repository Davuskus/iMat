package imat.ui.controls.product.feature.item;

import imat.model.FXMLController;
import imat.ui.controls.spinner.AmountSpinner;
import imat.utils.MathUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.ResourceBundle;

public class FeatureItem extends FXMLController {

    @FXML
    private ImageView imageView;

    @FXML
    private Label nameLabel;

    @FXML
    private Label ecoLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private AnchorPane spinner;

    @FXML
    private AmountSpinner spinnerController;

    @FXML
    private VBox infoVBox;

    private Product product;

    public FeatureItem(Product product) {
        this.product = product;
    }

    /**
     * Sets the product.
     *
     * @param product The product to use.
     */
    public void setProduct(Product product) {
        this.product = product;
        nameLabel.setText(product.getName());
        imageView.setImage(new Image("/imat/resources/images/products/" + product.getImageName()));
        priceLabel.setText(MathUtils.asPriceTag(product.getPrice(), product.getUnit()));
        if (!product.isEcological()) {
            infoVBox.getChildren().remove(ecoLabel);
        }
        spinnerController.setProduct(product);
    }

    /**
     * Returns the displayed product.
     *
     * @return The currently used product.
     */
    public Product getProduct() {
        return product;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        spinnerController.setModel(model);
        setProduct(product);
    }

}
