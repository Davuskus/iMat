package imat.ui.views.browse.sidebars.category;

import imat.model.FXMLController;
import imat.model.category.Category;
import imat.ui.controls.category.CategoryButton;
import imat.utils.FXMLLoader;
import imat.utils.IMatUtils;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.net.URL;
import java.util.ResourceBundle;

public class CategorySidebar extends FXMLController {

    @FXML
    private VBox categoryButtonsVBox;

    /*@Override
    public void initialize(URL location, ResourceBundle resources) {
        model.verifyExistence();
        for (ProductCategory category : IMatUtils.getCategories()) {
            CategoryButton btnController = new CategoryButton(category);
            btnController.setModel(model);
            Node btn = FXMLLoader.loadFXMLNodeFromRootPackage(
                    "../../../../controls/category/category_button.fxml", this, btnController);
            categoryButtonsVBox.getChildren().add(btn);
        }
    }*/

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model.verifyExistence();
        for (Category category : model.getCategories()) {
            CategoryButton btnController = new CategoryButton(category);
            btnController.setModel(model);
            Node btn = FXMLLoader.loadFXMLNodeFromRootPackage(
                    "../../../../controls/category/category_button.fxml", this, btnController);
            categoryButtonsVBox.getChildren().add(btn);
        }
    }
}

