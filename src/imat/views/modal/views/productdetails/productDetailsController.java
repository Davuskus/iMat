package imat.views.modal.views.productdetails;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import se.chalmers.cse.dat216.project.Product;

public class productDetailsController {
    @FXML
    ImageView productImage;

    @FXML
    Label productNameLabel;

    @FXML
    Label organicLabel;

    @FXML
    Label comparisonPriceLabel;

    @FXML
    Label totalPriceAmountLabel;

    public void setProductInfo(Product product) {
        productNameLabel.setText(product.getName());
        double amount = Double.parseDouble(product.getUnit());
        double comparisonPrice = product.getPrice() / amount;

        comparisonPriceLabel.setText(comparisonPrice + "kr" + "/" + product.getUnitSuffix());

        organicLabel.setText(product.isEcological() ? "Ekologisk" : "Inte ekologisk");
    }
}
