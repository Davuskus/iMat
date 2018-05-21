package imat.ui.views.browse.centerviews.products;

import imat.model.FXMLController;
import imat.ui.controls.product.menu.ProductMenuItem;
import imat.utils.FXMLLoader;
import javafx.scene.Node;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public abstract class ProductPane extends FXMLController {

    protected List<Product> currentProducts;

    protected Map<Product, Node> productMenuItems;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productMenuItems = new HashMap<>();
        for (Product product : model.getAllProducts()) {
            ProductMenuItem controller = new ProductMenuItem(product);
            controller.setModel(model);
            String fxmlPath = "../../../../../controls/product/menu/product_menu_item.fxml";
            Node item = FXMLLoader.loadFXMLNodeFromRootPackage(fxmlPath, this, controller);
            productMenuItems.put(product, item);
        }
    }

}
