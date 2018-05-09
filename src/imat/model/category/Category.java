package imat.model.category;

import se.chalmers.cse.dat216.project.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Category {
    private String name;
    private Map<String,List<Product>> dataSet;

    public Category(String name) {
        this.name = name;
    }

    public void addSubcategory(String name, List<Product> subcategory) {
        dataSet.put(name, subcategory);
    }

    public String getName() {
        return name;
    }

    public List<Product> getAllProducts() {
        List<Product> allProducts = new ArrayList<>();

        Set<String> allKeys = dataSet.keySet();
        for (String key : allKeys) {
            allProducts.addAll(dataSet.get(key));
        }

        return allProducts;
    }

    public List<String> getSubcategories() {
        List<String> subcategories = new ArrayList<>();
        subcategories.addAll(dataSet.keySet());

        return subcategories;
    }
}
