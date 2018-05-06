package imat.model;

import imat.enums.NavigationTarget;
import imat.interfaces.*;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.util.*;

public class Model {

    ProductCategory selectedCategory;

    private Map<Product, Double> cart = new HashMap<>();

    private final List<IShoppingListener> IShoppingListeners;
    private final List<ICategoryListener> categoryListeners;
    private final List<INavigationListener> navigationListeners;
    private final List<ISearchListener> searchListeners;
    private final List<IProducDetailstListener> productListeners;

    private boolean isThrowingCartInTrash;

    public Model() {
        IShoppingListeners = new ArrayList<>(1);
        categoryListeners = new ArrayList<>(1);
        navigationListeners = new ArrayList<>(1);
        searchListeners = new ArrayList<>(1);
        productListeners = new ArrayList<>(1);
    }

    public void navigate(NavigationTarget navigationTarget) {
        navigationListeners.forEach(x -> x.navigateTo(navigationTarget));
    }

    public void addShoppingListener(IShoppingListener IShoppingListener) {
        IShoppingListeners.add(IShoppingListener);
    }

    public void addProductDetailsListener(IProducDetailstListener productDetailsListener) {
        productListeners.add(productDetailsListener);
    }

    public void updateShoppingCart(Product product, double newAmount) {
        boolean existed = cart.containsKey(product);
        double oldAmount = existed ? cart.get(product) : 0.0;
        if (newAmount <= 0) {
            if (!cart.containsKey(product)) return;
            for (int i = 0; i < IShoppingListeners.size(); i++) {
                IShoppingListeners.get(i).onProductUpdate(product, newAmount);
            }
            cart.remove(product);
            for (int i = 0; i < IShoppingListeners.size(); i++) {
                IShoppingListeners.get(i).onProductRemoved(product, oldAmount);
            }
        } else if (!isThrowingCartInTrash) {
            cart.put(product, newAmount);
            if (!existed) {
                for (int i = 0; i < IShoppingListeners.size(); i++) {
                    IShoppingListeners.get(i).onProductAdded(product, newAmount);
                }
            }
            for (int i = 0; i < IShoppingListeners.size(); i++) {
                IShoppingListeners.get(i).onProductUpdate(product, newAmount);
            }
        }
    }

    public void removeProductFromShoppingCart(Product product) {
        updateShoppingCart(product, 0);
    }

    public double getProductAmount(Product product) {
        return cart.getOrDefault(product, 0.0);
    }

    public void addToShoppingCart(Product product, double amount) {
        updateShoppingCart(product, cart.getOrDefault(product, 0.0) + amount);
    }

    public void addCategoryListener(ICategoryListener categoryListener) {
        categoryListeners.add(categoryListener);
    }

    public void addSearchListener(ISearchListener searchListener) {
        searchListeners.add(searchListener);
    }

    public double getCartPrice() {
        return cart.entrySet().stream().mapToDouble(e -> e.getKey().getPrice() * e.getValue()).sum();
    }

    public Set<Product> getProductsInCart() {
        return cart.keySet();
    }

    public void saveShoppingCart() {
        cart.keySet().forEach(
                product -> IMatDataHandler.getInstance().getShoppingCart().addProduct(product, cart.get(product)));
    }

    public void clearBackendShoppingCart() {
        IMatDataHandler.getInstance().getShoppingCart().clear();
    }

    public void clearCart() {
        while (0 < cart.size()) {
            updateShoppingCart(cart.keySet().iterator().next(), 0);
        }
    }

    public void selectCategory(ProductCategory category) {
        selectedCategory = category;
        categoryListeners.forEach(x -> x.onCategorySelected(category));
    }

    public void search(String searchTerm) {
        if (searchTerm.length() == 0) return;

        List<Product> products = new ArrayList<>();

        for (Product product : IMatDataHandler.getInstance().getProducts()) {
            if (product.getName().toLowerCase().contains(searchTerm.toLowerCase())) {
                products.add(product);
            }
        }

        searchListeners.forEach(x -> x.onSearch(searchTerm, products));

    }

    public void verifyExistence() {
        System.out.println("Model exists!");
    }

    public void addNavigationListener(INavigationListener navigationListener) {
        navigationListeners.add(navigationListener);
    }

    public void setThrowingCartInTrash(boolean throwingCartInTrash) {
        isThrowingCartInTrash = throwingCartInTrash;
    }

    public boolean isThrowingCartInTrash() {
        return isThrowingCartInTrash;
    }

    public void showProductDetails(Product product) {
        productListeners.forEach(x -> x.onProductSelection(product));
    }
}
