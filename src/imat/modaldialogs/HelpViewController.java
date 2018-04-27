package imat.modaldialogs;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;

public class HelpViewController extends AnchorPane {
    @FXML
    ImageView helpViewCloseButton;

    @FXML
    Label helpViewCloseLabel;

    @FXML
    WebView helpTextView;

    public HelpViewController (String htmlPath) {
        String htmlContent = imat.utils.FileUtils.readAllTextFromFile(htmlPath);
        helpTextView.getEngine().loadContent(htmlContent, "text/html");
    }
}