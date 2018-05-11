package imat.ui.views.browse.centerviews.home;

import imat.enums.NavigationTarget;
import imat.interfaces.INavigationListener;
import imat.model.FXMLController;
import imat.ui.controls.product.feature.Feature;
import imat.ui.controls.product.menu.ProductMenuItem;
import imat.utils.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class Home extends FXMLController implements INavigationListener {

    @FXML
    private AnchorPane feature;

    @FXML
    private Feature featureController;

    @FXML
    private FlowPane productsFlowPane;

    @FXML
    private Label productsTitle;

    private final int maxNumProducts = 10;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model.addNavigationListener(this);
        updateProductsHBox();
    }

    private void updateProductsHBox() {
        productsFlowPane.getChildren().clear();
        if (IMatDataHandler.getInstance().getOrders().size() > 0) {
            productsTitle.setText("Mest köpta varorna:");
            model.getCommonlyPurchasedProducts(maxNumProducts).forEach(this::addProductMenuItem);
        } else {
            productsTitle.setText("Rekommenderade varor:");
            while (productsFlowPane.getChildren().size() < maxNumProducts) {
                addProductMenuItem(getRandomProduct());
            }
        }
    }

    private void addProductMenuItem(Product product) {
        ProductMenuItem controller = new ProductMenuItem(product);
        controller.setModel(model);
        Node item = FXMLLoader.loadFXMLNodeFromRootPackage(
                "../../../../controls/product/menu/product_menu_item.fxml", this, controller);
        productsFlowPane.getChildren().add(item);
    }

    private int getRandomInteger(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    private Product getRandomProduct() {
        int randomId = getRandomInteger(1, IMatDataHandler.getInstance().getProducts().size());
        return IMatDataHandler.getInstance().getProduct(randomId);
    }

    @Override
    public void navigateTo(NavigationTarget navigationTarget) {
        if (navigationTarget == NavigationTarget.HOME) {
            updateProductsHBox();
            featureController.setFeatureScrolling(navigationTarget == NavigationTarget.HOME);
        }
    }

}
