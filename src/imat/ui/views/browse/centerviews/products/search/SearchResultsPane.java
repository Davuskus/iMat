package imat.ui.views.browse.centerviews.products.search;

import imat.enums.NavigationTarget;
import imat.interfaces.INavigationListener;
import imat.interfaces.ISearchListener;
import imat.ui.views.browse.centerviews.products.ProductPane;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SearchResultsPane extends ProductPane implements ISearchListener, INavigationListener {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label titleLabel;

    @FXML
    private FlowPane productsFlowPane;

    @FXML
    private CheckBox onlyEcoCheckBox;

    @FXML
    private Label noResultsLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        model.addSearchListener(this);
        model.addNavigationListener(this);
    }

    @Override
    public void onSearch(String searchTerm, List<Product> products) {
        String titleText;
        if (products.size() == 0) {
            titleText = "Din sökning på \"" + searchTerm + "\" gav inga träffar";
        } else {
            titleText = "Sökresultat för: \"" + searchTerm + "\"";
        }
        titleLabel.setText(titleText);
        productsFlowPane.getChildren().clear();
        populateWithProducts(products, isOnlyShowingEcoProducts);

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

    @FXML
    private void checkBoxOnAction(Event event) {
        model.notifyEcologicalListeners(onlyEcoCheckBox.isSelected());
        updateProductList();
    }

    private void updateProductList() {
        productsFlowPane.getChildren().clear();
        populateWithProducts(currentProducts, isOnlyShowingEcoProducts);
    }

    @Override
    public void navigateTo(NavigationTarget navigationTarget) {
        if (navigationTarget == NavigationTarget.SEARCH_RESULTS) {
            rootPane.setDisable(false);
            updateProductList();
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
