public class Book {
    private final int id;
    private String title;
    private String author;
    private String category;
    private boolean available;

    public Book(int id, String title, String author, String category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.available = true;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
        return String.format("%-6d %-25s %-20s %-15s %-12s",
                id, title, author, category, available ? "Available" : "Issued");
    }
}
