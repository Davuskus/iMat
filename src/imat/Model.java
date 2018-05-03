package imat;

import imat.interfaces.ICategoryListener;
import imat.interfaces.ShoppingListener;
import se.chalmers.cse.dat216.project.ProductCategory;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.util.ArrayList;
import java.util.List;

public class Model {

    ProductCategory selectedCategory;

    private final List<ShoppingListener> shoppingListeners;
    private final List<ICategoryListener> categoryListeners;

    public Model() {
        shoppingListeners = new ArrayList<>(1);
        categoryListeners = new ArrayList<>(1);
    }

    public void addToCart(ShoppingItem shoppingItem) {
        for (ShoppingListener shoppingListener : shoppingListeners) {
            shoppingListener.onAddShoppingItem(shoppingItem);
        }
    }

    public void addShoppingListener(ShoppingListener shoppingListener) {
        shoppingListeners.add(shoppingListener);
    }
    public void addCategoryListener(ICategoryListener categoryListener) { categoryListeners.add(categoryListener); }

    public void selectCategory(ProductCategory category) {
        selectedCategory = category;
        System.out.println("Selected category " + category.name());
        categoryListeners.forEach(x->x.onCategorySelected(category));
    }

    public void addCategoryListener() {

    }

    public void verifyExistence() {
        System.out.println("Model exists!");
    }

    public void openCheckoutView() {
        System.out.println("Checkout");
    }

}
