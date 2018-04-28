package imat.views.payment;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class paymentController {

    @FXML
    Button ClientInfoDoneButton;

    @FXML
    Button creditcardDoneButton;

    @FXML
    Button creditcardBackButton;

    @FXML
    Button changeCreditcardInfoButton;

    @FXML
    Button changeClientInfoButton;

    @FXML
    AnchorPane sammanfattning;

    @FXML
    AnchorPane kunduppgifter;

    @FXML
    AnchorPane kreditkortsuppgifter;

    @FXML
    private void credidcardViewToFront(){
        kreditkortsuppgifter.toFront();
    }
    @FXML
    private void summaryViewToFront(){
        sammanfattning.toFront();
    }
    @FXML
    private void clientViewToFront(){
        kunduppgifter.toFront();
    }
}