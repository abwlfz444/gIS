import java.time.LocalDate;

public class Sale {
    private int saleId;
    private Product product;
    private Customer customer;
    private LocalDate date;
    private int quantity;

    public Sale(int saleId, Product product, Customer customer, LocalDate date, int quantity) {
        this.saleId = saleId;
        this.product = product;
        this.customer = customer;
        this.date = date;
        this.quantity = quantity;
    }

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "saleId=" + saleId +
                ", product=" + product.getName() +
                ", customer=" + customer.getName() +
                ", date=" + date +
                ", quantity=" + quantity +
                '}';
    }
}
