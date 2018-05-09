package imat.model;

import imat.enums.NavigationTarget;
import imat.interfaces.*;
import se.chalmers.cse.dat216.project.*;

import java.util.*;
import java.util.stream.Collectors;

public class Model {

    private Map<Product, Double> cart = new HashMap<>();

    private final List<IShoppingListener> IShoppingListeners;
    private final List<ICategoryListener> categoryListeners;
    private final List<INavigationListener> navigationListeners;
    private final List<ISearchListener> searchListeners;
    private final List<IProducDetailstListener> productListeners;
    private final List<IOrderListener> orderListeners;

    private boolean isThrowingCartInTrash;

    private final Deque<NavigationTarget> navigationHistory;

    public Model() {
        IShoppingListeners = new ArrayList<>(1);
        categoryListeners = new ArrayList<>(1);
        navigationListeners = new ArrayList<>(1);
        searchListeners = new ArrayList<>(1);
        productListeners = new ArrayList<>(1);
        orderListeners = new ArrayList<>(1);
        navigationHistory = new ArrayDeque<>();
        navigationHistory.push(NavigationTarget.HOME);
        loadBackendCart();
    }

    private void loadBackendCart() {
        IMatDataHandler.getInstance().getShoppingCart().getItems().forEach(
                shoppingItem -> cart.put(shoppingItem.getProduct(), shoppingItem.getAmount()));
    }

    public void navigateBack() {
        if (navigationHistory.size() >= 1) {
            navigationHistory.pop();
            navigate(navigationHistory.pop());
        } else {
            System.out.println("Model, navigateBack: There is no more navigation history.");
        }
    }

    public void navigate(NavigationTarget navigationTarget) {
        navigationHistory.push(navigationTarget);
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

    public void addOrderToCart(Order order) {
        order.getItems().forEach(this::addShoppingItemToCart);
    }

    public void addShoppingItemToCart(ShoppingItem shoppingItem) {
        addToShoppingCart(shoppingItem.getProduct(), shoppingItem.getAmount());
    }

    public void addToShoppingCart(Product product, double amount) {
        updateShoppingCart(product, cart.getOrDefault(product, 0.0) + amount);
    }

    public double getProductAmount(Product product) {
        return cart.getOrDefault(product, 0.0);
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
        IMatDataHandler.getInstance().getShoppingCart().clear();
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
        categoryListeners.forEach(x -> x.onCategorySelected(category));
    }

    public void selectOrder(Order order) {
        orderListeners.forEach(listener -> listener.onOrderSelected(order));
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

    public void addOrderListener(IOrderListener orderListener) {
        orderListeners.add(orderListener);
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

    public void placeOrder() {
        Set<Product> products = cart.keySet();
        for (Product product : products) {
            IMatDataHandler.getInstance().getShoppingCart().addProduct(product,cart.get(product));
        }

        IMatDataHandler.getInstance().placeOrder(true);
        clearCart();
    }

    public List<Product> getCommonlyPurchasedProducts(int numProducts) {
        List<Order> orders = IMatDataHandler.getInstance().getOrders();
        Map<Product, Double> articles = new HashMap<>();
        List<Product> mostCommon = new ArrayList<>();

        orders.forEach(order -> {
            order.getItems().forEach(shoppingItem -> {
                Product product = shoppingItem.getProduct();
                if (articles.containsKey(product)) {
                    articles.put(product, articles.get(product) + shoppingItem.getAmount());
                } else {
                    articles.put(product, shoppingItem.getAmount());
                }
            });
        });

        return articles
                .entrySet()
                .stream()
                .sorted((a, b) -> a.getValue() < b.getValue() ? 1 : -1)
                .limit(4)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        /*if (articles.keySet().size() < numProducts) {
            throw new IllegalArgumentException(
                    "The given number of products is greater than the available number of products");
        }

        while (mostCommon.size() < numProducts) {
            Product maxAmountProduct = null;
            double maxAmount = 0;
            for (Product product : articles.keySet()) {
                double amount = articles.get(product);
                if (amount > maxAmount) {
                    maxAmount = amount;
                    maxAmountProduct = product;
                }
            }
            articles.remove(maxAmountProduct);
            mostCommon.add(maxAmountProduct);
        }

        return mostCommon;*/
    }

}
