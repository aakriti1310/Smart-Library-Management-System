import java.util.*;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final Library library = new Library();
    private static final LibraryGraph categoryGraph = new LibraryGraph();

    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("       SMART LIBRARY MANAGEMENT SYSTEM");
        System.out.println("==============================================");
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1 -> addBook();
                case 2 -> viewBooks();
                case 3 -> searchBook();
                case 4 -> issueBook();
                case 5 -> returnBook();
                case 6 -> removeBook();
                case 7 -> categoryAlgorithms();
                case 8 -> AlgorithmDemo.run();
                case 0 -> { running = false; System.out.println("Thank you for using Smart Library!"); }
                default -> System.out.println("Invalid choice. Please select from the menu.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n--------------- MAIN MENU ----------------");
        System.out.println("1. Add a new book");
        System.out.println("2. Display all books (AVL sorted order)");
        System.out.println("3. Search book by ID or title");
        System.out.println("4. Issue a book");
        System.out.println("5. Return a book");
        System.out.println("6. Remove a book");
        System.out.println("7. Category graph: BFS / DFS / Dijkstra");
        System.out.println("8. Greedy and Dynamic Programming demos");
        System.out.println("0. Exit");
        System.out.println("-------------------------------------------");
    }

    private static void addBook() {
        int id = readInt("Book ID: ");
        System.out.print("Title: "); String title = sc.nextLine().trim();
        System.out.print("Author: "); String author = sc.nextLine().trim();
        System.out.print("Category: "); String category = sc.nextLine().trim();
        if (title.isEmpty() || author.isEmpty() || category.isEmpty()) {
            System.out.println("Fields cannot be empty."); return;
        }
        if (library.addBook(new Book(id, title, author, category)))
            System.out.println("Book added successfully.");
        else System.out.println("A book with that ID already exists.");
    }

    private static void viewBooks() {
        List<Book> books = library.getAllBooks();
        if (books.isEmpty()) { System.out.println("No books in the library."); return; }
        printHeader();
        for (Book b : books) System.out.println(b);
    }

    private static void searchBook() {
        System.out.println("1. Search by ID   2. Search by title");
        int option = readInt("Choose: ");
        if (option == 1) {
            Book b = library.findBook(readInt("Enter book ID: "));
            if (b == null) System.out.println("Book not found.");
            else { printHeader(); System.out.println(b); }
        } else if (option == 2) {
            System.out.print("Enter title or part of title: ");
            List<Book> found = library.searchByTitle(sc.nextLine().trim());
            if (found.isEmpty()) System.out.println("No matching books found.");
            else { printHeader(); for (Book b : found) System.out.println(b); }
        } else System.out.println("Invalid option.");
    }

    private static void issueBook() {
        int id = readInt("Enter book ID to issue: ");
        System.out.println(library.issueBook(id) ? "Book issued successfully." :
                "Unable to issue. Check the ID or book availability.");
    }

    private static void returnBook() {
        int id = readInt("Enter book ID to return: ");
        System.out.println(library.returnBook(id) ? "Book returned successfully." :
                "Unable to return. Check the ID or issue status.");
    }

    private static void removeBook() {
        int id = readInt("Enter book ID to remove: ");
        System.out.println(library.removeBook(id) ? "Book removed successfully." :
                "Unable to remove. Book may not exist or may currently be issued.");
    }

    private static void categoryAlgorithms() {
        System.out.println("\nCategories: Fiction, Mystery, Romance, Technology, Education, Biography");
        System.out.print("Enter starting category exactly as shown: ");
        String start = sc.nextLine().trim();
        System.out.println("1. BFS  2. DFS  3. Dijkstra shortest paths");
        int option = readInt("Choose: ");
        switch (option) {
            case 1 -> { System.out.print("BFS traversal: "); categoryGraph.breadthFirst(start); }
            case 2 -> { System.out.print("DFS traversal: "); categoryGraph.depthFirst(start); }
            case 3 -> categoryGraph.shortestPaths(start);
            default -> System.out.println("Invalid option.");
        }
    }

    private static void printHeader() {
        System.out.printf("%-6s %-25s %-20s %-15s %-12s%n", "ID", "TITLE", "AUTHOR", "CATEGORY", "STATUS");
        System.out.println("--------------------------------------------------------------------------------");
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextInt()) { int value = sc.nextInt(); sc.nextLine(); return value; }
            sc.nextLine();
            System.out.println("Please enter a valid whole number.");
        }
    }
}
