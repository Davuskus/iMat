package imat.controls.categorysidebar;

import imat.utils.FXMLLoader;
import imat.utils.IMatUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ProductCategory;

import static imat.utils.IMatUtils.getCategories;

public class CategorySidebar extends AnchorPane{

    @FXML
    private VBox categoryButtonsVBox;

    public CategorySidebar() {

        FXMLLoader.loadFXMLFromRootPackage("category_sidebar.fxml",this,this);

        for(ProductCategory category: IMatUtils.getCategories()){
            Button btn = new Button(category.name());
            btn.setMaxWidth(Double.MAX_VALUE);
            categoryButtonsVBox.getChildren().add(btn);
        }
    }
}

