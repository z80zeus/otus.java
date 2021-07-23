package homework;

import java.util.AbstractMap;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны
    TreeMap<Customer, String>   customers = new TreeMap<>(new java.util.Comparator<Customer>() {
        @Override
        public int compare(Customer o1, Customer o2) {
            return (int) (o1.getScores() - o2.getScores());
        }
    });

    public Map.Entry<Customer, String> getSmallest() {
        return createCloneOfMapEntry(customers.firstEntry());
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
//        final var foundEntry = customers.firstEntry();
//        if (foundEntry == null) return null;
//        final var foundCustomer = foundEntry.getKey();
//        final var rtnCustomer = new Customer(foundCustomer.getId(), foundCustomer.getName(), foundCustomer.getScores());
//        return new AbstractMap.SimpleEntry<Customer, String> (rtnCustomer, foundEntry.getValue());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return createCloneOfMapEntry(customers.higherEntry(customer));
//        final var foundEntry = customers.higherEntry(customer);
//        if (foundEntry == null) return null;
//        final var foundCustomer = foundEntry.getKey();
//        final var rtnCustomer = new Customer(foundCustomer.getId(), foundCustomer.getName(), foundCustomer.getScores());
//        return new AbstractMap.SimpleEntry<Customer, String> (rtnCustomer, foundEntry.getValue());
    }

    public void add(Customer customer, String data) {
        customers.put(customer, data);
    }

    private Map.Entry<Customer, String> createCloneOfMapEntry(Map.Entry<Customer, String> entry) {
        if (entry == null) return null;
        final var entryCustomer = entry.getKey();
        final var rtnCustomer = new Customer(entryCustomer.getId(), entryCustomer.getName(), entryCustomer.getScores());
        return new AbstractMap.SimpleEntry<Customer, String> (rtnCustomer, entry.getValue());
    }
}
