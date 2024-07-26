package be.ucll.model;

import static be.ucll.util.Validation.*;

public class User {
    private String name;
    private int age;
    private String email;
    private String password;
    
    
    public User(String name, int age, String email, String password){
        setName(name);
        setAge(age);
        setEmail(email);
        setPassword(password);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        validateNonEmptyString(name, "Name is required.");
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        validateAge(age, "Age must be a positive integer between 0 and 101.");
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        validateEmail(email, "E-mail must be a valid email format");
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        validatePassword(password, "Password must be at least 8 characters long.");
        this.password = password;
    }
}