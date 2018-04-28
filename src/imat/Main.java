package imat;

import imat.views.browse.Browse;
import imat.views.modal.Modal;
import imat.views.pay.Pay;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.awt.*;

public class Main extends Application {

    // Backend JavaDoc: http://www.cse.chalmers.se/research/group/idc/ituniv/courses/18/dkgg/p/backend/javadoc/

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("imat.fxml"));

        //Model m = ... ;

        Browse browseViewController = new Browse(/*m*/);
        Modal modalViewController = new Modal(/*m*/);
        Pay payViewController = new Pay(/*m*/);

        Callback<Class<?>, Object> controllerFactory = type -> {
            if (type == Browse.class) {
                return browseViewController;
            } else if (type == Modal.class) {
                return modalViewController;
            } else if (type == Pay.class) {
                return payViewController;
            } else {
                // default behavior for controllerFactory:
                try {
                    return type.newInstance();
                } catch (Exception exc) {
                    exc.printStackTrace();
                    throw new RuntimeException(exc); // fatal, just bail...
                }
            }
        };

        loader.setControllerFactory(controllerFactory);

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
    }
}
