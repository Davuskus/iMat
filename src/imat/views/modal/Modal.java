package imat.views.modal;

import imat.FXMLController;
import imat.enums.NavigationTarget;
import imat.interfaces.INavigationListener;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

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
    private AnchorPane productDetailsPane;

    @FXML
    private AnchorPane paymentPane;

    @FXML
    public void consumeEvent(Event event) {
        event.consume();
    }

    @FXML
    public void closeButtonOnAction(Event event) {
        rootPane.toBack();
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

    @Override
    public void navigateTo(NavigationTarget navigationTarget) {
        switch (navigationTarget) {
            case HELP:
                helpPane.toFront();
                break;
            case PRODUCT_DETAILS:
                productDetailsPane.toFront();
                break;
            case COPY_ORDER:
                copyOrderPane.toFront();
                break;
            case PAYMENT:
                paymentPane.toFront();
                break;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model.addNavigationListener(this);
    }

}
