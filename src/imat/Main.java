package imat;

import imat.interfaces.IShutdownListener;
import imat.model.Model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class Main extends Application {

    // Backend JavaDoc: http://www.cse.chalmers.se/research/group/idc/ituniv/courses/18/dkgg/p/backend/javadoc/

    private List<IShutdownListener> shutdownListeners;

    @Override
    public void start(Stage primaryStage) throws Exception {

        loadFonts();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ui/imat.fxml"));

        Model model = new Model();

        shutdownListeners = new ArrayList<>(1);
        shutdownListeners.add(() -> {
            model.notifyShutdownListeners();
            model.saveShoppingCart();
            IMatDataHandler.getInstance().shutDown();
        });

        imat.utils.FXMLLoader.trySetModel(model);
        loader.setControllerFactory(imat.utils.FXMLLoader::controllerFactoryMethod);

        Parent root = loader.load();

        primaryStage.setTitle("iMat");

        double ratio = 4.0 / 5;
        switch (Toolkit.getDefaultToolkit().getScreenResolution()) {
            case 120:
                ratio *= 1 / 1.25;
                break;
            case 144:
                ratio *= 1 / 1.5;
                break;
            case 192:
                ratio *= 0.5;
                break;
        }
        int stageWidth = (int) (0.5 + Toolkit.getDefaultToolkit().getScreenSize().width * ratio);
        int stageHeight = (int) (0.5 + Toolkit.getDefaultToolkit().getScreenSize().height * ratio);

        primaryStage.setScene(new Scene(root, stageWidth, stageHeight));
        primaryStage.getIcons().add(new Image("imat/resources/images/logo/imat_logo_icon.png"));
        primaryStage.show();
    }

    private void loadFonts() throws UnsupportedEncodingException, FileNotFoundException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource("imat/resources/fonts");
        String path = URLDecoder.decode(url.getFile(), "UTF-8");
        for(File fontFolder : new File(path).listFiles()) {
            if(!fontFolder.isDirectory()) continue;
            for(File fontFile : fontFolder.listFiles()) {
                if(!fontFile.getName().endsWith(".ttf")) continue;
                Font loaded = Font.loadFont(new FileInputStream(fontFile), 10);
                System.out.println(loaded.getFamily());
            }
        }
    }

    @Override
    public void stop() {
        shutdownListeners.forEach(IShutdownListener::onShutdown);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
