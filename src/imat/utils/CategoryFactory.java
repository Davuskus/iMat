package imat.utils;

import imat.model.category.Category;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CategoryFactory {

    public static List<Category> getCategoriesFromFolder(String filePath) {
        List<Category> categoryList = new ArrayList<>();
        File folder = new File(filePath);
        File[] fileList = folder.listFiles();
        for (File file : fileList) {
            categoryList.add(getCategoryFromFile(file.getPath()));
        }

        return categoryList;
    }

    public static Category getCategoryFromFile(String filePath) {
        Category category;
        String categoryName;
        String[] categoryData;

        String rawData = FileUtils.readAllTextFromFile(filePath);
        String[] lines = rawData.split("\n");

        categoryName = lines[0].substring(7,lines[0].length()-2);
        categoryData = lines[1].substring(6,lines[1].length()-1).split(";");

        category = new Category(categoryName);

        for (String str : categoryData) {
            List<Product> productList = new ArrayList<>();
            String[] components = str.split(":");
            String subcategoryName = components[0].replaceAll("\"","");
            String[] productNumbers = components[1].replaceAll("\"","").split(",");
            for (String product : productNumbers) {
                productList.add(IMatDataHandler.getInstance().getProduct(Integer.parseInt(product)));
            }
            category.addSubcategory(subcategoryName,productList);
        }

        return category;
    }
}
