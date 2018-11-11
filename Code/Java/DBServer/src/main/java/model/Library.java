package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "library", schema = "public")
public class Library {

    @Id @Column(name = "libraryid")
    private String libraryID;

    public Library(){
        //change
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
}
