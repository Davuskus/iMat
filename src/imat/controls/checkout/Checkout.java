package imat.controls.checkout;

import imat.Model;
import imat.interfaces.IFXMLController;
import imat.utils.FXMLLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Checkout implements Initializable,IFXMLController{

    @FXML
    private Label PriceLabel;

    @FXML
    private Label shippingCostLabel;

    @FXML
    private Label totalCostLable;

    @FXML
    private FlowPane flow;

    private final IMatDataHandler iMatDataHandler;

    private Model model;


    public Checkout() {
        iMatDataHandler = IMatDataHandler.getInstance();

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
      update();
    }


    private  void update(){
        updateList();
        updateLabels();
    }
    private void updateList(){
        iMatDataHandler.getShoppingCart().addProduct(iMatDataHandler.getProduct(87));
        List<ShoppingItem> list=iMatDataHandler.getShoppingCart().getItems();

        System.out.println(list.size() +"gömbkgfmknmgf");
        flow.getChildren().clear();

        for (ShoppingItem i:list) {
            CheckoutItem checkoutItem =new CheckoutItem(i);
            checkoutItem.setModel(model);
            Node item=FXMLLoader.loadFXMLNodeFromRootPackage("checkoutItem.fxml",this, checkoutItem);
            flow.getChildren().add(item);
        }


    }

    private void updateLabels(){

        String price=iMatDataHandler.getShoppingCart().getTotal()+ " kr";
        PriceLabel.setText(price);
        shippingCostLabel.setText("35 kr");
        double tot =iMatDataHandler.getShoppingCart().getTotal()+35;
        totalCostLable.setText( tot +" kr");

    }


    @Override
    public void setModel(Model m) {
        this.model=m;
    }
}
