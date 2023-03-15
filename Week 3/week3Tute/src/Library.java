public class Library {
    private final int maxBook = 10;
    private static int numBooks = 0;

    public int numBorrowed = 0;
    public Book[] bookShelf;

    public Library() {
        bookShelf = new Book[maxBook - 1];
        numBorrowed = 0;
    }

    // Add book to the library
    public void addBook(String author, String bookTitle) {
        if (numBooks > maxBook) {
            System.out.println("Maximum number of books reached! Can't store more.");
        }
        bookShelf[numBooks] = new Book(author, bookTitle, false);
        numBooks++;
    }

    // Book lookup
    public Book lookup(String bookTitle) {
        for (int i = 0; i < numBooks; i++) {
            if (bookShelf[i].equalsTitle(bookTitle)) {
                return bookShelf[i];
            }
        }
        return null;
    }

    public Book lookup(String author, String bookTitle) {
        for (int i = 0; i < numBooks; i++) {
            if (bookShelf[i].equalsTitle(bookTitle) && bookShelf[i].equalsAuthor(author)) {
                return bookShelf[i];
            }
        }
        return null;
    }

    public String getCatalogue() {
        String result = "";
        for (int i = 0; i < numBooks; i++) {
            System.out.println(bookShelf[i]);
            result += bookShelf[i].toString() + "\n";
        }
        return result;
    }

    public void borrowBook(String author, String bookTitle) {
        Book book = this.lookup(author, bookTitle);
        if (book != null && !(book.isBorrowed)) {
            book.isBorrowed = true;
            numBorrowed++;
            System.out.println("Successfully borrow the book");
        } else {
            System.out.println("Book is not found!");
        }
    }
}
