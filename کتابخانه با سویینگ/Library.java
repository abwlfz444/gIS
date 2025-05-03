import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books;
    private static final String CSV_FILE = "library.csv";

    public Library() {
        books = new ArrayList<>();
        loadBooksFromCSV();
    }

    public void addBook(Book book) {
        books.add(book);
        saveBooksToCSV();
    }

    public void removeBook(Book book) {
        books.remove(book);
        saveBooksToCSV();
    }

    public List<Book> getBooks() {
        return books;
    }

    private void loadBooksFromCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 3) {
                    books.add(new Book(values[0], values[1], values[2]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveBooksToCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE))) {
            for (Book book : books) {
                bw.write(book.getTitle() + "," + book.getAuthor() + "," + book.getIsbn());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} 