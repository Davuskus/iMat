package imat.util;

import java.io.IOException;

public final class FXMLLoader {

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
