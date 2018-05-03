package imat.controls.checkout;

import imat.FXMLController;

import imat.controls.spinner.AmountSpinner;
import imat.interfaces.IShoppingListener;
import imat.utils.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.ResourceBundle;

public class CheckoutItem extends FXMLController implements IShoppingListener {
    @FXML
    private Label productName;

    @FXML
    private Label price;

    @FXML
    private Label total;

    @FXML
    private VBox VBoxSpinner;

    private Product product;


    public CheckoutItem(Product product) {
        this.product = product;
    }



    private void updateTotal(double amount){
        total.setText(String.valueOf(amount*product.getPrice())+ "kr");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        VBoxSpinner.getChildren().clear();
        AmountSpinner spinner = new AmountSpinner(product);
        spinner.setModel(model);
        Node btn = FXMLLoader.loadFXMLNodeFromRootPackage("../spinner/amount_spinner.fxml",this, spinner);
        VBoxSpinner.getChildren().add(btn);
        model.addShoppingListener(this);

        productName.setText(product.getName());
        price.setText(String.valueOf(product.getPrice())+" kr");
        updateTotal(model.getProductAmount(product));
    }


    @Override
    public void onProductAdded(Product product, Double amount) {

    }

    @Override
    public void onProductRemoved(Product product, Double oldAmount) {

    }

    @Override
    public void onProductUpdate(Product product, Double newAmount) {
        if(product == this.product) updateTotal(newAmount);
    }
}
