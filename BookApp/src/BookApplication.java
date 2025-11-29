import java.io.*;
import java.util.Scanner;


public class BookApplication {

    private static final String FILE_NAME = "books.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        // load books from file
        loadBooksFromFile();

        System.out.println("=====================================");
        System.out.println("Book Application");
        System.out.println("=====================================");

        while (running) {
            System.out.println("\n--- MENU ---");
            System.out.println("0. Import Books From File");
            System.out.println("1. Add Printed Book");
            System.out.println("2. Add Audio Book");
            System.out.println("3. View Last Six Books");
            System.out.println("4. View Totals and Averages");
            System.out.println("5. View Genre's");
            System.out.println("6. View Last Three Audio Books");
            System.out.println("7. View Full Book List");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            String input = sc.nextLine();

            switch (input) {

                //Import Books
                case "0":
                    loadBooksFromFile();
                    break;


                // add printed
                case "1":
                    System.out.print("Enter title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter author: ");
                    String author = sc.nextLine();
                    System.out.print("Enter genre: ");
                    String genre = sc.nextLine();
                    System.out.print("Enter number of pages: ");
                    int pages = Integer.parseInt(sc.nextLine());
                    new PrintedBook(title, author, genre, pages);
                    System.out.println("Printed book added successfully!");

                    saveBooksToFile();
                    break;

                // add audio
                case "2":
                    System.out.print("Enter title: ");
                    title = sc.nextLine();
                    System.out.print("Enter author: ");
                    author = sc.nextLine();
                    System.out.print("Enter genre: ");
                    genre = sc.nextLine();
                    System.out.print("Enter length (hours): ");
                    double length = Double.parseDouble(sc.nextLine());
                    new AudioBook(title, author, genre, length);
                    System.out.println("Audio book added successfully!");

                    saveBooksToFile();
                    break;

                // view last six books
                case "3":
                    if (AbstractBook.allBooks.isEmpty()) {
                        System.out.println("No books have been added yet.");
                    } else {
                        AbstractBook.allBooks
                                .get(AbstractBook.allBooks.size() - 1)
                                .getLastSixBooks();
                    }
                    break;

                // totals and averages
                case "4":
                    System.out.println("\n--- Totals and Averages ---");
                    System.out.printf(" Total Printed Book Cost: $%.2f%n", PrintedBook.getTotalPrintedCost());
                    System.out.printf(" Average Pages: %.2f%n", PrintedBook.getAverageNumberOfPages());
                    System.out.printf(" Total Audio Book Cost: $%.2f%n", AudioBook.getTotalAudioCost());
                    System.out.printf(" Average Length: %.2f hours%n", AudioBook.getAverageLength());
                    double totalCost = PrintedBook.getTotalPrintedCost() + AudioBook.getTotalAudioCost();
                    System.out.printf(" Combined Total Cost: $%.2f%n", totalCost);
                    break;

                // view genres
                case "5":
                    if (AbstractBook.allBooks.isEmpty()) {
                        System.out.println("No Genre's have been added yet.");
                    } else {
                        System.out.println("\n--- Genres and Counts ---");

                        String[] genres = new String[AbstractBook.allBooks.size()];
                        int[] counts = new int[AbstractBook.allBooks.size()];
                        int uniqueCount = 0;

                        for (int i = 0; i < AbstractBook.allBooks.size(); i++) {
                            AbstractBook b = AbstractBook.allBooks.get(i);
                            String g = b.genre.toLowerCase();
                            boolean found = false;

                            for (int j = 0; j < uniqueCount; j++) {
                                if (genres[j].equals(g)) {
                                    counts[j]++;
                                    found = true;
                                    break;
                                }
                            }

                            if (!found) {
                                genres[uniqueCount] = g;
                                counts[uniqueCount] = 1;
                                uniqueCount++;
                            }
                        }

                        for (int i = 0; i < uniqueCount; i++) {
                            System.out.println("Genre: " + genres[i] + " | Count: " + counts[i]);
                        }
                    }
                    break;

                // view last three audio
                case "6":
                    if (AbstractBook.allBooks.isEmpty()) {
                        System.out.println("No audio books have been added.");
                    } else {
                        System.out.println("\n--- Last Three Audio Books ---");
                        int count = 0;

                        for (int i = AbstractBook.allBooks.size() - 1;
                        i >= 0 && count < 3;
                        i--) {

                            AbstractBook b = AbstractBook.allBooks.get(i);

                            if (b.getClass().getSimpleName().equals("AudioBook")) {
                                b.storeBookDetails();
                                System.out.println();
                                count++;
                            }
                        }

                        if (count == 0) {
                            System.out.println("There are audio books in list.");
                        }
                    }
                    break;


                // view list
                case "7":
                    viewFullBookList();
                    break;

                // Exit
                case "8":
                    System.out.println("Exiting Book Application... Goodbye! ");
                    running = false;
                    break;


                default: System.out.println("Invalid option. Please choose 1–7.");
            }
        }

