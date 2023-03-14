public class Book {
    //Basic info of book
    public String author;
    public String bookTitle;
    public boolean isBorrowed; // a boolean to check if book is borrowed

    // Question 3b
    public void Book(String author, String bookTitle, boolean isBorrowed) {
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
        if (isBorrowed) {
            System.out.println("This book is available.");
        } else {
            System.out.println("This book is currently being borrowed.");
        }
    }

    // Question 3d: toString() and equals()
}
