package imat.views.helpview;

import imat.controllers.MainController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import imat.utils.FXMLLoader;

import java.net.URL;
import java.util.ResourceBundle;

// TODO: Match background color of anchorpane and webview
// TODO: Change X-icon?

public class HelpView implements Initializable {
    @FXML
    ImageView helpViewCloseButton;

    @FXML
    Label helpViewCloseLabel;

    @FXML
    WebView helpTextView;

    @FXML
    AnchorPane basePane;

    @FXML
    ScrollPane helpScrollPane;

    @FXML MainController mainController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //FXMLLoader.loadFXMLFromRootPackage("helpView.fxml", this, this);
        //loadHtmlTextFromFile("res/helpText.html");
        //basePane.toBack();
        //helpTextView.toBack();
        //helpScrollPane.toBack();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private void loadHtmlTextFromFile(String htmlPath) {
        String htmlContent = imat.utils.FileUtils.readAllTextFromFile(htmlPath);
        loadHtmlTextFromString(htmlContent);
    }

    private void loadHtmlTextFromString(String htmlContent) {
        helpTextView.getEngine().loadContent(htmlContent, "text/html");
    }


    @FXML
    public void closeHelpView() {

    }

    @FXML
    public void mouseTrap(Event event) {
        event.consume();
    }

    @FXML
    public void closeButtonMouseEntered() {
        helpViewCloseButton.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "../res/images/icons/close/icon_close_hover.png"
        )));
    }

    @FXML
    public void closeButtonMousePressed() {
        helpViewCloseButton.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "../res/images/icons/close/icon_close_pressed.png"
        )));
    }

    @FXML
    public void closeButtonMouseExited() {
        helpViewCloseButton.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "../res/images/icons/close/icon_close_close.png"
        )));
    }
}