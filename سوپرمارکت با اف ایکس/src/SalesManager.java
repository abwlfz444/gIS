import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SalesManager {
    private ObservableList<Sale> salesList;

    public SalesManager() {
        this.salesList = FXCollections.observableArrayList();
    }

    public void addSale(Sale sale) {
        salesList.add(sale);
    }

    public ObservableList<Sale> getSalesList() {
        return salesList;
    }

    public double calculateTotalSales() {
        double total = 0;
        for (Sale sale : salesList) {
            total += sale.getProduct().getPrice() * sale.getQuantity();
        }
        return total;
    }

    public ObservableList<Sale> searchSalesByCustomer(Customer customer) {
        ObservableList<Sale> filteredSales = FXCollections.observableArrayList();
        for (Sale sale : salesList) {
            if (sale.getCustomer().equals(customer)) {
                filteredSales.add(sale);
            }
        }
        return filteredSales;
    }
}
