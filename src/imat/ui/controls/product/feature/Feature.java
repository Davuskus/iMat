package imat.ui.controls.product.feature;

import imat.interfaces.IShutdownListener;
import imat.model.FXMLController;
import imat.ui.controls.product.feature.item.FeatureItem;
import imat.utils.AnimationHandler;
import imat.utils.FXMLLoader;
import imat.utils.IMatUtils;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Feature extends FXMLController implements IShutdownListener {

    private final int numProducts;

    private final long scrollIntervalMillis;

    private boolean featureScrolling;

    @FXML
    private StackPane productStackPane;

    @FXML
    private ProgressBar progressBar;

    private Timeline progressAnimation;

    public Feature() {
        super();
        numProducts = 4;
        scrollIntervalMillis = 30000;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model.addShutdownListener(this);
        setRandomProductsToFeature(numProducts);
        switchFeature();
    }

    private void startFeatureScrolling() {
        if (!featureScrolling) {
            featureScrolling = true;
            Platform.runLater(() -> {
                progressAnimation = AnimationHandler.getAnimation(
                        v -> {
                            if (featureScrolling) {
                                switchFeature();
                                progressBar.setProgress(0);
                                progressAnimation.play();
                            }
                        },
                        AnimationHandler.getAnimationKeyFrame(
                                progressBar.progressProperty(),
                                scrollIntervalMillis,
                                1
                        )
                );
                progressAnimation.play();
            });
        }
    }

    private void switchFeature() {
        productStackPane.getChildren().forEach(child -> child.setDisable(true));
        Node featureItem = productStackPane.getChildren().get(0);
        featureItem.setDisable(false);
        featureItem.toFront();
    }

    private void setRandomProductsToFeature(int numProducts) {
        List<Product> products = new ArrayList<>(numProducts);
        while (productStackPane.getChildren().size() < numProducts) {
            Product product = IMatUtils.getRandomProduct();
            if (product != null && !products.contains(product)) {
                products.add(product);
                FeatureItem featureItem = new FeatureItem(product);
                featureItem.setModel(model);
                Node featureItemNode = FXMLLoader.loadFXMLNodeFromRootPackage(
                        "item/feature_item.fxml",
                        this,
                        featureItem);
                productStackPane.getChildren().add(featureItemNode);
            }
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
        progressAnimation.stop();
    }

}
