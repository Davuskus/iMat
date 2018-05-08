package imat.ui.views.browse.home;

import imat.enums.NavigationTarget;
import imat.interfaces.INavigationListener;
import imat.model.FXMLController;
import imat.ui.controls.product.feature.Feature;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Home extends FXMLController implements INavigationListener {

    @FXML
    private AnchorPane feature;

    @FXML
    private Feature featureController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model.addNavigationListener(this);
    }

    @Override
    public void navigateTo(NavigationTarget navigationTarget) {
        featureController.setFeatureScrolling(navigationTarget == NavigationTarget.HOME);
    }

}
