package imat.controls.header;

import javafx.scene.layout.AnchorPane;

public class Header extends AnchorPane {
    public Header(){
        imat.utils.FXMLLoader.loadFXMLFromRootPackage("header.fxml",this, this);
    }
}
