package model;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Indexed
@Table(name = "library", schema = "public")
public class Library implements Institution {

    @Id @Column(name = "libraryid")
    @Field(name = "library")
    private String libraryID;

    @Field
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
