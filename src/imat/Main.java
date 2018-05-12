package imat;

import imat.interfaces.IShutdownListener;
import imat.model.Model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class Main extends Application {

    // Backend JavaDoc: http://www.cse.chalmers.se/research/group/idc/ituniv/courses/18/dkgg/p/backend/javadoc/

    private List<IShutdownListener> shutdownListeners;

    @Override
    public void start(Stage primaryStage) throws Exception {

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
        primaryStage.getIcons().add(new Image("imat/resources/images/logo/imat_logo.png"));
        primaryStage.show();
    }

    @Override
    public void stop() {
        shutdownListeners.forEach(IShutdownListener::onShutdown);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
