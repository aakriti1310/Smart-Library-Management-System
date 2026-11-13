import java.util.ArrayList;
import java.util.List;

/** AVL tree indexing books by their unique ID. */
public class AVLTree {
    private static class Node {
        int key, height;
        Book book;
        Node left, right;
        Node(Book book) {
            this.key = book.getId();
            this.book = book;
            this.height = 1;
        }
    }

    private Node root;

    private int height(Node n) { return n == null ? 0 : n.height; }
    private int balance(Node n) { return n == null ? 0 : height(n.left) - height(n.right); }
    private void update(Node n) { n.height = 1 + Math.max(height(n.left), height(n.right)); }

    private Node rotateRight(Node y) {
        Node x = y.left, t = x.right;
        x.right = y; y.left = t;
        update(y); update(x);
        return x;
    }

    private Node rotateLeft(Node x) {
        Node y = x.right, t = y.left;
        y.left = x; x.right = t;
        update(x); update(y);
        return y;
    }

    public void insert(Book book) { root = insert(root, book); }

    private Node insert(Node node, Book book) {
        if (node == null) return new Node(book);
        if (book.getId() < node.key) node.left = insert(node.left, book);
        else if (book.getId() > node.key) node.right = insert(node.right, book);
        else { node.book = book; return node; }

        update(node);
        int b = balance(node);
        if (b > 1 && book.getId() < node.left.key) return rotateRight(node);
        if (b < -1 && book.getId() > node.right.key) return rotateLeft(node);
        if (b > 1 && book.getId() > node.left.key) {
            node.left = rotateLeft(node.left); return rotateRight(node);
        }
        if (b < -1 && book.getId() < node.right.key) {
            node.right = rotateRight(node.right); return rotateLeft(node);
        }
        return node;
    }

    public Book search(int id) {
        Node current = root;
        while (current != null) {
            if (id == current.key) return current.book;
            current = id < current.key ? current.left : current.right;
        }
        return null;
    }

    public List<Book> inOrder() {
        List<Book> result = new ArrayList<>();
        inOrder(root, result);
        return result;
    }

    private void inOrder(Node node, List<Book> result) {
        if (node == null) return;
        inOrder(node.left, result);
        result.add(node.book);
        inOrder(node.right, result);
    }

    public void delete(int id) { root = delete(root, id); }

    private Node delete(Node node, int id) {
        if (node == null) return null;
        if (id < node.key) node.left = delete(node.left, id);
        else if (id > node.key) node.right = delete(node.right, id);
        else {
            if (node.left == null || node.right == null) {
                node = (node.left != null) ? node.left : node.right;
            } else {
                Node successor = minValueNode(node.right);
                node.key = successor.key;
                node.book = successor.book;
                node.right = delete(node.right, successor.key);
            }
        }
        if (node == null) return null;
        update(node);
        int b = balance(node);
        if (b > 1 && balance(node.left) >= 0) return rotateRight(node);
        if (b > 1 && balance(node.left) < 0) {
            node.left = rotateLeft(node.left); return rotateRight(node);
        }
        if (b < -1 && balance(node.right) <= 0) return rotateLeft(node);
        if (b < -1 && balance(node.right) > 0) {
            node.right = rotateRight(node.right); return rotateLeft(node);
        }
        return node;
    }

    private Node minValueNode(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }
}
