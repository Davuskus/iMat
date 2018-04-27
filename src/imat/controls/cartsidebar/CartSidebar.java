package imat.controls.cartsidebar;

import imat.utils.FXMLLoader;
import javafx.scene.layout.VBox;

public class CartSidebar extends VBox{
    public CartSidebar(){
        FXMLLoader.loadFXMLFromRootPackage("cart_sidebar.fxml",this, this);
    }
}
