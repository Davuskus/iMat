package imat.controls.categorysidebar;

import javafx.scene.layout.VBox;

public class CategorySidebar extends VBox{
    public CategorySidebar(){
        imat.util.FXMLLoader.loadFXMLFromRootPackage("category_sidebar.fxml",this, this);
    }
}

