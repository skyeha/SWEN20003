public class Book {
    //Basic info of book
    public String author;
    public String bookTitle;
    public boolean isBorrowed; // a boolean to check if book is borrowed
    public String borrowedBy;

    //private static int numBorrowed = 0;

    // Question 3b
    public Book(String author, String bookTitle, boolean isBorrowed) {
        this.author = author;
        this.bookTitle = bookTitle;
        this.isBorrowed = isBorrowed;
    }

    // Question 3c: setter and getter
    public String getAuthor() {
        return author;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void borrowStatus() {
        if (!isBorrowed) {
            System.out.println("This book is available.");
        } else {
            System.out.println("This book is currently being borrowed.");
        }
    }

    // Question 3d: toString() and equals()
    public String toString() {
        return author + ": " + bookTitle;
    }

    public boolean equalsTitle(String bookTitle) {
        if (this.bookTitle.equalsIgnoreCase(bookTitle)) {
            return true;
        } else { return false;}
    }

    public boolean equalsAuthor(String author) {
        if (this.author.equalsIgnoreCase(author)) {
            return true;
        } else { return false;}
    }
    // Question 3f:
    public void borrow(String borrowedBy) {
        this.isBorrowed = true;
        this.borrowedBy = borrowedBy;
        //numBorrowed++;
    }

    // Question 3g:
    public void returnBook() {
        this.isBorrowed = false;
        this.borrowedBy = "";
        //numBorrowed--;
    }

    // Question 3h:
   /* public static int currBorrowed() {
        return numBorrowed;
    }
    */
}
