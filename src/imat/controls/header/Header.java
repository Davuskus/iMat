package imat.controls.header;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.AnchorPane;

public class Header {
    @FXML
    private TextField searchBox;

    @FXML
    private void onSearchBoxUpdate(InputEvent event) {
        System.out.println(searchBox.getText());
    }

}
