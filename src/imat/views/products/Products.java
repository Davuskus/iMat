package imat.views.products;

import imat.FXMLController;
import imat.controls.product.menuitem.ProductMenuItem;
import imat.interfaces.ICategoryListener;
import imat.utils.FXMLLoader;
import imat.utils.IMatUtils;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.net.URL;
import java.util.ResourceBundle;

public class Products extends FXMLController implements ICategoryListener {

    @FXML private Label categoryLabel;
    @FXML private FlowPane productsFlowPane;
    private ProductCategory currentCategory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model.addCategoryListener(this);
    }

    @Override
    public void onCategorySelected(ProductCategory category) {
        if(category == currentCategory) return;
        currentCategory = category;
        productsFlowPane.getChildren().removeIf(x->true);
        categoryLabel.setText(category.name());
        for(Product product: IMatDataHandler.getInstance().getProducts(category)) {
            ProductMenuItem controller = new ProductMenuItem(product);
            controller.setModel(model);
            Node item = FXMLLoader.loadFXMLNodeFromRootPackage("../../controls/product/menuitem/product_menu_item.fxml",this, controller);
            productsFlowPane.getChildren().add(item);
        }
    }
}