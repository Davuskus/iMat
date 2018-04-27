package imat.controls.product.menuitem;

import imat.controls.product.spinner.ProductCountSpinner;
import imat.interfaces.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;

public class ProductMenuItem extends AnchorPane {

    @FXML
    private ImageView imageView;

    @FXML
    private ProductCountSpinner spinner;

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label ecoLabel;

    private Product product;

    /**
     * Sets the product and updates the menu item's information accordingly.
     *
     * @param product The product to use.
     */
    public void setProduct(Product product) {
        this.product = product;
        imageView.setImage(new Image("res/images/" + product.getImageName()));
        nameLabel.setText(product.getName());
        priceLabel.setText(String.valueOf(product.getPrice()) + " kr");
        if (!product.isEcological()) {
            ecoLabel.setVisible(false);
        }
    }

    /**
     * Returns the menu item's displayed product.
     *
     * @return The currently used product.
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Adds a a listener that will get notified when the product count spinner's value changes.
     *
     * @param listener The listener to add.
     */
    public void addChangeListener(ChangeListener<Integer> listener) {
        spinner.addChangeListener(listener);
    }

}
