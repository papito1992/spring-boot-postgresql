package com.alpaka.dtos.request;

import javax.validation.constraints.*;
import java.util.Set;

public class SignupRequest {
    @NotBlank(message = "username is mandatory")
    @Size(min = 3, max = 20)
    private String username;

    private Set<String> role;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
  
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Set<String> getRole() {
      return this.role;
    }
    
    public void setRole(Set<String> role) {
      this.role = role;
    }

    @Override
    public String toString() {
        return "SignupRequest{" +
                "username='" + username + '\'' +
                "password='" + password + '\'' +
                '}';
    }
}
