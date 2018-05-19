package imat.model.category;

import se.chalmers.cse.dat216.project.Product;

import java.util.*;

public class Category {
    private String name;
    private Map<String,List<Product>> dataSet;

    public Category(String name) {
        this.name = name;
        dataSet = new HashMap<>();
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

    public List<Product> getProductsFromSubcategory(String subcategory) {
        return dataSet.get(subcategory);
    }

    public List<String> getSubcategories() {
        List<String> subcategories = new ArrayList<>();
        subcategories.addAll(dataSet.keySet());

        return subcategories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name) &&
                Objects.equals(dataSet, category.dataSet);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, dataSet);
    }
}
