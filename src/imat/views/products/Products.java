package imat.views.products;

import imat.FXMLController;
import imat.interfaces.ICategoryListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.net.URL;
import java.util.ResourceBundle;

public class Products extends FXMLController implements ICategoryListener {

    @FXML private Label categoryLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model.addCategoryListener(this);
    }

    @Override
    public void onCategorySelected(ProductCategory category) {
        categoryLabel.setText(category.name());
    }
}
