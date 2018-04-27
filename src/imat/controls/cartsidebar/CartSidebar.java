package imat.controls.cartsidebar;

import javafx.scene.layout.VBox;

public class CartSidebar extends VBox{
    public CartSidebar(){
        imat.util.FXMLLoader.loadFXMLFromRootPackage("cart_sidebar.fxml",this, this);
    }
}
