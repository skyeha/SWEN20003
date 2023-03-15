public class LibraryFunc {
    public static void main(String[] args) {
        Library unimelbLib = new Library();
        unimelbLib.addBook("Arthur", "Leywin");
        Book bookResult = unimelbLib.lookup("Arthur","Leywin");
        unimelbLib.bookShelf[0].borrowStatus();
        System.out.println(bookResult);
        unimelbLib.borrowBook("Arthur", "Leywin");
        unimelbLib.bookShelf[0].borrowStatus();
    }
}