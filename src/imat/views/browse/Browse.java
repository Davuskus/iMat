package imat.views.browse;


import imat.FXMLController;
import imat.Model;
import imat.enums.NavigationTarget;
import imat.interfaces.IFXMLController;
import imat.interfaces.INavigationListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Browse extends FXMLController implements INavigationListener {

    @FXML private AnchorPane historyPane;
    @FXML private AnchorPane productPane;

    private Model model;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model.addNavigationListener(this);
    }

    @Override
    public void setModel(Model m) {
        this.model = m;
    }

    @Override
    public void navigateTo(NavigationTarget navigationTarget) {
        switch (navigationTarget) {
            case HISTORY:
                historyPane.toFront(); break;
            default:
                productPane.toFront(); break;
        }
    }
}
