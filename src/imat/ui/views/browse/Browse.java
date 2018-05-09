package imat.ui.views.browse;


import imat.enums.NavigationTarget;
import imat.interfaces.INavigationListener;
import imat.interfaces.IProducDetailstListener;
import imat.model.FXMLController;
import imat.model.Model;
import imat.ui.views.browse.productdetails.ProductDetailsController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.ResourceBundle;

public class Browse extends FXMLController implements INavigationListener, IProducDetailstListener {

    @FXML
    private GridPane browseGridPane;

    @FXML
    private AnchorPane checkoutPane;

    @FXML
    private AnchorPane historyPane;

    @FXML
    private AnchorPane historyArticlesPane;

    @FXML
    private AnchorPane productPane;

    @FXML
    private AnchorPane productDetailsPane;

    @FXML
    private AnchorPane homePane;

    @FXML
    private ProductDetailsController productDetailsPaneController;

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
            case ORDER_HISTORY:
                browseGridPane.toFront();
                historyPane.toFront();
                break;
            case ORDER_HISTORY_ARTICLE:
                browseGridPane.toFront();
                historyArticlesPane.toFront();
                break;
            case PRODUCT_DETAILS:
                browseGridPane.toFront();
                productDetailsPane.toFront();
                break;
            case CATEGORY:
                browseGridPane.toFront();
                productPane.toFront();
                break;
            case CHECKOUT:
                checkoutPane.toFront();
                break;
            case HOME:
            default:
                browseGridPane.toFront();
                homePane.toFront();
                break;
        }
    }

    @Override
    public void onProductSelection(Product product) {
        productDetailsPaneController.setProductInfo(product);
        navigateTo(NavigationTarget.PRODUCT_DETAILS);
    }
}
