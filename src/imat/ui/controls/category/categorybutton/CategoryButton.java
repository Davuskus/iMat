package imat.ui.controls.category.categorybutton;

import imat.model.FXMLController;
import imat.enums.NavigationTarget;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.net.URL;
import java.util.ResourceBundle;

public class CategoryButton extends FXMLController {

    @FXML private Button button;

    private final ProductCategory category;

    public CategoryButton(ProductCategory category){
        this.category = category;
    }

    @FXML private void onButtonClick(){
        model.selectCategory(category);
        model.navigate(NavigationTarget.CATEGORY);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        button.setText(category.name());
    }
}
