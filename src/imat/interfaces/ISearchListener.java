package imat.interfaces;

import se.chalmers.cse.dat216.project.Product;

import java.util.List;

public interface ISearchListener {
    void onSearch(List<Product> products);
}
