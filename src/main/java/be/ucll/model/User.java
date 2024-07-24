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
        if(name == " " | name == null){
            throw new DomainException("Name is required.");
        }
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        if(age < 0 | age > 101) {
            throw new DomainException("Age must be a positive integer between 0 and 101.");
        }
        this.age = age;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        if(!email.contains("@") || !email.contains(".")) {
            throw new DomainException("E-mail must be a valid email format");
        }
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        if(password.length() < 8){
            throw new DomainException("Password must be at least 8 characters long.");
        }
        this.password = password;
    }
}