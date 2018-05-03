package imat.controls.categorysidebar;

import imat.Model;
import imat.interfaces.IFXMLController;
import imat.utils.FXMLLoader;
import imat.utils.IMatUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.net.URL;
import java.util.ResourceBundle;

import static imat.utils.IMatUtils.getCategories;

public class CategorySidebar implements IFXMLController, Initializable{

    Model model;

    @FXML
    private VBox categoryButtonsVBox;


    @Override
    public void setModel(Model m) {
        this.model = model;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for(ProductCategory category: IMatUtils.getCategories()){
            Button btn = new Button(category.name());
            btn.setMaxWidth(Double.MAX_VALUE);
            categoryButtonsVBox.getChildren().add(btn);
        }
    }
}

