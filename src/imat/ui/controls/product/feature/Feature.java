package imat.ui.controls.product.feature;

import imat.enums.NavigationTarget;
import imat.model.FXMLController;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.*;

public class Feature extends FXMLController {

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

    }

    private void startFeatureScrolling() {
        featureScrolling = true;

//        Timer timer = new Timer();
//        timer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                Platform.runLater(() -> {
//                    if (++currentProductIndex >= numProducts) {
//                        currentProductIndex = 0;
//                    }
//                    productStackPane.getChildren().get(currentProductIndex).toFront();
//                });
//                if (!featureScrolling) {
//                    timer.cancel();
//                }
//            }
//        }, 0, scrollIntervalMillis);
    }

    public void setFeatureScrolling(boolean featureScrolling) {
        if (featureScrolling) {
            startFeatureScrolling();
        } else {
            this.featureScrolling = false;
        }
    }

}
