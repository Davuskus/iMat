package imat.ui.views.modal.payment.confirmation;

// Todo Add approximated delivery time to confirmation text?

import imat.enums.NavigationTarget;
import imat.model.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.application.Platform.exit;

public class Confirmation extends FXMLController {
    @FXML
    Label purchaseConfirmationHeader;

    @FXML
    Label purchaseConfirmationDescription;

    @FXML
    Button confirmationReturnToStoreButton;

    @FXML Button confirmationExitAppButton;

    @FXML
    public void onReturnToStoreButtonAction(ActionEvent actionEvent) {
        model.navigate(NavigationTarget.HOME);
    }

    @FXML
    public void onQuitButtonAction(ActionEvent actionEvent) {
        exit();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
