package imat.controls.header;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Header extends AnchorPane {
    public Header(){
        imat.util.FXMLLoader.loadFXMLFromRootPackage("header.fxml",this, this);
    }
}
