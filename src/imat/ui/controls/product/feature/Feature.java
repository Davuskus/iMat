package imat.ui.controls.product.feature;

import imat.enums.NavigationTarget;
import imat.model.FXMLController;
import javafx.fxml.FXML;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Feature extends FXMLController {

    private int currentProductIndex;
    private final List<Product> products;

    public Feature() {
        super();
        products = new ArrayList<>(4);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void showProductDetails() {
        model.showProductDetails(products.get(currentProductIndex));
        model.navigate(NavigationTarget.PRODUCT_DETAILS);
    }

}
