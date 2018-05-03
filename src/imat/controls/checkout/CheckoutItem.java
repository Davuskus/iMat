package imat.controls.checkout;

import imat.FXMLController;

import imat.controls.spinner.AmountSpinner;
import imat.interfaces.ChangeListener;
import imat.utils.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.IMatDataHandler;
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
    private VBox VBoxSpinner;

    private ShoppingItem item;

    private Checkout checkout;

    public CheckoutItem(ShoppingItem item, Checkout c) {

        checkout=c;
        this.item=item;


    }


    @Override
    public void onChange(Double oldValue, Double newValue) {

        item.setAmount(newValue);
        updateInfo();
        checkout.update();

    }

    private void updateInfo(){
        produktName.setText(item.getProduct().getName());
        price.setText(String.valueOf(item.getProduct().getPrice())+" kr");
        total.setText(String.valueOf(item.getTotal())+ "kr");

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        VBoxSpinner.getChildren().clear();
        AmountSpinner spinner = new AmountSpinner(item.getProduct());
        spinner.setModel(model);
        Node btn = FXMLLoader.loadFXMLNodeFromRootPackage("../spinner/amount_spinner.fxml",this, spinner);
        VBoxSpinner.getChildren().add(btn);

        updateInfo();
    }


}
