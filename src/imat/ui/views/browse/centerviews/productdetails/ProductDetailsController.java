package imat.ui.views.browse.centerviews.productdetails;

import imat.model.FXMLController;
import imat.ui.controls.spinner.AmountSpinner;
import imat.utils.IMatUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductDetailsController extends FXMLController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private ImageView productImage;

    @FXML
    private Label productNameLabel;

    @FXML
    private Label organicLabel;

    @FXML
    private Label comparisonPriceLabel;

    @FXML
    private AmountSpinner amountSpinnerController;
    private Product product;

    public void setProductInfo(Product product) {
        this.product = product;

        productNameLabel.setText(product.getName());
        productImage.setImage(new Image("/imat/resources/images/products/" + product.getImageName()));
        organicLabel.setText(product.isEcological() ? "Ekologisk" : "");

        amountSpinnerController.setProduct(product);
        String unit = product.getUnit();
        if (unit.equals("kr/kg") || unit.equals("kr/l")) {
            amountSpinnerController.setAcceptDoubles(true);
        } else {
            amountSpinnerController.setAcceptDoubles(false);
        }

    }

    public Product getProduct() {
        return IMatUtils.cloneProduct(product);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        amountSpinnerController.setModel(model);
    }

    public void onBackButtonAction(ActionEvent actionEvent) {
        model.navigateBack();
    }
}
