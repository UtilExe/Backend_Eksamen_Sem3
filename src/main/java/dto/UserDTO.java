
package dto;

import entities.User;

public class UserDTO {
    
    private String username;

    public UserDTO(User user) {
        this.username = user.getUserName();
    }

    public String getUsername() {
        return username;
    }
    
    
}
