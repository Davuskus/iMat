package imat.ui.views.browse.centerviews.products.subcategoryPane;

import imat.model.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class SubcategoryController extends FXMLController {

    @FXML
    private Label subcategoryLabel;

    @FXML
    private FlowPane productsFlowPane;

    private String subcategoryName;

    private List<Node> productList;

    public SubcategoryController(String subcategoryName, List<Node> productList) {
        this.subcategoryName = subcategoryName;
        this.productList = productList;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.subcategoryLabel.setText(subcategoryName);
        populateWithProducts(productList);
    }

    private void populateWithProducts(List<Node> procuctList) {
        for (Node product : procuctList) {
            productsFlowPane.getChildren().add(product);
            /*
            ProductMenuItem controller = new ProductMenuItem(product);
            controller.setModel(model);
            String fxmlPath = "../../../../controls/product/menu/product_menu_item.fxml";
            Node item = FXMLLoader.loadFXMLNodeFromRootPackage(fxmlPath, this, controller);
            productsFlowPane.getChildren().add(item);
            */
        }
    }

}
