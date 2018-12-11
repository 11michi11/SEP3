package model;

public interface User {

    boolean authenticate(String password);

    String getName();
    String getUserId();
}