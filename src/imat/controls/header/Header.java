package imat.controls.header;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Header extends AnchorPane {
    public Header(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/imat/controls/header/header.fxml"));
        fxmlLoader.setRoot(this); fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}