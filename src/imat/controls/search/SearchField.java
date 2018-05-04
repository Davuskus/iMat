package imat.controls.search;

import imat.Model;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;

public class SearchField {

    Model model;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchBox;

    private void makeSearch() {
        if (model == null) {
            System.out.println("No model attached to search field!");
            return;
        }

        model.search(searchBox.getText());
    }

    @FXML
    private void onEnter(Event event) {
        makeSearch();
        System.out.println("Enter was pressed!");
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

    public void setModel(Model model) {
        this.model = model;
    }

}
