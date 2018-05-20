package imat.ui.controls.category;

import imat.interfaces.ICategoryListener;
import imat.model.FXMLController;
import imat.enums.NavigationTarget;
import imat.model.category.Category;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class CategoryButton extends FXMLController implements ICategoryListener {

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
        model.navigate(NavigationTarget.PRODUCTS);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        button.setText(category.getName());
        model.addCategoryListener(this);
    }

    public void setSelected(boolean selected) {
        // TODO Make the selection visible somehow
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public void onCategorySelected(Category category) {
        if(category == this.category) {
            button.getStyleClass().add("selected-category");
        } else {
            button.getStyleClass().remove("selected-category");
        }
    }
}
