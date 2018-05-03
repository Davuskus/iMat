package imat;

import imat.interfaces.ShoppingListener;
import se.chalmers.cse.dat216.project.ProductCategory;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.util.ArrayList;
import java.util.List;

public class Model {

    ProductCategory selectedCategory;

    private final List<ShoppingListener> shoppingListeners;

    public Model() {
        shoppingListeners = new ArrayList<>(1);
    }

    public void addToCart(ShoppingItem shoppingItem) {
        for (ShoppingListener shoppingListener : shoppingListeners) {
            shoppingListener.onAddShoppingItem(shoppingItem);
        }
    }

    public void addShoppingListener(ShoppingListener shoppingListener) {
        shoppingListeners.add(shoppingListener);
    }

    public void selectCategory(ProductCategory category) {
        selectedCategory = category;
        System.out.println("Selected category " + category.name());
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
