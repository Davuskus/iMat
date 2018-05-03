package imat;

import imat.interfaces.IFXMLController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.awt.*;

public class Main extends Application {

    // Backend JavaDoc: http://www.cse.chalmers.se/research/group/idc/ituniv/courses/18/dkgg/p/backend/javadoc/

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("imat.fxml"));

        Model m = new Model();

        loader.setControllerFactory(type -> {
            try {
                if (IFXMLController.class.isAssignableFrom(type)) {
                    IFXMLController controller = (IFXMLController) type.newInstance();
                    controller.setModel(m);
                    return controller;
                }
                return type.newInstance();
            } catch (Exception exc) {
                exc.printStackTrace();
                throw new RuntimeException(exc); // fatal, just bail...
            }
        });

        Parent root = loader.load();

        primaryStage.setTitle("iMat");

        double ratio = 3.0 / 4;
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
