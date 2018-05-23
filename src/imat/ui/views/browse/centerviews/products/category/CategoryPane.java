package imat.ui.views.browse.centerviews.products.category;

import imat.enums.NavigationTarget;
import imat.interfaces.ICategoryListener;
import imat.interfaces.INavigationListener;
import imat.model.category.Category;
import imat.ui.views.browse.centerviews.products.ProductPane;
import imat.ui.views.browse.centerviews.products.category.subcategoryPane.SubcategoryController;
import imat.utils.FXMLLoader;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CategoryPane extends ProductPane implements ICategoryListener, INavigationListener {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label categoryLabel;

    @FXML
    private VBox productsVBox;

    @FXML
    private CheckBox onlyEcoCheckBox;

    @FXML
    private Label noResultsLabel;

    private Category currentCategory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        model.addCategoryListener(this);
        model.addNavigationListener(this);
    }

    @Override
    public void onCategorySelected(Category category) {
        if ((category == null) || (category == currentCategory)) return;
        currentCategory = category;
        updatePaneContent(currentCategory);
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
                String fxmlPath = "subcategoryPane/subcategory_pane.fxml";
                Node item = FXMLLoader.loadFXMLNodeFromRootPackage(fxmlPath, this, controller);
                productsVBox.getChildren().add(item);
            }
        }
    }

    @FXML
    private void checkBoxOnAction(Event event) {
        model.notifyEcologicalListeners(onlyEcoCheckBox.isSelected());
        updatePaneContent(currentCategory);
    }

    private void updatePaneContent(Category category) {
        categoryLabel.setText(category.getName());
        productsVBox.getChildren().clear();
        populateWithCategorizedProducts(currentCategory, isOnlyShowingEcoProducts);
    }

    @Override
    public void navigateTo(NavigationTarget navigationTarget) {
        if (navigationTarget == NavigationTarget.CATEGORY) {
            rootPane.setDisable(false);
            updatePaneContent(currentCategory);
            model.selectCategory(currentCategory);
        } else {
            rootPane.setDisable(true);
        }
    }

    @Override
    public void onEcologicalUpdate(boolean isEcological) {
        super.onEcologicalUpdate(isEcological);
        onlyEcoCheckBox.setSelected(isEcological);
    }

}
