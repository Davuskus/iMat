package imat.utils;

import imat.model.Model;
import imat.ui.controls.product.menu.ProductMenuItem;
import imat.ui.views.browse.centerviews.products.ProductPane;
import javafx.scene.Node;
import se.chalmers.cse.dat216.project.Product;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProductPaneWorker implements Runnable {

    private List<Product> products;
    private ConcurrentHashMap<Product, Node> productNodes;
    private Model model;
    private ProductPane root;

    public ProductPaneWorker(List<Product> products, ConcurrentHashMap<Product, Node> productNodes, Model model, ProductPane root) {
        this.products = products;
        this.productNodes = productNodes;
        this.model = model;
        this.root = root;
    }

    @Override
    public void run() {
        for (Product product : products) {
            productNodes.put(product, ProductNodeFactory.makeNodeFromProduct(product, root, model));
        }
    }
}
