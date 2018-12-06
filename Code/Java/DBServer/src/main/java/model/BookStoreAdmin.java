package model;

import javax.persistence.*;

@Entity
@Table(name = "bookstoreadmin", schema = "public")
public class BookStoreAdmin implements User {

	@Id
	@Column(name = "adminid")
	private String adminId;

	@ManyToOne
	@JoinColumn(name = "bookstoreid")
	private BookStore bookstore;

	@Column(name = "name")
	private String name;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	public BookStoreAdmin() {}

	public BookStoreAdmin(String adminId, BookStore bookstore, String name, String email, String password) {
		this.adminId = adminId;
		this.bookstore = bookstore;
		this.name = name;
		this.email = email;
		this.password = password;
	}

	@Override
	public boolean authenticate(String password) {
		return this.password.equals(password);
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public BookStore getBookstore() {
		return bookstore;
	}

	public void setBookstore(BookStore bookstore) {
		this.bookstore = bookstore;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "BookStoreAdmin{" +
				"adminId='" + adminId + '\'' +
				", bookstore=" + bookstore +
				", name='" + name + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BookStoreAdmin)) return false;

		BookStoreAdmin that = (BookStoreAdmin) o;

		if (adminId != null ? !adminId.equals(that.adminId) : that.adminId != null) return false;
		if (bookstore != null ? !bookstore.equals(that.bookstore) : that.bookstore != null) return false;
		if (name != null ? !name.equals(that.name) : that.name != null) return false;
		if (email != null ? !email.equals(that.email) : that.email != null) return false;
		return password != null ? password.equals(that.password) : that.password == null;
	}

	@Override
	public int hashCode() {
		int result = adminId != null ? adminId.hashCode() : 0;
		result = 31 * result + (bookstore != null ? bookstore.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (email != null ? email.hashCode() : 0);
		result = 31 * result + (password != null ? password.hashCode() : 0);
		return result;
	}
}
