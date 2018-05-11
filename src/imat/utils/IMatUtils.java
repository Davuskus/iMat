package imat.utils;

import se.chalmers.cse.dat216.project.*;

import java.util.*;
import java.util.stream.Collectors;

public final class IMatUtils {

    public static ShoppingItem cloneShoppingItem(ShoppingItem shoppingItem) {
        ShoppingItem shoppingItemCopy = new ShoppingItem(cloneProduct(shoppingItem.getProduct()));
        shoppingItemCopy.setAmount(shoppingItem.getAmount());
        return shoppingItemCopy;
    }

    public static Product cloneProduct(Product product) {
        return IMatDataHandler.getInstance().getProduct(product.getProductId());
    }

    public static Order cloneOrder(Order order) {
        Order orderClone = new Order();
        orderClone.setItems(cloneShoppingItemList(order.getItems()));
        orderClone.setDate((Date) order.getDate().clone());
        orderClone.setOrderNumber(order.getOrderNumber());
        return orderClone;
    }

    public static List<ShoppingItem> cloneShoppingItemList(List<ShoppingItem> shoppingItems) {
        List<ShoppingItem> shoppingItemsClone = new ArrayList<>(shoppingItems.size());
        for (ShoppingItem shoppingItem : shoppingItems) {
            shoppingItemsClone.add(cloneShoppingItem(shoppingItem));
        }
        return shoppingItemsClone;
    }

    public static Set<ProductCategory> getCategories() {
        return IMatDataHandler
                .getInstance()
                .getProducts()
                .stream()
                .map(product -> product.getCategory())
                .collect(Collectors.toSet());
    }

    public static Product getRandomProduct() {
        Random random = new Random();
        int index = random.nextInt(IMatDataHandler.getInstance().getProducts().size() - 1);
        return IMatDataHandler.getInstance().getProduct(index);
    }

}
