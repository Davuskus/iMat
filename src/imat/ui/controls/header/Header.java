package imat.ui.controls.header;

import imat.model.FXMLController;
import imat.ui.controls.search.SearchField;
import imat.enums.NavigationTarget;
import imat.ui.controls.spinner.AmountSpinner;
import imat.utils.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Header extends FXMLController {

    @FXML
    private AnchorPane searchFieldPane;

    @FXML
    private SearchField searchFieldController;

    @FXML private void onHelpButtonAction() {
        model.navigate(NavigationTarget.HELP);
    }

    @FXML private void onHistoryButtonAction() {
        model.navigate(NavigationTarget.HISTORY);
    }

    @FXML private void onHomeButtonAction() {
        model.navigate(NavigationTarget.HOME);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchFieldController.setModel(model);
    }
}