
package dto;

import entities.User;

public class UserDTO {
    
    private String username;
    private String userPass;
    private String passwordCheck;
    private String fullName;
    private int age;
    private double weight;
    

    public UserDTO(User user) {
        this.username = user.getUserName();
        this.userPass = user.getUserPass();
        this.fullName = user.getFullName();
        this.age = user.getAge();
        this.weight = user.getWeight();
    }

    public UserDTO(String username, String userPass, String passwordCheck, String fullName, int age, double weight) {
        this.username = username;
        this.userPass = userPass;
        this.passwordCheck = passwordCheck;
        this.fullName = fullName;
        this.age = age;
        this.weight = weight;
    }
    
    

    public String getUsername() {
        return username;
    }

    public String getUserPass() {
        return userPass;
    }

    public String getFullName() {
        return fullName;
    }

    public int getAge() {
        return age;
    }

    public double getWeight() {
        return weight;
    }

    public String getPasswordCheck() {
        return passwordCheck;
    }
    
    
    
}
