package imat.utils;

import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

public final class IMatUtils {

    public static ShoppingItem cloneShoppingItem(ShoppingItem shoppingItem) {
        ShoppingItem shoppingItemCopy = new ShoppingItem(cloneProduct(shoppingItem.getProduct()));
        shoppingItemCopy.setAmount(shoppingItem.getAmount());
        return shoppingItemCopy;
    }

    public static Product cloneProduct(Product product) {
        Product productCopy = new Product();
        productCopy.setProductId(product.getProductId());
        return productCopy;
    }

}
