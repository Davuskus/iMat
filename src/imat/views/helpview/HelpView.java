package imat.views.helpview;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;

public class HelpView extends AnchorPane {
    @FXML
    ImageView helpViewCloseButton;

    @FXML
    Label helpViewCloseLabel;

    @FXML
    WebView helpTextView;

    public void loadHtmlTextFromFile(String htmlPath) {
        String htmlContent = imat.utils.FileUtils.readAllTextFromFile(htmlPath);
        loadHtmlTextFromString(htmlContent);
    }

    public void loadHtmlTextFromString(String htmlContent) {
        helpTextView.getEngine().loadContent(htmlContent, "text/html");
    }
}