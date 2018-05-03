package imat;

import se.chalmers.cse.dat216.project.ProductCategory;

public class Model {

    ProductCategory selectedCategory;

    public void addToCart(){
        //...
    }

    public void addShoppingCartListener(){
        //...
    }

    public void selectCategory(ProductCategory category){
        selectedCategory = category;
        System.out.println("Selected category " + category.name());
    }

    public void addCategoryListener(){

    }

    public void verifyExistance(){
        System.out.println("Model exists!");
    }
}