        sc.close();
    }

    // load books form txt file
    private static void loadBooksFromFile() {
        AbstractBook.allBooks.clear();

        try(BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            int imported = 0;

            br.readLine();

            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;

                String[] parts = line.split(",");


                if (parts.length < 7) continue;

                String title = clean(parts[0]);
                String author = clean(parts[1]);
                String genre = clean(parts[2]);

                String lengthStr = clean(parts[4]);
                String pagesStr = clean(parts[5]);
                String type = clean(parts[6]).toLowerCase();

                if (type.equals("printedbook")) {
                    int pages = Integer.parseInt(pagesStr);
                    new PrintedBook(title, author, genre, pages);
                    imported++;
                }
                else if (type.equals("audiobook")) {
                    double length = Double.parseDouble(lengthStr);
                    new AudioBook(title, author, genre, length);
                    imported++;
                }
            }

            System.out.println("Imported " + imported + " books from " + FILE_NAME);

        } catch (FileNotFoundException e) {
            System.out.println(FILE_NAME + " not found.");
        } catch (IOException e) {
            System.out.println("Error Reading " + FILE_NAME + ": " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("File has bad number (pages/length). CHECK DATA!");
        }
    }

    private static String clean(String s) {
        return s.replace("\"","").trim();
    }

    // Save books to txt file
    private static void saveBooksToFile() {

        if (AbstractBook.allBooks.isEmpty()) {
            System.out.println("No books to save. File not modified.");
            return;
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {

            pw.println("title,author,genre,cost,length,pages,booktype");

            for (AbstractBook b : AbstractBook.allBooks) {

                if (b.getClass().getSimpleName().equals("PrintedBook")) {
                    PrintedBook pb = (PrintedBook) b;

                    pw.println("\"" + pb.title + "\"," +
                            "\"" + pb.author + "\"," +
                            "\"" + pb.genre + "\"," +
                            pb.getCost() + "," +
                            "\"N/A\"," +
                            pb.getNumberOfPages() + "," +
                            "printedBook");

                } else if (b.getClass().getSimpleName().equals("AudioBook")) {
                    AudioBook ab = (AudioBook) b;

                    pw.println("\"" + ab.title + "\"," +
                            "\"" + ab.author + "\"," +
                            "\"" + ab.genre + "\"," +
                            ab.getCost() + ","
                            + ab.getLength() + "," +
                            "\"N/A\"," + "audiobook");
                }
            }

            System.out.println("File saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving to " + FILE_NAME + ": " + e.getMessage());
        }
    }

    // view last three audio books
    private static void viewLastThreeAudioBooks() {
        if (AbstractBook.allBooks.isEmpty()) {
            System.out.println("No Audio Books have been added.");
            return;
        }

        System.out.println("\n--- Last Three Audio Books ---");
        int count = 0;

        for (int i = AbstractBook.allBooks.size()- 1;
        i >= 0 && count < 3;
        i--) {
            AbstractBook b = AbstractBook.allBooks.get(i);

            if (b.getClass().getSimpleName().equals("AudioBook")) {
                b.storeBookDetails();
                System.out.println();
                count++;
            }
        }

        if (count == 0) {
         System.out.println("There are no Audio Books in the list.");
        }
    }

    // view full list
    private static void viewFullBookList() {
        if (AbstractBook.allBooks.isEmpty()) {
            System.out.println("No books in list.");
            return;
        }

        System.out.println("\n--- Full Book List ---");

        for (int i = 0; i < AbstractBook.allBooks.size(); i++) {
            System.out.println("Book #" + (i + 1));
            AbstractBook.allBooks.get(i).storeBookDetails();
            System.out.println();
        }
    }
}

