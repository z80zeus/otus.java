package homework;

import java.util.AbstractMap;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    TreeMap<Customer, String>   customers = new TreeMap<>((o1, o2) -> (int) (o1.getScores() - o2.getScores()));

    public Map.Entry<Customer, String> getSmallest() {
        return createCloneOfMapEntry(customers.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return createCloneOfMapEntry(customers.higherEntry(customer));
    }

    public void add(Customer customer, String data) {
        customers.put(customer, data);
    }

    private Map.Entry<Customer, String> createCloneOfMapEntry(Map.Entry<Customer, String> entry) {
        if (entry == null) return null;
        final var entryCustomer = entry.getKey();
        final var rtnCustomer = new Customer(entryCustomer.getId(), entryCustomer.getName(), entryCustomer.getScores());
        return new AbstractMap.SimpleEntry<>(rtnCustomer, entry.getValue());
    }
}
