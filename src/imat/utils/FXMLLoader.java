package imat.utils;

import java.io.IOException;

/**
 * Sets the root and controllers for the relevant .fxml file and loads it after initialization.
 */
public final class FXMLLoader {

    /**
     * Sets the root and the controllers for a .fxml file and loads it afterwards.
     *
     * @param fxmlFilePath The file path to a .fxml file, relative to the root class.
     * @param root The root of the .fxml object hierarchy.
     * @param controller The controllers associated with the root.
     */
    public static void loadFXMLFromRootPackage(String fxmlFilePath, Object root, Object controller) {
        javafx.fxml.FXMLLoader fxmlLoader = new javafx.fxml.FXMLLoader(root.getClass().getResource(fxmlFilePath));
        fxmlLoader.setRoot(root);
        fxmlLoader.setController(controller);

        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Sets the root and the controllers for a .fxml file and loads it afterwards.
     *
     * @param fxmlFilePath The file path to a .fxml file.
     * @param root The root of the .fxml object hierarchy.
     * @param controller The controllers associated with the root.
     */
    public static void loadFXML(String fxmlFilePath, Object root, Object controller) {
        javafx.fxml.FXMLLoader fxmlLoader = new javafx.fxml.FXMLLoader(FXMLLoader.class.getResource(fxmlFilePath));
        fxmlLoader.setRoot(root);
        fxmlLoader.setController(controller);

        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
