package imat.utils;

import imat.model.Model;
import imat.ui.controls.product.menu.ProductMenuItem;
import imat.ui.views.browse.centerviews.products.ProductPane;
import javafx.scene.Node;
import se.chalmers.cse.dat216.project.Product;

public class ProductNodeFactory {

    public static Node makeNodeFromProduct(Product product, ProductPane root, Model model) {
        ProductMenuItem controller = new ProductMenuItem(product);
        controller.setModel(model);
        String fxmlPath = "../../../../../controls/product/menu/product_menu_item.fxml";
        return FXMLLoader.loadFXMLNodeFromRootPackage(fxmlPath, root, controller);
    }
}
