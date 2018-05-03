package imat.interfaces;


import se.chalmers.cse.dat216.project.Product;

public interface ShoppingListener {
    void onProductAdded(Product product, Double amount);
    void onProductRemoved(Product product, Double oldAmount);
    void onProductUpdate(Product product, Double newAmount);
}
