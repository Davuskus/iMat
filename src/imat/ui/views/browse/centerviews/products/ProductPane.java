package imat.ui.views.browse.centerviews.products;

import imat.interfaces.IEcologicalListener;
import imat.model.FXMLController;
import imat.ui.controls.product.menu.ProductMenuItem;
import imat.utils.FXMLLoader;
import imat.utils.ProductNodeFactory;
import imat.utils.ProductPaneWorker;
import javafx.scene.Node;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public abstract class ProductPane extends FXMLController implements IEcologicalListener {

    protected boolean isOnlyShowingEcoProducts;

    protected List<Product> currentProducts;

    protected ConcurrentHashMap<Product, Node> productMenuItems;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model.addEcologicalListener(this);
        productMenuItems = new ConcurrentHashMap<>();

        long startTime = System.currentTimeMillis();
        populateParallelStream(model.getAllProducts()); 
        //populateMultiThreaded(model.getAllProducts());
        //populateSingleThreaded(model.getAllProducts());
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Generated all nodes in: " + elapsedTime + " milliseconds.");
    }

    private void populateSingleThreaded(List<Product> productList) {
        for (Product product : productList) {
            productMenuItems.put(product, ProductNodeFactory.makeNodeFromProduct(product, this, model));
        }
    }

    private void populateParallelStream(List<Product> productList) {
        productList.parallelStream().forEach(product -> productMenuItems.put(product, ProductNodeFactory.makeNodeFromProduct(product, this, model)));
    }

    private void populateMultiThreaded(List<Product> productList) {
        int numThreads = Runtime.getRuntime().availableProcessors();
        int remainder = productList.size() % numThreads;

        List<Thread> threads = new ArrayList<>();

        // Distribute products and create workers
        for (int i = 0; i < numThreads; i++) {
            List<Product> productShare = productList.subList(i * (productList.size() / numThreads), (i + 1) * (productList.size() / numThreads));

            if (remainder > 0 && i  < remainder) {
                productShare.add(productList.get(productList.size() - remainder + i));
            }

            Thread thread = new Thread(new ProductPaneWorker(productShare, productMenuItems, model, this));
            thread.start();
            threads.add(thread);
        }

        // Wait for workers to finish
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onEcologicalUpdate(boolean isEcological) {
        isOnlyShowingEcoProducts = isEcological;
    }

}
