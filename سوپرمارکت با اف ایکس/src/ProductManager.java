import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductManager {
    private final String filePath;

    public ProductManager(String filePath) {
        this.filePath = filePath;
    }

    public void addProduct(Product product) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(product.toString());
            writer.newLine();
        }
    }

    public List<Product> getAllProducts() throws IOException {
        List<Product> products = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Product product = new Product(data[0], data[1], Double.parseDouble(data[2]), Integer.parseInt(data[3]));
                products.add(product);
            }
        }
        return products;
    }

    public void updateProduct(Product updatedProduct) throws IOException {
        List<Product> products = getAllProducts();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Product product : products) {
                if (product.getId().equals(updatedProduct.getId())) {
                    writer.write(updatedProduct.toString());
                } else {
                    writer.write(product.toString());
                }
                writer.newLine();
            }
        }
    }

    public void deleteProduct(String productId) throws IOException {
        List<Product> products = getAllProducts();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Product product : products) {
                if (!product.getId().equals(productId)) {
                    writer.write(product.toString());
                    writer.newLine();
                }
            }
        }
    }
}
