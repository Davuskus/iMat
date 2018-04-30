package imat.views.helpview;

import imat.controllers.MainController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import imat.utils.FXMLLoader;

// TODO: Match background color of anchorpane and webview
// TODO: Change X-icon?

public class HelpView extends AnchorPane {
    @FXML
    ImageView helpViewCloseButton;

    @FXML
    Label helpViewCloseLabel;

    @FXML
    WebView helpTextView;

    MainController mainController;

    AnchorPane parentWindow;

    public HelpView(MainController mainController) {
        FXMLLoader.loadFXMLFromRootPackage("helpView.fxml", this, this);
        loadHtmlTextFromFile("res/helpText.html");
        this.mainController = mainController;
        this.toFront();
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
        this.toBack();
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