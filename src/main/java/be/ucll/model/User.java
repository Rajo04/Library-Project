package be.ucll.model;

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
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
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
        if(password.length() < 8){
            throw new IllegalArgumentException("Password must be at least 8 characters long.");
        }
        this.password = password;
    }
}