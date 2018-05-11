package imat.ui;

import imat.enums.NavigationTarget;
import imat.interfaces.INavigationListener;
import imat.model.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends FXMLController implements INavigationListener {

    @FXML
    private AnchorPane modalView;

    @FXML
    private AnchorPane browseView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model.addNavigationListener(this);
        model.navigate(NavigationTarget.HOME);
    }

    @Override
    public void navigateTo(NavigationTarget navigationTarget) {
        switch (navigationTarget) {
            case HELP:
            case COPY_ORDER:
            case CONFIRMATION:
            case PAYMENT:
                modalView.toFront();
                break;
            case HOME:
            case ORDER_HISTORY:
            case CHECKOUT:
                browseView.toFront();
            default:
                browseView.toFront();
                break;
        }
    }
}
