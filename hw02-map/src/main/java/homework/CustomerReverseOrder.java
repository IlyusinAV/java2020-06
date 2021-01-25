package homework;

import java.util.ArrayDeque;
import java.util.Deque;

public class CustomerReverseOrder {
    private final Deque<Customer> customerStack = new ArrayDeque<>();

    //todo: 2. надо реализовать методы этого класса
    //надо подобрать подходящую структуру данных, тогда решение будет в "две строчки"

    public void add(Customer customer) {
        var newCustomer = new Customer(customer.getId(), customer.getName(), customer.getScores());
        customerStack.push(newCustomer);
    }

    public Customer take() {
        return customerStack.pop();
    }
}
