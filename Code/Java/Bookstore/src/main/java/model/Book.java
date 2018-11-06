package model;

import com.google.gson.Gson;

public class Book {

	private String isbn;
	private String title;
	private String author;
	private int year;
	private Category category;

	public enum Category {Fantasy, SciFi, Criminal, Science, Drama, Children, Horror, Empty, Poetry}

	public Book(String isbn, String title, String author, int year, Category category) {
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.year = year;
		this.category = category;
	}

	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Book book = (Book) o;

		if (year != book.year) return false;
		if (isbn != null ? !isbn.equals(book.isbn) : book.isbn != null) return false;
		if (title != null ? !title.equals(book.title) : book.title != null) return false;
		if (author != null ? !author.equals(book.author) : book.author != null) return false;
		return category == book.category;
	}

	@Override
	public int hashCode() {
		int result = isbn != null ? isbn.hashCode() : 0;
		result = 31 * result + (title != null ? title.hashCode() : 0);
		result = 31 * result + (author != null ? author.hashCode() : 0);
		result = 31 * result + year;
		result = 31 * result + (category != null ? category.hashCode() : 0);
		return result;
	}
}
