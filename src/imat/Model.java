package imat;

import imat.interfaces.ICategoryListener;
import imat.interfaces.INavigationListener;
import imat.interfaces.ISearchListener;
import imat.interfaces.IShoppingListener;
import se.chalmers.cse.dat216.project.*;


import java.util.*;

public class Model {

    ProductCategory selectedCategory;

    private Map<Product, Double> cart = new HashMap<>();

    private final List<IShoppingListener> IShoppingListeners;
    private final List<ICategoryListener> categoryListeners;
    private final List<INavigationListener> navigationListeners;
    private final List<ISearchListener> searchListeners;

    public Model() {
        IShoppingListeners = new ArrayList<>(1);
        categoryListeners = new ArrayList<>(1);
        navigationListeners = new ArrayList<>(1);
        searchListeners = new ArrayList<>(1);
    }

    public void navigate(String destination) {
        navigationListeners.forEach(x->x.navigateTo(destination));
    }

    public void addShoppingListener(IShoppingListener IShoppingListener) {
        IShoppingListeners.add(IShoppingListener);
    }

    public void updateShoppingCart(Product product, double newAmount) {
        boolean existed = cart.containsKey(product);
        double oldAmount = existed ? cart.get(product) : 0.0;
        if(newAmount <= 0) {
            if(!cart.containsKey(product)) return;
            for (int i = 0; i < IShoppingListeners.size(); i++) {
                IShoppingListeners.get(i).onProductUpdate(product, newAmount);
            }
            cart.remove(product);
            for (int i = 0; i < IShoppingListeners.size(); i++) {
                IShoppingListeners.get(i).onProductRemoved(product, oldAmount);
            }
            return;
        }
        cart.put(product, newAmount);
        if(!existed) {
            for (int i = 0; i < IShoppingListeners.size(); i++) {
                IShoppingListeners.get(i).onProductAdded(product, newAmount);
            }
        }
        for (int i = 0; i < IShoppingListeners.size(); i++) {
            IShoppingListeners.get(i).onProductUpdate(product, newAmount);
        }
    }

    public double getProductAmount(Product product) {
        return cart.getOrDefault(product, 0.0);
    }

    public void addToShoppingCart(Product product, double amount) {
        updateShoppingCart(product, cart.getOrDefault(product,0.0) + amount);
    }

    public void addCategoryListener(ICategoryListener categoryListener) { categoryListeners.add(categoryListener); }

    public void addSearchListener(ISearchListener searchListener) { searchListeners.add(searchListener); }

    public double getCartPrice() {
        return cart.entrySet().stream().mapToDouble(e -> e.getKey().getPrice() * e.getValue()).sum();
    }

    public Set<Product> getProductsInCart() {
        return cart.keySet();
    }

    public void clearCart() {
        while(0 < cart.size()) {
            updateShoppingCart(cart.keySet().iterator().next(), 0);
        }
    }

    public void selectCategory(ProductCategory category) {
        selectedCategory = category;
        categoryListeners.forEach(x->x.onCategorySelected(category));
    }

    public void search(String searchTerm) {
        List<Product> products = new ArrayList<>();

        for (Product product : IMatDataHandler.getInstance().getProducts()) {
            if (product.getName().toLowerCase().contains(searchTerm.toLowerCase())) {
                products.add(product);
            }
        }

        searchListeners.forEach(x->x.onSearch(searchTerm, products));

    }

    public void verifyExistence() {
        System.out.println("Model exists!");
    }

    public void openHistoryView() {

    }

    public void openCheckoutView() {
        System.out.println("Checkout");
    }

    public void addNavigationListener(INavigationListener navigationListener) {
        navigationListeners.add(navigationListener);
    }
}
