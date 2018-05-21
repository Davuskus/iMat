package imat.ui.controls.search;

import imat.enums.NavigationTarget;
import imat.model.FXMLController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchField extends FXMLController {

    @FXML
    private Button searchButton;

    @FXML
    private ImageView searchButtonImageView;

    @FXML
    private TextField searchBox;

    private boolean clearingText;

    private void makeSearch() {
        if (model == null) {
            System.out.println("No imat.model attached to search field!");
        } else if (searchBox.getText().length() == 0) {
            if (!clearingText && model.getCurrentNavigationTarget() == NavigationTarget.SEARCH_RESULTS) {
                model.returnToCategoryRoot();
                model.navigateBack();
            }
        } else {
            model.search(searchBox.getText());
            model.navigate(NavigationTarget.SEARCH_RESULTS);
        }
    }

    @FXML
    private void onEnter(Event event) {
        makeSearch();
    }

    @FXML
    private void searchButtonOnAction(Event event) {
        makeSearch();
    }

    @FXML
    private void onSearchBoxUpdate(InputEvent event) {
        // System.out.println(searchBox.getText());
        // TODO Show suggestions depending on the input
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            makeSearch();
        });
    }

    @FXML
    public void searchButtonOnMouseEntered() {
        searchButtonImageView.setImage(new Image("/imat/resources/images/icons/search/icon_search_hover.png"));
    }

    @FXML
    public void searchButtonOnMousePressed() {
        searchButtonImageView.setImage(new Image("/imat/resources/images/icons/search/icon_search_pressed.png"));
    }

    @FXML
    public void searchButtonOnMouseExited() {
        searchButtonImageView.setImage(new Image("/imat/resources/images/icons/search/icon_search.png"));
    }

    public void clearText() {
        clearingText = true;
        searchBox.clear();
        clearingText = false;
    }

}
