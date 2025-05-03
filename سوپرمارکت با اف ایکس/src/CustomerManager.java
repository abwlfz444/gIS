import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerManager {
    private final String filePath;

    public CustomerManager(String filePath) {
        this.filePath = filePath;
    }

    public void addCustomer(Customer customer) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(customer.toString());
            writer.newLine();
        }
    }

    public List<Customer> getAllCustomers() throws IOException {
        List<Customer> customers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Customer customer = new Customer(data[0], data[1], data[2]);
                customers.add(customer);
            }
        }
        return customers;
    }

    public void updateCustomer(Customer updatedCustomer) throws IOException {
        List<Customer> customers = getAllCustomers();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Customer customer : customers) {
                if (customer.getId().equals(updatedCustomer.getId())) {
                    writer.write(updatedCustomer.toString());
                } else {
                    writer.write(customer.toString());
                }
                writer.newLine();
            }
        }
    }

    public void deleteCustomer(String customerId) throws IOException {
        List<Customer> customers = getAllCustomers();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Customer customer : customers) {
                if (!customer.getId().equals(customerId)) {
                    writer.write(customer.toString());
                    writer.newLine();
                }
            }
        }
    }
}
