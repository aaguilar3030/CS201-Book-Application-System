public class AudioBook extends AbstractBook {
    // in hours
    private double length;

    private static double totalLength = 0;
    private static int totalAudioBooks = 0;
    private static double totalAudioCost = 0;

    public AudioBook(String title, String author, String genre, double length) {
        super(title, author, genre);
        this.length = length;

        // compute and store cost
        this.cost = length * 15.0;

        totalLength += length;
        totalAudioBooks++;
        totalAudioCost += this.cost;
    }

    public double getLength() {
        return length;
    }

    public static double getAverageLength() {
        return totalAudioBooks == 0 ? 0 : totalLength / totalAudioBooks;
    }

    public static double getTotalAudioCost() {
        return totalAudioCost;
    }

    @Override
    public void storeBookDetails() {
        System.out.println(" Audio Book Details:");
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Genre: " + genre);
        System.out.println("Length: " + length + " hours");
        System.out.println("Cost: $" + getCost());
    }
}
