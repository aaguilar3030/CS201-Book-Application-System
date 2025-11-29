public class PrintedBook extends AbstractBook {
    private int numberOfPages;
    private static int totalPages = 0;
    private static int totalPrintedBooks = 0;
    private static double totalPrintedCost = 0;

    public PrintedBook(String title, String author, String genre, int numberOfPages) {
        super(title, author, genre);
        this.numberOfPages = numberOfPages;

        // cost per page
        this.cost = numberOfPages * 10.0;

        totalPages += numberOfPages;
        totalPrintedBooks++;
        totalPrintedCost += this.cost;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public static double getAverageNumberOfPages() {
        return totalPrintedBooks == 0 ? 0 : (double) totalPages / totalPrintedBooks;
    }

    public static double getTotalPrintedCost() {
        return totalPrintedCost;
    }

    @Override
    public void storeBookDetails() {
        System.out.println("Printed Book Details:");
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Genre: " + genre);
        System.out.println("Pages: " + numberOfPages);
        System.out.println("Cost: $" + getCost());
    }
}
