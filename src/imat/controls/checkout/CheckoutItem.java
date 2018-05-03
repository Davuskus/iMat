package imat.controls.checkout;

import imat.FXMLController;
import imat.controls.spinner.AmountSpinner;
import imat.interfaces.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.net.URL;
import java.util.ResourceBundle;

public class CheckoutItem extends FXMLController implements ChangeListener<Double>{
    @FXML
    private Label produktName;

    @FXML
    private Label price;

    @FXML
    private Label total;

    @FXML
    private AmountSpinner spinner;


    private ShoppingItem item;


    public CheckoutItem(ShoppingItem item) {

        this.item=item;

    }


    @Override
    public void onChange(Double oldValue, Double newValue) {
        item.setAmount(newValue);
        updateInfo();
    }

    private void updateInfo(){
        produktName.setText(item.getProduct().getName());
        price.setText(String.valueOf(item.getProduct().getPrice())+" kr");
        total.setText(String.valueOf(item.getTotal())+ "kr");

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateInfo();
    }
}
