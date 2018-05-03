package imat.controls.header;

import imat.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class Header extends FXMLController {

    @FXML private void onHelpButtonAction() {
        model.navigate("help");
    }

    @FXML private void onHistoryButtonAction() {
        model.navigate("history");
    }

    @FXML private void onHomeButtonAction() {
        model.navigate("home");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
