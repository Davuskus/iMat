package imat.ui.controls.product.feature;

import imat.enums.NavigationTarget;
import imat.model.FXMLController;
import javafx.fxml.FXML;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.*;

public class Feature extends FXMLController {

    private final List<Product> products;

    private final int numProducts;

    private final long scrollIntervalMillis;

    private int currentProductIndex;

    private boolean featureScrolling;

    public Feature() {
        super();
        numProducts = 4;
        scrollIntervalMillis = 10000;
        products = new ArrayList<>(numProducts);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getRandomProductsToFeature();
    }

    private void getRandomProductsToFeature() {
        Random random = new Random();
        List<Product> backendProducts = IMatDataHandler.getInstance().getProducts();
        while (products.size() < numProducts) {
            Product product = IMatDataHandler.getInstance().getProduct(random.nextInt(backendProducts.size()));
            if (!products.contains(product)) {
                products.add(product);
            }
        }
    }

    private void startFeatureScrolling() {
        featureScrolling = true;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // TODO Switch between the feature products.
                if (!featureScrolling) {
                    timer.cancel();
                }
            }
        }, scrollIntervalMillis);
    }

    @FXML
    public void showProductDetails() {
        model.showProductDetails(products.get(currentProductIndex));
        model.navigate(NavigationTarget.PRODUCT_DETAILS);
    }

    public void setFeatureScrolling(boolean featureScrolling) {
        if (featureScrolling) {
            startFeatureScrolling();
        } else {
            this.featureScrolling = false;
        }
    }

}
