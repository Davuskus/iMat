package imat.ui.controls.product.menuitem;

import imat.model.FXMLController;
import imat.ui.controls.spinner.AmountSpinner;
import imat.utils.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductMenuItem extends FXMLController {

    @FXML
    private ImageView imageView;

    @FXML
    private AmountSpinner amountSpinnerController;

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label ecoLabel;

    @FXML private VBox elementsVBox;

    private Product product;

    public ProductMenuItem(Product product) {
        this.product = product;
    }

    /**
     * Sets the product and updates the menu item's information accordingly.
     *
     * @param product The product to use.
     */
    public void setProduct(Product product) {
        this.product = product;
        imageView.setImage(new Image("/imat/resources/images/products/" + product.getImageName()));
        nameLabel.setText(product.getName());
        priceLabel.setText(String.valueOf(product.getPrice()) + " " + product.getUnit());
        if (!product.isEcological()) {
            ecoLabel.setVisible(false);
        }
        amountSpinnerController.setProduct(product);
    }

    /**
     * Returns the menu item's displayed product.
     *
     * @return The currently used product.
     */
    public Product getProduct() {
        return product;
    }

    @FXML
    public void showProductDetails() {
        model.showProductDetails(product);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        amountSpinnerController.setModel(model);
        setProduct(product);
    }

}
