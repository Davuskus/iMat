package imat.utils;

import imat.interfaces.IFXMLController;
import imat.model.Model;
import javafx.scene.Node;

import java.io.IOException;
import java.net.URL;

/**
 * Sets the root and controllers for the relevant .fxml file and loads it after initialization.
 */
public final class FXMLLoader {
    private static Model model;

    public static boolean trySetModel(Model m) {
        if (model == null) {
            model = m;
            return true;
        }
        return false;
    }

    public static Object controllerFactoryMethod(Class type) {
        try {
            if (IFXMLController.class.isAssignableFrom(type)) {
                IFXMLController controller = (IFXMLController) type.newInstance();
                controller.setModel(model);
                System.out.println("loaded " + type.getName());
                return controller;
            }
            System.out.println("failed to load " + type.getName());
            return type.newInstance();
        } catch (Exception exc) {
            exc.printStackTrace();
            throw new RuntimeException(exc); // fatal, just bail...
        }
    }

    /**
     * Sets the root and the controllers for a .fxml file and loads it afterwards.
     *
     * @param fxmlFilePath The file path to a .fxml file, relative to the root class.
     * @param root         The root of the .fxml object hierarchy.
     * @param controller   The controllers associated with the root.
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
     * @param root         The root of the .fxml object hierarchy.
     * @param controller   The controllers associated with the root.
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

    public static Node loadFXMLNode(String fxmlFilePath, Object controller) {
        try {
            return javafx.fxml.FXMLLoader.load(new URL(fxmlFilePath),
                    null,
                    null,
                    new ControllerFactoryGate(type -> controller, FXMLLoader::controllerFactoryMethod));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Node loadFXMLNodeFromRootPackage(String fxmlFilePath, Object root, Object controller) {
        try {
            return javafx.fxml.FXMLLoader.load(
                    root.getClass().getResource(fxmlFilePath),
                    null,
                    null,
                    new ControllerFactoryGate(type -> controller, FXMLLoader::controllerFactoryMethod));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
