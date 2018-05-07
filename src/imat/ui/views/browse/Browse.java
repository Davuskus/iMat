package imat.ui.views.browse;


import imat.interfaces.IProducDetailstListener;
import imat.model.FXMLController;
import imat.model.Model;
import imat.enums.NavigationTarget;
import imat.interfaces.INavigationListener;
import imat.ui.views.browse.productdetails.ProductDetailsController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.ResourceBundle;

public class Browse extends FXMLController implements INavigationListener, IProducDetailstListener {

    @FXML private AnchorPane historyPane;
    @FXML private AnchorPane productPane;
    @FXML private AnchorPane productDetailsPane;
    @FXML private ProductDetailsController productDetailsPaneController;

    private Model model;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model.addNavigationListener(this);
        model.addProductDetailsListener(this);
        productPane.toFront();
    }

    @Override
    public void setModel(Model m) {
        this.model = m;
    }

    @FXML
    public void consumeEvent(Event event) {
        event.consume();
    }

    @Override
    public void navigateTo(NavigationTarget navigationTarget) {
        switch (navigationTarget) {
            case HISTORY:
                historyPane.toFront(); break;
            case PRODUCT_DETAILS:
                productDetailsPane.toFront(); break;
            default:
                productPane.toFront(); break;
        }
    }

    @Override
    public void onProductSelection(Product product) {
        productDetailsPaneController.setProductInfo(product);
        navigateTo(NavigationTarget.PRODUCT_DETAILS);
    }
}
