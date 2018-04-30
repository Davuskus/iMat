package imat.controls.history.order;

import imat.utils.FXMLLoader;
import imat.views.history.OrderHistoryController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderHistoryItem extends AnchorPane {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label dateLabel;

    private final OrderHistoryController orderHistoryController;

    private final Order order;

    private final String dateFormat;

    public OrderHistoryItem(Order order, OrderHistoryController orderHistoryController) {
        this.orderHistoryController = orderHistoryController;
        this.order = order;
        FXMLLoader.loadFXMLFromRootPackage("order_history_item.fxml", this, this);

        dateFormat = "yyyy/MM/dd - HH:mm";
        dateLabel.setText(getDate(dateFormat));

    }

    /**
     * Returns the orders shopping items in a list.
     *
     * @return The order's shopping items.
     */
    public List<ShoppingItem> getShoppingItems() {
        List<ShoppingItem> copiedShoppingItemList = new ArrayList<>(order.getItems().size());
        copiedShoppingItemList.addAll(order.getItems());
        return copiedShoppingItemList;
    }

    /**
     * Returns the order date. Some format examples: "yyyy/MM/dd" and "yyyy/MM/dd - HH:mm"
     *
     * @param format The date format.
     * @return The date in the given format.
     */
    public String getDate(String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(order.getDate());
    }

    /**
     * Returns the total order price.
     *
     * @return The total order price.
     */
    public double getOrderPrice() {
        double price = 0;
        for (ShoppingItem shoppingItem : order.getItems()) {
            price += shoppingItem.getTotal();
        }
        return price;
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

    @FXML
    private void onAction(Event Event) {
        orderHistoryController.showProductsPane(this);
    }

}
