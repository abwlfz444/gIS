import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoanManager {
    private List<Loan> loans;
    private static final String CSV_FILE = "loans.csv";

    public LoanManager() {
        loans = new ArrayList<>();
        loadLoansFromCSV();
    }

    public void addLoan(Loan loan) {
        loans.add(loan);
        saveLoansToCSV();
    }

    public void removeLoan(Loan loan) {
        loans.remove(loan);
        saveLoansToCSV();
    }

    public List<Loan> getLoans() {
        return loans;
    }

    private void loadLoansFromCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("Reading line: " + line);
                String[] values = line.split(",");
                if (values.length == 8) {
                    Book book = new Book(values[0], values[1], values[2]);
                    Member member = new Member(values[3], values[4], values[5]);
                    LocalDate loanDate = LocalDate.parse(values[6]);
                    LocalDate returnDate = LocalDate.parse(values[7]);
                    loans.add(new Loan(book, member, loanDate, returnDate));
                    System.out.println("Loaded loan: " + book.getTitle() + " for " + member.getFirstName() + " " + member.getLastName());
                } else {
                    System.out.println("Incorrect format: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveLoansToCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE))) {
            for (Loan loan : loans) {
                bw.write(loan.getBook().getTitle() + "," + loan.getBook().getAuthor() + "," + loan.getBook().getIsbn() + "," +
                        loan.getMember().getFirstName() + "," + loan.getMember().getLastName() + "," + loan.getMember().getMemberId() + "," +
                        loan.getLoanDate() + "," + loan.getReturnDate());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} 