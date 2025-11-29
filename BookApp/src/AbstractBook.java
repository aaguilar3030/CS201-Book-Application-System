import java.util.ArrayList;
import java.util.List;
//CS 201 Project
public abstract class AbstractBook implements Book {
    protected String title;
    protected String author;
    protected String genre;
    protected double cost;

    // Store book details
    protected static final List<AbstractBook> allBooks = new ArrayList<>();

    public AbstractBook(String title, String author, String genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;

        // Keeps last six books
        allBooks.add(this);

    }

    // Abstract method
    public abstract void storeBookDetails();

    // Getter
    public double getCost() {
        return cost;
    }

    // Interface methods
    @Override
    public double getTotalCost() {
        double total = 0;
        for (AbstractBook b : allBooks) {
            total += b.getCost();
        }
        return total;
    }

    @Override
    public void getLastSixBooks() {
        System.out.println("\n--- Last Six Books Added ---");

        int start = Math.max(0, allBooks.size() - 6);
        for (int i = start; i < allBooks.size(); i++)  {
            allBooks.get(i).storeBookDetails();
            System.out.println();
        }
    }

    @Override
    public int getNumberOfBooksGenre() {
        int count = 0;
        for (AbstractBook b : allBooks) {
            if (b.genre.equalsIgnoreCase(this.genre)) {
                count++;
            }
        }
        return count;
    }
}
