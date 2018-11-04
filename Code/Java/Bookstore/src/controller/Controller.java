package Controller;

import Controller.Connection.DatabaseProxy;
import Model.Category;

public class Controller {
	private Controller instance;
	private DatabaseProxy db;
	
	private Controller() {
		
	}
	public Controller getInstance() {
		if(instance==null)
			instance=new Controller();
		return instance;
	}
	public String search(String searhTerm) {
		return null;
	}
	public String advancedSearch(String title, String author, int year, String isbn, Category category) {
		return null;
	}
}
