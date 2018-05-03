package imat;

import imat.controls.cartsidebar.CartSidebar;
import imat.controls.categorysidebar.CategorySidebar;
import imat.controls.header.Header;
import imat.interfaces.IFXMLController;
import imat.views.browse.Browse;
import imat.views.modal.Modal;
import imat.views.pay.Pay;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.awt.*;
import java.util.Arrays;

public class Main extends Application {

    // Backend JavaDoc: http://www.cse.chalmers.se/research/group/idc/ituniv/courses/18/dkgg/p/backend/javadoc/

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("imat.fxml"));

        Model m = new Model();

        imat.utils.FXMLLoader.trySetModel(m);
        loader.setControllerFactory(imat.utils.FXMLLoader::controllerFactoryMethod);

        Parent root = loader.load();

        primaryStage.setTitle("iMat");

        double ratio = 1.0 / 2;
        int stageWidth = (int) (0.5 + Toolkit.getDefaultToolkit().getScreenSize().width * ratio);
        int stageHeight = (int) (0.5 + Toolkit.getDefaultToolkit().getScreenSize().height * ratio);

        primaryStage.setScene(new Scene(root, stageWidth, stageHeight));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            IMatDataHandler.getInstance().shutDown();
        }));
    }
}
