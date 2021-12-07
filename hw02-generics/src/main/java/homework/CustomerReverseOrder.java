package homework;

import java.util.Stack;

public class CustomerReverseOrder {

    Stack<Customer> customerStack = new Stack<>();

    public void add(Customer customer) {
        customerStack.add(customer);
    }

    public Customer take() {
        return customerStack.pop();
    }
}
