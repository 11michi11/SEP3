package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "library", schema = "public")
public class Library implements Institution {

    @Id @Column(name = "libraryid")
    private String libraryID;

    @Column(name = "name")
    private String name;

    public Library(){
    }

    public Library(String libraryID) {
        this.libraryID = libraryID;
        this.name = "First Library";
    }

    public String getLibraryID() {
        return libraryID;
    }

    public void setLibraryID(String libraryID) {
        this.libraryID = libraryID;
    }

    @Override
    public String toString() {
        return "Library{" +
                "libraryID='" + libraryID + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Library library = (Library) o;

        return libraryID != null ? libraryID.equals(library.libraryID) : library.libraryID == null;
    }

    @Override
    public int hashCode() {
        return libraryID != null ? libraryID.hashCode() : 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
