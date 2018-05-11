package imat.ui.controls.product.feature;

import imat.interfaces.IShutdownListener;
import imat.model.FXMLController;
import imat.ui.controls.product.feature.item.FeatureItem;
import imat.utils.FXMLLoader;
import imat.utils.IMatUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Feature extends FXMLController implements IShutdownListener {

    private final List<Product> products;

    private final int numProducts;

    private final long scrollIntervalMillis;

    private int currentProductIndex;

    private boolean featureScrolling;

    @FXML
    private StackPane productStackPane;

    public Feature() {
        super();
        numProducts = 4;
        scrollIntervalMillis = 30000;
        products = new ArrayList<>(numProducts);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model.addShutdownListener(this);
        setRandomProductsToFeature(numProducts);
    }

    private ScheduledThreadPoolExecutor scheduledThreadExecutor;
    private ScheduledFuture<?> scheduledFuture;

    private void startFeatureScrolling() {
        if (!featureScrolling) {
            featureScrolling = true;
            scheduledThreadExecutor = new ScheduledThreadPoolExecutor(1);
            scheduledFuture = scheduledThreadExecutor.scheduleAtFixedRate(() -> {
                if (!featureScrolling) {
                    scheduledFuture.cancel(true);
                }
                Platform.runLater(() -> {
                    if (++currentProductIndex >= numProducts) {
                        currentProductIndex = 0;
                    }
                    productStackPane.getChildren().get(currentProductIndex).toFront();
                });
            }, scrollIntervalMillis, scrollIntervalMillis, TimeUnit.MILLISECONDS);
        }
    }

    private void setRandomProductsToFeature(int numProducts) {
        while (productStackPane.getChildren().size() < numProducts) {
            FeatureItem featureItem = new FeatureItem(IMatUtils.getRandomProduct());
            featureItem.setModel(model);
            Node featureItemNode = FXMLLoader.loadFXMLNodeFromRootPackage(
                    "item/feature_item.fxml",
                    this,
                    featureItem);
            productStackPane.getChildren().add(featureItemNode);
        }
    }

    public void setFeatureScrolling(boolean featureScrolling) {
        if (featureScrolling) {
            startFeatureScrolling();
        } else {
            this.featureScrolling = false;
        }
    }

    @Override
    public void onShutdown() {
        featureScrolling = false;
        scheduledThreadExecutor.shutdownNow();
    }

}
