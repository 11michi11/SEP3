package model;

import org.apache.lucene.document.Document;
import org.hibernate.search.bridge.LuceneOptions;
import org.hibernate.search.bridge.TwoWayFieldBridge;

public class BookBridge implements TwoWayFieldBridge {


    @Override
    public Object get(String s, Document document) {
        System.out.println("Get: " + s);
        String isbn = document.get(s + ".isbn");
        String title = document.get(s + ".title");
        String author = document.get(s + ".author");
        int year = Integer.parseInt(document.get(s + ".year"));
        Book.Category category = Book.Category.valueOf(document.get(s + ".category"));
        return new Book(isbn, title, author, year, category);
    }

    @Override
    public String objectToString(Object o) {
        Book book = (Book) o;
        return book.toString();
    }

    @Override
    public void set(String s, Object o, Document document, LuceneOptions luceneOptions) {
        System.out.println("Set: " + s);
        Book book = (Book) o;
        luceneOptions.addFieldToDocument(s+".isbn",book.getIsbn(), document);
        luceneOptions.addFieldToDocument(s+".title",book.getTitle(), document);
        luceneOptions.addFieldToDocument(s+".author",book.getAuthor(), document);
        luceneOptions.addFieldToDocument(s+".year", String.valueOf(book.getYear()), document);
        luceneOptions.addFieldToDocument(s+".category",book.getCategory().name(), document);
    }
}
