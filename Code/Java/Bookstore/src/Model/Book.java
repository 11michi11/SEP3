package Model;

public class Book {
	private String title;
	private String author;
	private int year;
	private String isbn;
	private Category category;
	
	public Book(String title, String author, int year, String isbn, Category category) {
		this.title = title;
		this.author = author;
		this.year = year;
		this.isbn = isbn;
		this.category = category;
	}
	
	
	
}
