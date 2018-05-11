package imat.ui.controls.category;

import imat.model.FXMLController;
import imat.enums.NavigationTarget;
import imat.model.category.Category;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.net.URL;
import java.util.ResourceBundle;

public class CategoryButton extends FXMLController {

    @FXML private Button button;

    //private final ProductCategory category;

    private final Category category;

    /*
    public CategoryButton(ProductCategory category){
        this.category = category;
        productCategory = null;
    }
    */

    public CategoryButton(Category productCategory) {
        this.category = productCategory;
    }

    @FXML private void onButtonClick(){
        model.selectCategory(category);
        model.navigate(NavigationTarget.CATEGORY);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        button.setText(category.getName());
    }
}
