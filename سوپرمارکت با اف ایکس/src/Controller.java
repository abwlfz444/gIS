import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;
import java.util.List;

public class Controller {
    @FXML
    private TextField productIdField;
    @FXML
    private TextField productNameField;
    @FXML
    private TextField productPriceField;
    @FXML
    private TextField productQuantityField;
    @FXML
    private TableView<Product> productTableView;
    @FXML
    private TableColumn<Product, String> idColumn;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private TableColumn<Product, Integer> quantityColumn;
    @FXML
    private TextField customerIdField;
    @FXML
    private TextField customerNameField;
    @FXML
    private TextField customerPhoneField;
    @FXML
    private TableView<Customer> customerTableView;
    @FXML
    private TableColumn<Customer, String> customerIdColumn;
    @FXML
    private TableColumn<Customer, String> customerNameColumn;
    @FXML
    private TableColumn<Customer, String> customerPhoneColumn;
    @FXML
    private TextField searchProductField;
    @FXML
    private TextField searchCustomerField;
    @FXML
    private TableView<Product> searchProductTableView;
    @FXML
    private TableView<Customer> searchCustomerTableView;

//------------------------------------------------------

    private ProductManager productManager;
    private ObservableList<Product> productList;

//------------------------------------------------------

    private CustomerManager customerManager;
    private ObservableList<Customer> customerList;

//------------------------------------------------------

    public Controller() {
        productManager = new ProductManager("src/Data/product.csv");
        productList = FXCollections.observableArrayList();

        customerManager = new CustomerManager("src/Data/customers.csv");
        customerList = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        loadProducts();

        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        priceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
        quantityColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());

        productTableView.setItems(productList);

//------------------------------------------------------

        customerIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        customerNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        customerPhoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhone()));

        customerTableView.setItems(customerList);

    }

//------------------------------------------------------

    private void loadProducts() {
        try {
            List<Product> products = productManager.getAllProducts();
            productList.clear();
            productList.addAll(products);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAddProduct() {
        try {
            String id = productIdField.getText();
            String name = productNameField.getText();
            double price = Double.parseDouble(productPriceField.getText());
            int quantity = Integer.parseInt(productQuantityField.getText());

            Product product = new Product(id, name, price, quantity);
            productManager.addProduct(product);
            loadProducts();
            clearFields2();
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleUpdateProduct() {
        try {
            String id = productIdField.getText();
            String name = productNameField.getText();
            double price = Double.parseDouble(productPriceField.getText());
            int quantity = Integer.parseInt(productQuantityField.getText());

            Product product = new Product(id, name, price, quantity);
            productManager.updateProduct(product);
            loadProducts();
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleDeleteProduct() {
        try {
            Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                productManager.deleteProduct(selectedProduct.getId());
                loadProducts();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearFields2() {
        productIdField.clear();
        productNameField.clear();
        productPriceField.clear();
        productQuantityField.clear();
    }

//------------------------------------------------------

    private void loadCustomers() {
        try {
            List<Customer> customers = customerManager.getAllCustomers();
            customerList.clear();
            customerList.addAll(customers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddCustomer() {
        try {
            String id = customerIdField.getText();
            String name = customerNameField.getText();
            String phone = customerPhoneField.getText();

            Customer newCustomer = new Customer(id, name, phone);

            customerManager.addCustomer(newCustomer);
            loadCustomers();
            clearFields1();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpdateCustomer() {
        try {
            String id = customerIdField.getText();
            String name = customerNameField.getText();
            String phone = customerPhoneField.getText();

            Customer newcustomer = new Customer(id, name, phone);
            customerManager.updateCustomer(newcustomer);
            loadCustomers();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleDeleteCustomer() {
        int selectedIndex = customerTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Customer selectedCustomer = customerTableView.getItems().get(selectedIndex);
            try {
                customerManager.deleteCustomer(selectedCustomer.getId());
                customerTableView.getItems().remove(selectedIndex);
                clearFields1();
            } catch (IOException e) {
                showAlert("خطا در حذف مشتری: " + e.getMessage());
            }
        } else {
            showAlert("لطفا یک مشتری را انتخاب کنید.");
        }
    }

    private void clearFields1() {
        customerIdField.clear();
        customerNameField.clear();
        customerPhoneField.clear();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("هشدار");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleSearchProduct() {
        String searchText = searchProductField.getText().toLowerCase();
        ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

        for (Product product : productList) {
            if (product.getName().toLowerCase().contains(searchText)) {
                filteredProducts.add(product);
            }
        }

        searchProductTableView.setItems(filteredProducts);
    }

//------------------------------------------------------

    @FXML
    private void handleSearchCustomer() {
        String searchText = searchCustomerField.getText().toLowerCase();
        ObservableList<Customer> filteredCustomers = FXCollections.observableArrayList();

        for (Customer customer : customerList) {
            if (customer.getName().toLowerCase().contains(searchText)) {
                filteredCustomers.add(customer);
            }
        }

        searchCustomerTableView.setItems(filteredCustomers);
    }
}
