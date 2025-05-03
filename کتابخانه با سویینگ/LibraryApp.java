import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.time.LocalDate;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

public class LibraryApp {
    private Library library;
    private MemberManager memberManager;
    private LoanManager loanManager;
    private JFrame frame;
    private JTable bookTable;
    private JTable memberTable;
    private JTable loanTable;
    private BookTableModel bookTableModel;
    private MemberTableModel memberTableModel;
    private LoanTableModel loanTableModel;

    public LibraryApp() {
        library = new Library();
        memberManager = new MemberManager();
        loanManager = new LoanManager();
        frame = new JFrame("Library Information System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);

        bookTableModel = new BookTableModel(library.getBooks());
        bookTable = new JTable(bookTableModel);

        memberTableModel = new MemberTableModel(memberManager.getMembers());
        memberTable = new JTable(memberTableModel);

        loanTableModel = new LoanTableModel(loanManager.getLoans());
        loanTable = new JTable(loanTableModel);

        // Books Tab
        JPanel bookPanel = new JPanel(new BorderLayout());
        bookPanel.add(new JScrollPane(bookTable), BorderLayout.CENTER);
        JPanel bookButtonPanel = new JPanel();
        JButton addBookButton = new JButton("Add Book");
        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });

        JButton deleteBookButton = new JButton("Delete Book");
        deleteBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteBook();
            }
        });

        JButton editBookButton = new JButton("Edit Book");
        editBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editBook();
            }
        });

        JTextField bookSearchField = new JTextField(15);
        TableRowSorter<BookTableModel> bookSorter = new TableRowSorter<>(bookTableModel);
        bookTable.setRowSorter(bookSorter);
        bookSearchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                search(bookSorter, bookSearchField);
            }
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                search(bookSorter, bookSearchField);
            }
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                search(bookSorter, bookSearchField);
            }
        });

        bookButtonPanel.add(new JLabel("Search Books:"));
        bookButtonPanel.add(bookSearchField);
        bookButtonPanel.add(addBookButton);
        bookButtonPanel.add(deleteBookButton);
        bookButtonPanel.add(editBookButton);
        bookPanel.add(bookButtonPanel, BorderLayout.SOUTH);

        // Members Tab
        JPanel memberPanel = new JPanel(new BorderLayout());
        memberPanel.add(new JScrollPane(memberTable), BorderLayout.CENTER);
        JPanel memberButtonPanel = new JPanel();
        JButton addMemberButton = new JButton("Add Member");
        addMemberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMember();
            }
        });

        JButton deleteMemberButton = new JButton("Delete Member");
        deleteMemberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteMember();
            }
        });

        JButton editMemberButton = new JButton("Edit Member");
        editMemberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editMember();
            }
        });

        JTextField memberSearchField = new JTextField(15);
        TableRowSorter<MemberTableModel> memberSorter = new TableRowSorter<>(memberTableModel);
        memberTable.setRowSorter(memberSorter);
        memberSearchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                search(memberSorter, memberSearchField);
            }
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                search(memberSorter, memberSearchField);
            }
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                search(memberSorter, memberSearchField);
            }
        });

        memberButtonPanel.add(new JLabel("Search Members:"));
        memberButtonPanel.add(memberSearchField);
        memberButtonPanel.add(addMemberButton);
        memberButtonPanel.add(deleteMemberButton);
        memberButtonPanel.add(editMemberButton);
        memberPanel.add(memberButtonPanel, BorderLayout.SOUTH);

        // Loans Tab
        JPanel loanPanel = new JPanel(new BorderLayout());
        loanPanel.add(new JScrollPane(loanTable), BorderLayout.CENTER);
        JPanel loanButtonPanel = new JPanel();

        JComboBox<String> bookComboBox = new JComboBox<>();
        for (Book book : library.getBooks()) {
            bookComboBox.addItem(book.getTitle());
        }

        JComboBox<String> memberComboBox = new JComboBox<>();
        for (Member member : memberManager.getMembers()) {
            memberComboBox.addItem(member.getFirstName() + " " + member.getLastName());
        }

        JButton addLoanButton = new JButton("Add Loan");
        addLoanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int bookIndex = bookComboBox.getSelectedIndex();
                int memberIndex = memberComboBox.getSelectedIndex();
                if (bookIndex >= 0 && memberIndex >= 0) {
                    Book book = library.getBooks().get(bookIndex);
                    Member member = memberManager.getMembers().get(memberIndex);
                    try {
                        String loanDateString = JOptionPane.showInputDialog(frame, "Enter loan date (YYYY-MM-DD):");
                        LocalDate loanDate = LocalDate.parse(loanDateString);
                        String returnDateString = JOptionPane.showInputDialog(frame, "Enter return date (YYYY-MM-DD):");
                        LocalDate returnDate = LocalDate.parse(returnDateString);
                        Loan loan = new Loan(book, member, loanDate, returnDate);
                        loanManager.addLoan(loan);
                        loanTableModel.fireTableDataChanged();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, "Invalid date format. Please enter the date as YYYY-MM-DD.");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a book and a member to create a loan.");
                }
            }
        });

        JButton deleteLoanButton = new JButton("Delete Loan");
        deleteLoanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteLoan();
            }
        });

        JButton editLoanButton = new JButton("Edit Loan");
        editLoanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editLoan();
            }
        });

        loanButtonPanel.add(new JLabel("Select Book:"));
        loanButtonPanel.add(bookComboBox);
        loanButtonPanel.add(new JLabel("Select Member:"));
        loanButtonPanel.add(memberComboBox);
        loanButtonPanel.add(addLoanButton);
        loanButtonPanel.add(deleteLoanButton);
        loanButtonPanel.add(editLoanButton);
        loanPanel.add(loanButtonPanel, BorderLayout.SOUTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Books", bookPanel);
        tabbedPane.addTab("Members", memberPanel);
        tabbedPane.addTab("Loans", loanPanel);

        frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void addBook() {
        String title = JOptionPane.showInputDialog(frame, "Enter book title:");
        String author = JOptionPane.showInputDialog(frame, "Enter book author:");
        String isbn = JOptionPane.showInputDialog(frame, "Enter book ISBN:");
        if (title != null && author != null && isbn != null) {
            Book book = new Book(title, author, isbn);
            library.addBook(book);
            bookTableModel.fireTableDataChanged();
        }
    }

    private void deleteBook() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow >= 0) {
            Book book = library.getBooks().get(selectedRow);
            library.removeBook(book);
            bookTableModel.fireTableDataChanged();
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a book to delete.");
        }
    }

    private void addMember() {
        String firstName = JOptionPane.showInputDialog(frame, "Enter member first name:");
        String lastName = JOptionPane.showInputDialog(frame, "Enter member last name:");
        String memberId = JOptionPane.showInputDialog(frame, "Enter member ID:");
        if (firstName != null && lastName != null && memberId != null) {
            Member member = new Member(firstName, lastName, memberId);
            memberManager.addMember(member);
            memberTableModel.fireTableDataChanged();
        }
    }

    private void deleteMember() {
        int selectedRow = memberTable.getSelectedRow();
        if (selectedRow >= 0) {
            Member member = memberManager.getMembers().get(selectedRow);
            memberManager.removeMember(member);
            memberTableModel.fireTableDataChanged();
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a member to delete.");
        }
    }

    private void deleteLoan() {
        int selectedRow = loanTable.getSelectedRow();
        if (selectedRow >= 0) {
            Loan loan = loanManager.getLoans().get(selectedRow);
            loanManager.removeLoan(loan);
            loanTableModel.fireTableDataChanged();
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a loan to delete.");
        }
    }

    private void editBook() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow >= 0) {
            Book book = library.getBooks().get(selectedRow);
            String newTitle = JOptionPane.showInputDialog(frame, "Edit book title:", book.getTitle());
            String newAuthor = JOptionPane.showInputDialog(frame, "Edit book author:", book.getAuthor());
            String newIsbn = JOptionPane.showInputDialog(frame, "Edit book ISBN:", book.getIsbn());
            if (newTitle != null && newAuthor != null && newIsbn != null) {
                book.setTitle(newTitle);
                book.setAuthor(newAuthor);
                book.setIsbn(newIsbn);
                bookTableModel.fireTableDataChanged();
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a book to edit.");
        }
    }

    private void editMember() {
        int selectedRow = memberTable.getSelectedRow();
        if (selectedRow >= 0) {
            Member member = memberManager.getMembers().get(selectedRow);
            String newFirstName = JOptionPane.showInputDialog(frame, "Edit member first name:", member.getFirstName());
            String newLastName = JOptionPane.showInputDialog(frame, "Edit member last name:", member.getLastName());
            String newMemberId = JOptionPane.showInputDialog(frame, "Edit member ID:", member.getMemberId());
            if (newFirstName != null && newLastName != null && newMemberId != null) {
                member.setFirstName(newFirstName);
                member.setLastName(newLastName);
                member.setMemberId(newMemberId);
                memberTableModel.fireTableDataChanged();
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a member to edit.");
        }
    }

    private void editLoan() {
        int selectedRow = loanTable.getSelectedRow();
        if (selectedRow >= 0) {
            Loan loan = loanManager.getLoans().get(selectedRow);
            LocalDate newLoanDate = LocalDate.parse(JOptionPane.showInputDialog(frame, "Edit loan date (YYYY-MM-DD):", loan.getLoanDate().toString()));
            LocalDate newReturnDate = LocalDate.parse(JOptionPane.showInputDialog(frame, "Edit return date (YYYY-MM-DD):", loan.getReturnDate().toString()));
            if (newLoanDate != null && newReturnDate != null) {
                loan.setLoanDate(newLoanDate);
                loan.setReturnDate(newReturnDate);
                loanTableModel.fireTableDataChanged();
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a loan to edit.");
        }
    }

    private void search(TableRowSorter<?> sorter, JTextField searchField) {
        String text = searchField.getText();
        if (text.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LibraryApp();
            }
        });
    }
}

class BookTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Title", "Author", "ISBN"};
    private final List<Book> books;

    public BookTableModel(List<Book> books) {
        this.books = books;
    }

    @Override
    public int getRowCount() {
        return books.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Book book = books.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return book.getTitle();
            case 1:
                return book.getAuthor();
            case 2:
                return book.getIsbn();
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}

class MemberTableModel extends AbstractTableModel {
    private final String[] columnNames = {"First Name", "Last Name", "Member ID"};
    private final List<Member> members;

    public MemberTableModel(List<Member> members) {
        this.members = members;
    }

    @Override
    public int getRowCount() {
        return members.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Member member = members.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return member.getFirstName();
            case 1:
                return member.getLastName();
            case 2:
                return member.getMemberId();
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}

class LoanTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Book Title", "Member Name", "Loan Date", "Return Date", "Remaining Days"};
    private final List<Loan> loans;

    public LoanTableModel(List<Loan> loans) {
        this.loans = loans;
    }

    @Override
    public int getRowCount() {
        return loans.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Loan loan = loans.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return loan.getBook().getTitle();
            case 1:
                return loan.getMember().getFirstName() + " " + loan.getMember().getLastName();
            case 2:
                return loan.getLoanDate();
            case 3:
                return loan.getReturnDate();
            case 4:
                return java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), loan.getReturnDate());
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
} 