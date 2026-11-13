import java.io.*;
import java.nio.file.*;
import java.util.*;

/** Coordinates book operations and persists data in a local CSV file. */
public class Library {
    private final Map<Integer, Book> books = new HashMap<>();
    private final AVLTree index = new AVLTree();
    private static final Path DATA_FILE = Paths.get("library-books.csv");

    public Library() { load(); }

    public boolean addBook(Book book) {
        if (books.containsKey(book.getId())) return false;
        books.put(book.getId(), book);
        index.insert(book);
        save();
        return true;
    }

    public Book findBook(int id) { return index.search(id); }

    public boolean removeBook(int id) {
        Book book = books.get(id);
        if (book == null || !book.isAvailable()) return false;
        books.remove(id);
        index.delete(id);
        save();
        return true;
    }

    public boolean issueBook(int id) {
        Book book = findBook(id);
        if (book == null || !book.isAvailable()) return false;
        book.setAvailable(false);
        save();
        return true;
    }

    public boolean returnBook(int id) {
        Book book = findBook(id);
        if (book == null || book.isAvailable()) return false;
        book.setAvailable(true);
        save();
        return true;
    }

    public List<Book> getAllBooks() { return index.inOrder(); }

    public List<Book> searchByTitle(String query) {
        List<Book> result = new ArrayList<>();
        for (Book b : books.values())
            if (b.getTitle().toLowerCase().contains(query.toLowerCase())) result.add(b);
        result.sort(Comparator.comparing(Book::getTitle, String.CASE_INSENSITIVE_ORDER));
        return result;
    }

    private void load() {
        if (!Files.exists(DATA_FILE)) {
            addSampleBooks();
            return;
        }
        try (BufferedReader reader = Files.newBufferedReader(DATA_FILE)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] p = line.split(",", -1);
                if (p.length != 5) continue;
                Book b = new Book(Integer.parseInt(p[0]), decode(p[1]), decode(p[2]), decode(p[3]));
                b.setAvailable(Boolean.parseBoolean(p[4]));
                books.put(b.getId(), b);
                index.insert(b);
            }
        } catch (Exception e) {
            System.out.println("Could not load saved data. Starting with sample records.");
            books.clear();
            addSampleBooks();
        }
    }

    private void addSampleBooks() {
        addBook(new Book(101, "The Alchemist", "Paulo Coelho", "Fiction"));
        addBook(new Book(102, "Clean Code", "Robert Martin", "Technology"));
        addBook(new Book(103, "Wings of Fire", "A. P. J. Abdul Kalam", "Biography"));
        addBook(new Book(104, "Data Structures", "Seymour Lipschutz", "Education"));
    }

    private String encode(String s) { return s.replace("%", "%25").replace(",", "%2C"); }
    private String decode(String s) { return s.replace("%2C", ",").replace("%25", "%"); }

    private void save() {
        try (BufferedWriter writer = Files.newBufferedWriter(DATA_FILE)) {
            for (Book b : books.values()) {
                writer.write(b.getId() + "," + encode(b.getTitle()) + "," + encode(b.getAuthor())
                        + "," + encode(b.getCategory()) + "," + b.isAvailable());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Warning: could not save library data: " + e.getMessage());
        }
    }
}
