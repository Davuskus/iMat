package imat.ui.views.browse.centerviews.products;

import imat.interfaces.ICategoryListener;
import imat.interfaces.ISearchListener;
import imat.model.FXMLController;
import imat.model.category.Category;
import imat.ui.controls.product.menu.ProductMenuItem;
import imat.ui.views.browse.centerviews.products.subcategoryPane.SubcategoryController;
import imat.utils.FXMLLoader;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.*;

public class Products extends FXMLController implements ICategoryListener, ISearchListener {

    @FXML
    private Label categoryLabel;

    @FXML
    private FlowPane productsFlowPane;

    @FXML
    private CheckBox onlyEcoCheckBox;

    @FXML
    private Label noResultsLabel;

    private Category currentCategory;

    private boolean onlyEcologicalProducts;

    private List<Product> currentProducts;

    private Map<Product, Node> productMenuItems;

    private List<Node> subcategories;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model.addCategoryListener(this);
        model.addSearchListener(this);

        productMenuItems = new HashMap<>();
        for (Product product : model.getAllProducts()) {
            ProductMenuItem controller = new ProductMenuItem(product);
            controller.setModel(model);
            String fxmlPath = "../../../../controls/product/menu/product_menu_item.fxml";
            Node item = FXMLLoader.loadFXMLNodeFromRootPackage(fxmlPath, this, controller);
            productMenuItems.put(product, item);
        }
    }

    @Override
    public void onCategorySelected(Category category) {
        if (category == currentCategory) return;
        currentCategory = category;
        productsFlowPane.getChildren().clear();
        categoryLabel.setText(category.getName());
        populateWithCategorizedProducts(category, onlyEcologicalProducts);
    }

    @Override
    public void onSearch(String searchTerm, List<Product> products) {
        currentCategory = null;
        String categoryText;
        if (products.size() == 0) {
            categoryText = "Din sökning på \"" + searchTerm + "\" gav inga träffar";
        } else {
            categoryText = "Sökresultat för: \"" + searchTerm + "\"";
        }
        categoryLabel.setText(categoryText);
        productsFlowPane.getChildren().clear();
        populateWithProducts(products, onlyEcologicalProducts);
    }

    private void populateWithProducts(List<Product> products, boolean onlyEcologicalProducts) {
        currentProducts = products;
        int numShownProducts = 0;
        for (Product product : products) {
            if (!onlyEcologicalProducts || product.isEcological()) {
                numShownProducts++;
                productsFlowPane.getChildren().add(productMenuItems.get(product));
            }
        }
        noResultsLabel.setVisible(numShownProducts == 0);
    }

    private void populateWithCategorizedProducts(Category category, boolean onlyEcologicalProducts) {
        currentCategory = category;
        noResultsLabel.setVisible(category.getAllProducts().stream().noneMatch(x -> (x.isEcological() || !onlyEcologicalProducts)));
        List<String> subcategories = category.getSubcategories();
        for (String subcategory : subcategories) {
            List<Product> productList = category.getProductsFromSubcategory(subcategory);
            List<Node> subcategoryProducts = new ArrayList<>();
            for (Product product : productList) {
                if (!onlyEcologicalProducts || product.isEcological()) {
                    subcategoryProducts.add(productMenuItems.get(product));
                }
            }

            if (subcategoryProducts.size() > 0) {
                SubcategoryController controller = new SubcategoryController(subcategory, subcategoryProducts);
                controller.setModel(model);
                String fxmlPath = "../../../../views/browse/centerviews/products/subcategoryPane/SubcategoryPane.fxml";
                Node item = FXMLLoader.loadFXMLNodeFromRootPackage(fxmlPath, this, controller);
                productsFlowPane.getChildren().add(item);
            }
        }
    }

    @FXML
    private void checkBoxOnAction(Event event) {
        onlyEcologicalProducts = onlyEcoCheckBox.isSelected();
        productsFlowPane.getChildren().clear();
        if (currentCategory == null) {
            populateWithProducts(currentProducts, onlyEcologicalProducts);
        } else {
            populateWithCategorizedProducts(currentCategory, onlyEcologicalProducts);
        }
    }

}
