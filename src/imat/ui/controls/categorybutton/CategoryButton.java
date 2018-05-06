package imat.ui.controls.categorybutton;

import imat.model.FXMLController;
import imat.enums.NavigationTarget;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.net.URL;
import java.util.ResourceBundle;

public class CategoryButton extends FXMLController {

    @FXML private Button button;

    private final ProductCategory CATEGORY;

    public CategoryButton(ProductCategory category){
        this.CATEGORY = category;
    }

    @FXML private void onButtonClick(){
        model.selectCategory(CATEGORY);
        model.navigate(NavigationTarget.CATEGORY);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        button.setText(CATEGORY.name());
    }
}
