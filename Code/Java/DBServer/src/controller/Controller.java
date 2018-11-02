package controller;

import model.Book;

import java.util.List;

public class Controller implements DBProxy {

    private DBProxy db;

    private Controller(DBProxy db){
        this.db = db;
    }


    public static void main(String[] args) {
        DBProxy db = new HibernateAdapter();
        Controller controller = new Controller(db);
        controller.getAllBooks().forEach(System.out::println);
        System.out.println("-----------------------");
        controller.advancedSearch("^&*(", "Lord", "&*()", 1954, Book.Category.Fantasy).forEach(System.out::println);
        System.out.println("-----------------------");
        controller.search("Fantasy").forEach(System.out::println);
        System.out.println("-----------------------");
        controller.search("Cay").forEach(System.out::println);
        System.out.println("-----------------------");
        controller.search("Java").forEach(System.out::println);
        System.out.println("-----------------------");
        controller.search("2014").forEach(System.out::println);
    }

    public List<Book> getAllBooks() {
        return db.getAllBooks();
    }

    public List<Book> search(String searchTerm) {
        final String emptyStringValue ="!@#$%^&*()"; //this value represents empty string for query so that it is not matched to any typical string value
        if(searchTerm.equals(""))
            searchTerm = emptyStringValue;

        int year;
        try {
            year = Integer.parseInt(searchTerm);
        }catch(NumberFormatException e){
            year = 0;
        }

        String cat = searchTerm.toLowerCase();
        cat = cat.substring(0, 1).toUpperCase() + cat.substring(1);
        Book.Category searchCategory;
        try{
           searchCategory = Book.Category.valueOf(cat);
        }catch(IllegalArgumentException e){
            searchCategory = Book.Category.Empty;
        }

        return db.advancedSearch(searchTerm, searchTerm, searchTerm, year, searchCategory);
    }

    @Override
    public List<Book> advancedSearch(String isbn, String title, String author, int year, Book.Category category) {
        final String emptyStringValue ="!@#$%^&*()"; //this value represents empty string for query so that it is not matched to any typical string value
        if(isbn.equals(""))
            isbn = emptyStringValue;

        if(title.equals(""))
            title = emptyStringValue;

        if(author.equals(""))
            author = emptyStringValue;

        return db.advancedSearch(isbn, title, author, year, category);
    }
}
