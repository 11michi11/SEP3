package model;

public class Library implements Institution {

    private String libraryID;

    public Library(String libraryID){
        this.libraryID = libraryID;
    }


    public String getLibraryID() {
        return libraryID;
    }
}
