package imat.ui.controls.category.categorysidebar;

import imat.model.FXMLController;
import imat.ui.controls.category.categorybutton.CategoryButton;
import imat.utils.FXMLLoader;
import imat.utils.IMatUtils;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.net.URL;
import java.util.ResourceBundle;

public class CategorySidebar extends FXMLController{

    @FXML
    private VBox categoryButtonsVBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model.verifyExistence();
        for(ProductCategory category: IMatUtils.getCategories()){
            CategoryButton btnController = new CategoryButton(category);
            btnController.setModel(model);
            Node btn = FXMLLoader.loadFXMLNodeFromRootPackage("../categorybutton/category_button.fxml",this, btnController);
            categoryButtonsVBox.getChildren().add(btn);
        }
    }
}

