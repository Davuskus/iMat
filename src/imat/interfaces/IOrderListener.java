package imat.interfaces;

import se.chalmers.cse.dat216.project.Order;

public interface IOrderListener {
    void onOrderSelected(Order order);
}
