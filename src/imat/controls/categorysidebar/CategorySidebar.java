package imat.controls.categorysidebar;

import imat.utils.FXMLLoader;
import javafx.scene.layout.VBox;

public class CategorySidebar extends VBox{
    public CategorySidebar(){
        FXMLLoader.loadFXMLFromRootPackage("category_sidebar.fxml",this, this);
    }
}

