package imat.utils;

import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

}
