package imat.ui.controls.history.order;

import imat.utils.FXMLLoader;
import imat.utils.IMatUtils;
import imat.ui.views.browse.history.OrderHistoryPane;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

public class OrderHistoryItem extends AnchorPane implements Initializable {

    @FXML
    private Label dateLabel;

    private OrderHistoryPane orderHistoryPane;

    private Order order;

    private final String dateFormat;

    public OrderHistoryItem() {
        super();
        dateFormat = "yyyy/MM/dd - HH:mm";
        FXMLLoader.loadFXMLFromRootPackage("order_history_item.fxml", this, this);
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

    public Order getOrder() {
        return IMatUtils.cloneOrder(order);
    }

    @FXML
    private void onAction(Event Event) {
        orderHistoryPane.showArticlesPane(this);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setOrderHistoryPane(OrderHistoryPane orderHistoryPane) {
        this.orderHistoryPane = orderHistoryPane;
    }

    public void setOrder(Order order) {
        this.order = order;
        dateLabel.setText(getDate(dateFormat));
    }

}
