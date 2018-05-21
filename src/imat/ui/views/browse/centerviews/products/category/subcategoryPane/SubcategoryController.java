package imat.ui.views.browse.centerviews.products.category.subcategoryPane;

import imat.model.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SubcategoryController extends FXMLController {

    @FXML
    private Label subcategoryLabel;

    @FXML
    private VBox paneVBox;

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

    private void populateWithProducts(List<Node> productList) {
        paneVBox.setVisible(!productList.isEmpty());
        productsFlowPane.getChildren().addAll(productList);
    }

}
