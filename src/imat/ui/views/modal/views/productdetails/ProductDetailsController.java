package imat.ui.views.modal.views.productdetails;

import imat.model.FXMLController;
import imat.ui.controls.spinner.AmountSpinner;
import imat.utils.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
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
    private Label totalPriceAmountLabel;

    public void setProductInfo(Product product) {
        productNameLabel.setText(product.getName());
        //double amount = Double.parseDouble(product.getUnit());
        //double comparisonPrice = product.getPrice() / amount;

        //comparisonPriceLabel.setText(comparisonPrice + "kr" + "/" + product.getUnitSuffix());

        organicLabel.setText(product.isEcological() ? "Ekologisk" : "Inte ekologisk");

        AmountSpinner spinnerController = new AmountSpinner(product);
        spinnerController.setModel(model);
        //Node amountSpinnerNode = FXMLLoader.loadFXMLNodeFromRootPackage("../../spinner/amount_spinner.fxml",this, spinnerController);
        //rootPane.getChildren().add(amountSpinnerNode);
        rootPane.setLeftAnchor(rootPane, 240.0);
        rootPane.setTopAnchor(rootPane, 140.0);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
