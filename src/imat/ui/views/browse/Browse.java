package imat.ui.views.browse;


import imat.enums.NavigationTarget;
import imat.interfaces.INavigationListener;
import imat.model.FXMLController;
import imat.model.Model;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Browse extends FXMLController implements INavigationListener {

    @FXML
    private GridPane browseGridPane;

    @FXML
    private AnchorPane checkoutPane;

    @FXML
    private AnchorPane historyPane;

    @FXML
    private AnchorPane historyArticlesPane;

    @FXML
    private AnchorPane searchPane;

    @FXML
    private AnchorPane categoryPane;

    @FXML
    private AnchorPane homePane;

    @FXML
    private AnchorPane header;

    private Model model;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model.addNavigationListener(this);
        categoryPane.toFront();
    }

    @Override
    public void setModel(Model m) {
        this.model = m;
    }

    @FXML
    public void consumeEvent(Event event) {
        event.consume();
    }

    @Override
    public void navigateTo(NavigationTarget navigationTarget) {
        browseGridPane.setDisable(false);
        header.setDisable(false);
        switch (navigationTarget) {
            case ORDER_HISTORY:
                browseGridPane.toFront();
                historyPane.toFront();
                break;
            case ORDER_HISTORY_ARTICLE:
                browseGridPane.toFront();
                historyArticlesPane.toFront();
                break;
            case SEARCH_RESULTS:
                browseGridPane.toFront();
                searchPane.toFront();
                break;
            case CATEGORY:
                browseGridPane.toFront();
                categoryPane.toFront();
                break;
            case CHECKOUT:
                browseGridPane.setDisable(true);
                checkoutPane.toFront();
                break;
            case HOME:
                browseGridPane.toFront();
                homePane.toFront();
                break;
            default:
                browseGridPane.setDisable(true);
                header.setDisable(true);
                break;
        }
    }

}
