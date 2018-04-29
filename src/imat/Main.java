package imat;

import imat.controls.cartsidebar.CartSidebar;
import imat.controls.categorysidebar.CategorySidebar;
import imat.controls.header.Header;
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

public class Main extends Application {

    // Backend JavaDoc: http://www.cse.chalmers.se/research/group/idc/ituniv/courses/18/dkgg/p/backend/javadoc/

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("imat.fxml"));

        //TODO explicitly define a model class somewhere (if backend is not sufficient)
        //Model m = ... ;

        Browse browseViewController = new Browse(/*m*/);
        Modal modalViewController = new Modal(/*m*/);
        Pay payViewController = new Pay(/*m*/);

        //TODO Instantiation of the controller(s) below should probably be moved

        Header headerController = new Header(/*m*/);
        CategorySidebar categorySidebarController = new CategorySidebar(/*m*/);
        CartSidebar cartSidebarController = new CartSidebar(/*m*/);

        //TODO This factory method looks quite messy, a cleaner method might be worth investigating

        Callback<Class<?>, Object> controllerFactory = type -> {
            if      (type == Browse.class) return browseViewController;
            else if (type == Modal.class)  return modalViewController;
            else if (type == Pay.class)    return payViewController;
            else if (type == Header.class) return headerController;
            else if (type == CategorySidebar.class) return categorySidebarController;
            else if (type == CartSidebar.class) return cartSidebarController;
            else {
                // default behavior for controllerFactory:
                try {
                    System.out.println("Unexpected " + type.getName());
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

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                IMatDataHandler.getInstance().shutDown();
            }
        }));
    }
}
