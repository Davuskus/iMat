package imat.model;

import imat.enums.NavigationTarget;
import imat.interfaces.*;
import imat.model.category.Category;
import imat.utils.CategoryFactory;
import imat.utils.ListUtils;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.util.*;
import java.util.stream.Collectors;

public class Model {

    private Map<Product, Double> cart = new HashMap<>();
    private List<Category> categories;
    private Category currentCategory;

    private final List<IShoppingListener> IShoppingListeners;
    private final List<ICategoryListener> categoryListeners;
    private final List<INavigationListener> navigationListeners;
    private final List<ISearchListener> searchListeners;
    private final List<IOrderListener> orderListeners;
    private List<IRemoveEvent> cartItemRemoveEvents;
    private List<IRemoveEvent> checkoutItemRemoveEvents;
    private final List<IShutdownListener> shutdownListeners;
    private final List<ICartTrashListener> cartTrashListeners;
    private final List<IEcologicalListener> ecologicalListeners;

    private boolean isThrowingCartInTrash;

    private final Deque<NavigationTarget> navigationHistory;

    private List<Order> orders;
    private int numOrders;

    public Model() {
        categories = CategoryFactory.getCategoriesFromFolder("src/imat/resources/categories");
        ecologicalListeners = new ArrayList<>(1);
        IShoppingListeners = new ArrayList<>(1);
        categoryListeners = new ArrayList<>(1);
        navigationListeners = new ArrayList<>(1);
        searchListeners = new ArrayList<>(1);
        orderListeners = new ArrayList<>(1);
        checkoutItemRemoveEvents = new ArrayList<>(1);
        cartItemRemoveEvents = new ArrayList<>(1);
        shutdownListeners = new ArrayList<>(1);
        cartTrashListeners = new ArrayList<>(1);
        cartTrashListeners.add(new ICartTrashListener() {
            @Override
            public void onCartTrashStarted() {
                isThrowingCartInTrash = true;
            }

            @Override
            public void onCartTrashStopped() {
                isThrowingCartInTrash = false;
            }
        });
        orders = new ArrayList<>(1);
        navigationHistory = new ArrayDeque<>();
        navigationHistory.push(NavigationTarget.HOME);
        loadBackendCart();
    }

    private void loadBackendCart() {
        IMatDataHandler.getInstance()
                .getShoppingCart()
                .getItems()
                .forEach(shoppingItem -> cart.put(shoppingItem.getProduct(), shoppingItem.getAmount()));
        IMatDataHandler.getInstance().getShoppingCart().clear();
    }


    public List<Category> getCategories() {
        return categories;
    }

    public NavigationTarget getCurrentNavigationTarget() {
        return navigationHistory.peek();
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
        if (navigationHistory.peek() != navigationTarget) {
            navigationHistory.push(navigationTarget);
        }
        navigationListeners.forEach(x -> x.navigateTo(navigationTarget));
    }

    public void addEcologicalListener(IEcologicalListener ecologicalListener) {
        ecologicalListeners.add(ecologicalListener);
    }

    public void notifyEcologicalListeners(boolean isEcological) {
        ecologicalListeners.forEach(listener -> listener.onEcologicalUpdate(isEcological));
    }

    public void addShoppingListener(IShoppingListener IShoppingListener) {
        IShoppingListeners.add(IShoppingListener);
    }

    public void addShutdownListener(IShutdownListener shutdownListener) {
        shutdownListeners.add(shutdownListener);
    }

    public void addCartTrashListener(ICartTrashListener cartTrashListener) {
        cartTrashListeners.add(cartTrashListener);
    }

    public void notifyCartTrashListenersOfStart() {
        cartTrashListeners.forEach(ICartTrashListener::onCartTrashStarted);
    }

