package imat.interfaces;

import imat.model.category.Category;
import se.chalmers.cse.dat216.project.ProductCategory;

/*public interface ICategoryListener {
    void onCategorySelected(ProductCategory category);
}*/

public interface ICategoryListener {
    void onCategorySelected(Category category);
}