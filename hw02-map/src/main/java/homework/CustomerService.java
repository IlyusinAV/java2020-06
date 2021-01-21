package homework;


import java.util.Map;
import java.util.TreeMap;

public class CustomerService {
    private final TreeMap<Customer, String> customerService = new TreeMap<>();

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    public Map.Entry<Customer, String> getSmallest() {
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        TreeMap<Customer, String> smallest = new TreeMap<>();
        var customer = customerService.firstEntry().getKey();
        var newCustomer = new Customer(customer.getId(), customer.getName(), customer.getScores());
        smallest.put(newCustomer, customerService.firstEntry().getValue());
        return smallest.firstEntry();
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        TreeMap<Customer, String> nextEntry = new TreeMap<>();
        var nextItem = customerService.higherEntry(customer);
        if (nextItem == null) return null;
        var nextKey = nextItem.getKey();
        var nextValue = nextItem.getValue();
        nextEntry.put(nextKey, nextValue);
        return nextEntry.firstEntry();
    }

    public void add(Customer customer, String data) {
        var newCustomer = new Customer(customer.getId(), customer.getName(), customer.getScores());
        customerService.put(newCustomer, data);
    }
}
