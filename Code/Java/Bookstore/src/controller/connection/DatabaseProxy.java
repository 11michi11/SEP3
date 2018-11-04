package Controller.Connection;

import Model.Category;

public interface DatabaseProxy {
	public String search(String searchTerm);
	public String advancedSearch(String title, String author, int year, String isbn, Category category);

}
