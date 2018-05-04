package imat.controls.header;

import imat.FXMLController;
import imat.controls.search.SearchField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Header extends FXMLController {

    @FXML
    AnchorPane searchField;

    @FXML
    SearchField searchFieldController;

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
        searchFieldController.setModel(model);
    }
}
