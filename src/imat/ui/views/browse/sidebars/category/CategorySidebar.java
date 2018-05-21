package imat.ui.views.browse.sidebars.category;

import imat.enums.NavigationTarget;
import imat.interfaces.ICategoryListener;
import imat.interfaces.INavigationListener;
import imat.model.FXMLController;
import imat.model.category.Category;
import imat.ui.controls.category.CategoryButton;
import imat.utils.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CategorySidebar extends FXMLController implements ICategoryListener, INavigationListener {

    @FXML
    private VBox categoryButtonsVBox;

    private final List<CategoryButton> categoryButtons;

    public CategorySidebar() {
        categoryButtons = new ArrayList<>(14);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model.addCategoryListener(this);
        model.addNavigationListener(this);
        for (Category category : model.getCategories()) {
            CategoryButton btnController = new CategoryButton(category);
            btnController.setModel(model);
            Node btn = FXMLLoader.loadFXMLNodeFromRootPackage(
                    "../../../../controls/category/category_button.fxml", this, btnController);
            categoryButtonsVBox.getChildren().add(btn);
            categoryButtons.add(btnController);
        }
    }

    @Override
    public void onCategorySelected(Category category) {
        categoryButtons.forEach(button -> button.setSelected(false));
        for (CategoryButton button : categoryButtons) {
            if (button.getCategory().equals(category)) {
                button.setSelected(true);
                break;
            }
        }
    }

    private void deselectAllCategoryButtons() {
        categoryButtons.forEach(categoryButton -> categoryButton.setSelected(false));
    }

    @Override
    public void navigateTo(NavigationTarget navigationTarget) {
        if (navigationTarget != NavigationTarget.CATEGORY) {
            deselectAllCategoryButtons();
        }
    }
}

