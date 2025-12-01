package main.java.com.hotel.model;

public class User {
    private int id;
    private String username;
    private String password;
    private String fullName;
    private String role; // ADMIN or CUSTOMER

    // constructors, getters, setters
    public User() {}
    public User(int id, String username, String fullName, String role){
        this.id = id; this.username = username; this.fullName = fullName; this.role = role;
    }
    // getters & setters...
}
