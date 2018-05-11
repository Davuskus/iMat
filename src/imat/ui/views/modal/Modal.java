package imat.ui.views.modal;

import imat.enums.NavigationTarget;
import imat.interfaces.INavigationListener;
import imat.model.FXMLController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Modal extends FXMLController implements INavigationListener {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private ImageView closeButtonImageView;

    @FXML
    private AnchorPane helpPane;

    @FXML
    private AnchorPane copyOrderPane;

    @FXML
    private AnchorPane paymentPane;

    @FXML
    private AnchorPane confirmationPane;

    @FXML
    private Label modalTitleLabel;

    @FXML
    private StackPane stackPane;

    @FXML
    public void consumeEvent(Event event) {
        event.consume();
    }

    @FXML
    public void closeButtonOnAction(Event event) {
        model.navigateBack();
    }

    @FXML
    public void closeButtonMouseEntered() {
        closeButtonImageView.setImage(new Image("/imat/resources/images/icons/close/icon_close_hover.png"));
    }

    @FXML
    public void closeButtonMousePressed() {
        closeButtonImageView.setImage(new Image("/imat/resources/images/icons/close/icon_close_pressed.png"));
    }

    @FXML
    public void closeButtonMouseExited() {
        closeButtonImageView.setImage(new Image("/imat/resources/images/icons/close/icon_close.png"));
    }

    // TODO The background pane behind the modal always shows "Home". Fix this.

    @Override
    public void navigateTo(NavigationTarget navigationTarget) {
        switch (navigationTarget) {
            case HELP:
                modalTitleLabel.setText("Hjälp");
                setSize(helpPane);
                helpPane.toFront();
                break;
            case COPY_ORDER:
                modalTitleLabel.setText("Kopiera order");
                setSize(copyOrderPane);
                copyOrderPane.toFront();
                break;
            case PAYMENT:
                modalTitleLabel.setText("Betalning");
                setSize(paymentPane);
                paymentPane.toFront();
                break;
            case CONFIRMATION:
                modalTitleLabel.setText("Beställningen klar");
                confirmationPane.toFront();
                break;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model.addNavigationListener(this);

    }

    private void setSize(AnchorPane anchorPane){
        stackPane.setMaxSize(anchorPane.getPrefWidth(), anchorPane.getPrefHeight());
        stackPane.setMinSize(anchorPane.getPrefWidth(),anchorPane.getPrefHeight());
        stackPane.setPrefSize(anchorPane.getPrefWidth(),anchorPane.getPrefHeight());
        setVisible(anchorPane);
    }

    private void setVisible(AnchorPane anchorPane){
        setAllInvisible();
        anchorPane.setVisible(true);
    }
    private void setAllInvisible(){
      helpPane.setVisible(false);
      copyOrderPane.setVisible(false);
      paymentPane.setVisible(false);
      confirmationPane.setVisible(false);
    }
}