    public void notifyCartTrashListenersOfStop() {
        cartTrashListeners.forEach(ICartTrashListener::onCartTrashStopped);
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

    public void clearCartFast() {
        cartItemRemoveEvents.forEach(IRemoveEvent::execute);
        checkoutItemRemoveEvents.forEach(IRemoveEvent::execute);
        cartItemRemoveEvents.clear();
        checkoutItemRemoveEvents.clear();
        clearCart();
    }

    public void addCartItemRemoveEvent(IRemoveEvent removeEvent) {
        cartItemRemoveEvents.add(removeEvent);
    }

    public void addCheckoutItemRemoveEvent(IRemoveEvent removeEvent) {
        checkoutItemRemoveEvents.add(removeEvent);
    }

    public void selectCategory(Category category) {
        currentCategory = category;
        categoryListeners.forEach(x -> x.onCategorySelected(category));
    }

    public Category getCurrentCategory() {
        return currentCategory;
    }

    public void returnToCategoryRoot() {
        if (currentCategory != null) {
            categoryListeners.forEach(x -> x.onCategorySelected(currentCategory));
        }
    }

    public void selectOrder(Order order) {
        orderListeners.forEach(listener -> listener.onOrderSelected(order));
    }

    public void search(String searchTerm) {
        if (searchTerm.length() == 0) return;
        categoryListeners.forEach(x -> x.onCategorySelected(null));
        List<Product> products = new ArrayList<>();

        for (Product product : IMatDataHandler.getInstance().getProducts()) {
            if (product.getName().toLowerCase().contains(searchTerm.trim().toLowerCase())) {
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

    public void placeOrder() {
        Set<Product> products = cart.keySet();
        for (Product product : products) {
            IMatDataHandler.getInstance().getShoppingCart().addProduct(product, cart.get(product));
        }

        IMatDataHandler.getInstance().placeOrder(true);
        clearCartFast();
    }

    public List<Product> getCommonlyPurchasedProducts(int maxNumProducts) {

        List<Order> orders = IMatDataHandler.getInstance().getOrders();
        Map<Product, Double> articles = new HashMap<>();

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
                .limit(maxNumProducts)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

    }

    public List<Product> getAllProducts() {
        return IMatDataHandler.getInstance().getProducts();
    }

    public Category searchForCategory(String searchTerm) {
        Category category = null;
        for (Category c : categories) {
            if (c.getName().toLowerCase().contains(searchTerm.trim().toLowerCase())) {
                category = c;
                break;
            }
        }
        return category;
    }

    public void notifyShutdownListeners() {
        shutdownListeners.forEach(IShutdownListener::onShutdown);
    }

    public List<Order> updateOrderList() {
        if (numOrdersChanged()) {
            orders.clear();
            List<Order> backendOrders = IMatDataHandler.getInstance().getOrders();
            numOrders = backendOrders.size();
            orders = ListUtils.getReversedList(backendOrders);
            removePotentialDuplicateOrders(orders);
            orders.forEach(this::removePotentialDuplicateProducts);
        }
        return ListUtils.cloneList(orders);
    }

    public int getNumOrders() {
        return orders.size();
    }

    public boolean numOrdersChanged() {
        return numOrders != IMatDataHandler.getInstance().getOrders().size();
    }

    private void removePotentialDuplicateOrders(List<Order> orders) {
        List<Order> noDuplicates = new ArrayList<>(1);
        orders.forEach(order -> {
            if (!noDuplicates.contains(order)) {
                noDuplicates.add(order);
            }
        });
        orders.clear();
        orders.addAll(noDuplicates);
    }

    private void removePotentialDuplicateProducts(Order order) {
        List<ShoppingItem> shoppingItems = getUniqueShoppingItems(order);
        order.getItems().clear();
        order.setItems(shoppingItems);
    }

    private List<ShoppingItem> getUniqueShoppingItems(Order order) {
        Map<Product, Double> shoppingItemMap = new HashMap<>();
        order.getItems().forEach(shoppingItem -> {
            Product product = shoppingItem.getProduct();
            double amount = shoppingItem.getAmount();
            if (shoppingItemMap.keySet().contains(product)) {
                shoppingItemMap.put(product, shoppingItemMap.get(product) + amount);
            } else {
                shoppingItemMap.put(product, amount);
            }
        });
        List<ShoppingItem> shoppingItems = new ArrayList<>(shoppingItemMap.keySet().size());
        shoppingItemMap.forEach(((product, amount) -> shoppingItems.add(new ShoppingItem(product, amount))));

        return shoppingItems;
    }

    public Order getOlderOrder(Order sourceOrder) {
        if (!orders.isEmpty()) {
            if (orders.indexOf(sourceOrder) < orders.size() - 1) {
                int index = 0;
                for (Order order : orders) {
                    index++;
                    if (order.equals(sourceOrder)) {
                        return orders.get(index);
                    }
                }
            }
        }

        return null;
    }

    public Order getNewerOrder(Order sourceOrder) {
        if (!orders.isEmpty()) {
            if (orders.indexOf(sourceOrder) > 0) {
                int index = -1;
                for (Order order : orders) {
                    if (order.equals(sourceOrder)) {
                        return orders.get(index);
                    }
                    index++;
                }
            }
        }

        return null;
    }

    public boolean isCartBeingThrownInTheTrash() {
        return isThrowingCartInTrash;
    }

}
