package imat.ui.views.modal;

import imat.interfaces.IProducDetailstListener;
import imat.model.FXMLController;
import imat.enums.NavigationTarget;
import imat.interfaces.INavigationListener;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.ResourceBundle;

public class Modal extends FXMLController implements INavigationListener, IProducDetailstListener {

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

    @Override
    public void navigateTo(NavigationTarget navigationTarget) {
        switch (navigationTarget) {
            case HELP:
                modalTitleLabel.setText("Hjälp");
                helpPane.toFront();
                break;
            case COPY_ORDER:
                modalTitleLabel.setText("Kopiera order");
                copyOrderPane.toFront();
                break;
            case PAYMENT:
                modalTitleLabel.setText("Betalning");
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
        //model.addProductDetailsListener(this);
    }

    @Override
    public void onProductSelection(Product product) {
        //productDetailsPaneController.setProductInfo(product);
        //navigateTo(NavigationTarget.PRODUCT_DETAILS);
    }
}
