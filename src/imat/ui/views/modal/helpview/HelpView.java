package imat.ui.views.modal.helpview;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;
import netscape.javascript.JSObject;
import java.net.URL;
import java.util.ResourceBundle;

// TODO: Match background color of anchorpane and webview

public class HelpView implements Initializable {

    @FXML
    WebView helpTextView;

    WebEngine webEngine;

    @FXML
    AnchorPane basePane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //FXMLLoader.loadFXMLFromRootPackage("helpView.fxml", this, this);
        //loadHtmlTextFromFile("src/imat/resources/helptext/helptext.html");
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

}