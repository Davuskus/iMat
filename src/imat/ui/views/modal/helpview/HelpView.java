package imat.ui.views.modal.helpview;

import imat.enums.NavigationTarget;
import imat.interfaces.INavigationListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

// TODO: Match background color of anchorpane and webview

public class HelpView implements Initializable, INavigationListener {

    @FXML
    WebView helpTextView;

    WebEngine webEngine;

    @FXML
    AnchorPane basePane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //FXMLLoader.loadFXMLFromRootPackage("helpView.fxml", this, this);
        //loadHtmlTextFromFile("src/imat/resources/helptext/helptext.html");

        Font.loadFont(
                HelpView.class.getResource("../../../../resources/fonts/raleway/Raleway-Thin.ttf").toExternalForm(),
                10
        );


        webEngine = helpTextView.getEngine();
        loadHtmlTextFromFile("src/imat/resources/helptext/helptext.html");
        helpTextView.getEngine().setUserStyleSheetLocation(getClass().getResource("../../../../resources/helptext/helpStyle.css").toString());

    }

    private void loadHtmlTextFromFile(String htmlPath) {
        String htmlContent = imat.utils.FileUtils.readAllTextFromFile(htmlPath);
        webEngine.loadContent(htmlContent, "text/html");
    }

    private void setStyleFromFile(String cssPath) {
        //helpTextView.getEngine().setUserStyleSheetLocation(getClass().getResource(cssPath).toString());
    }

    @Override
    public void navigateTo(NavigationTarget navigationTarget) {
        basePane.setDisable(navigationTarget != NavigationTarget.HELP);
    }

}