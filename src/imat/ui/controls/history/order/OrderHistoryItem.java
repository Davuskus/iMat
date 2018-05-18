package imat.ui.controls.history.order;

import imat.enums.NavigationTarget;
import imat.model.FXMLController;
import imat.utils.DateUtils;
import imat.utils.IMatUtils;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class OrderHistoryItem extends FXMLController {

    @FXML
    private Button button;

    @FXML
    private ImageView imageView;

    private Order order;

    private final String dateFormat;

    public enum ColorScheme {
        LIGHT, DARK
    }

    public OrderHistoryItem() {
        dateFormat = "yyyy/MM/dd - HH:mm";
    }

    /**
     * Returns the orders shopping items in a list.
     *
     * @return The order's shopping items.
     */
    public List<ShoppingItem> getShoppingItems() {
        return IMatUtils.cloneShoppingItemList(order.getItems());
    }

    /**
     * Returns the total number of shopping items (articles).
     *
     * @return The total number of shopping items (articles).
     */
    public int getNumShoppingItems() {
        return order.getItems().size();
    }

    /**
     * Returns the date format used in this class.
     *
     * @return The date format String used in this class.
     */
    public String getDateFormat() {
        return dateFormat;
    }

    public Order getOrder() {
        return IMatUtils.cloneOrder(order);
    }

    @FXML
    private void onAction(Event Event) {
        model.selectOrder(order);
        model.navigate(NavigationTarget.ORDER_HISTORY_ARTICLE);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setOrder(Order order) {
        this.order = order;
        updateDateText();
    }

    private void updateDateText() {
        button.setText("Beställning: " + DateUtils.getFormattedDate(order.getDate(), dateFormat));
    }

    public void setColorScheme(ColorScheme colorScheme) {
        switch (colorScheme) {
            case LIGHT:
                button.getStyleClass().clear();
                button.getStyleClass().add("white-button");
                button.getStyleClass().add("text-bold");
                imageView.setImage(new Image("/imat/resources/images/icons/order/icon_order.png"));
                break;
            case DARK:
            default:
                button.getStyleClass().clear();
                button.getStyleClass().add("dark-gray-button");
                button.getStyleClass().add("text-bold");
                imageView.setImage(new Image("/imat/resources/images/icons/order/icon_order_white.png"));
                break;
        }
    }

}
