package model;

import javax.persistence.*;

@Entity
@Table(name = "libraryadmin", schema = "public")
public class LibraryAdmin implements User, Admin{

    @Id @Column(name = "adminid")
    private String adminId;

    @ManyToOne
    @JoinColumn(name = "libraryid")
    private Library library;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "serverUrl")
    private String serverUrl;

    public LibraryAdmin() {
    }

    public LibraryAdmin(String adminId, Library library, String name, String email, String password, String serverUrl) {
        this.adminId = adminId;
        this.library = library;
        this.name = name;
        this.email = email;
        this.password = password;
        this.serverUrl = serverUrl;
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

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getUserId() {
        return adminId;
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
    public String getInstitutionId() {
        return library.getLibraryID();
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    @Override
    public String toString() {
        return "LibraryAdmin{" +
                "adminId='" + adminId + '\'' +
                ", library=" + library +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", serverUrl='" + serverUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LibraryAdmin that = (LibraryAdmin) o;

        if (adminId != null ? !adminId.equals(that.adminId) : that.adminId != null) return false;
        if (library != null ? !library.equals(that.library) : that.library != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        return serverUrl != null ? serverUrl.equals(that.serverUrl) : that.serverUrl == null;
    }

    @Override
    public int hashCode() {
        int result = adminId != null ? adminId.hashCode() : 0;
        result = 31 * result + (library != null ? library.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (serverUrl != null ? serverUrl.hashCode() : 0);
        return result;
    }
}
