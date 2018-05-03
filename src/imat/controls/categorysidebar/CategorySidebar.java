package imat.controls.categorysidebar;

import imat.FXMLController;
import imat.Model;
import imat.controls.categorybutton.CategoryButton;
import imat.interfaces.IFXMLController;
import imat.utils.FXMLLoader;
import imat.utils.IMatUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static imat.utils.IMatUtils.getCategories;

public class CategorySidebar extends FXMLController{

    @FXML
    private VBox categoryButtonsVBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model.verifyExistance();
        for(ProductCategory category: IMatUtils.getCategories()){
            CategoryButton btnController = new CategoryButton(category);
            btnController.setModel(model);
            Node btn = FXMLLoader.loadFXMLNodeFromRootPackage("../categorybutton/category_button.fxml",this, btnController);
            categoryButtonsVBox.getChildren().add(btn);
        }
    }
}

