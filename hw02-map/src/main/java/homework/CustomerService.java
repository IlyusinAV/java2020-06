package homework;


import java.util.Map;
import java.util.TreeMap;

public class CustomerService {
    private final TreeMap<Customer, String> customerService = new TreeMap<>();

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    public Map.Entry<Customer, String> getSmallest() {
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        var key = customerService.firstEntry().getKey();
        var value = customerService.firstEntry().getValue();
        var newCustomer = new Customer(key.getId(), key.getName(), key.getScores());
        return new CustomerEntry(newCustomer, value);
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        var nextItem = customerService.higherEntry(customer);
        if (nextItem == null) return null;
        var key = customerService.higherEntry(customer).getKey();
        var value = customerService.higherEntry(customer).getValue();
        var newCustomer = new Customer(key.getId(), key.getName(), key.getScores());
        return new CustomerEntry(newCustomer, value);
    }

    public void add(Customer customer, String data) {
        var newCustomer = new Customer(customer.getId(), customer.getName(), customer.getScores());
        customerService.put(newCustomer, data);
    }

    private static class CustomerEntry implements Map.Entry<Customer, String> {
        private final Customer customer;
        private String value;
        public CustomerEntry(Customer customer, String value) {
            this.customer = customer;
            this.value = value;
        }

        @Override
        public Customer getKey() {
            return this.customer;
        }

        @Override
        public String getValue() {
            return this.value;
        }

        @Override
        public String setValue(String value) {
            this.value = value;
            return this.value;
        }
    }
}
