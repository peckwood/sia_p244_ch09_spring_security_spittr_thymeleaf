package spittr;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Email;

public class Spitter {

  private Long id;
  
  @NotNull
  //min and max will replace the placeholders {min} and {max} in ValidationMessages.properties
  @Size(min=5, max=16, message="{username.size}")
  private String username;

  @NotNull
  @Size(min=5, max=25, message="{password.size}")
  private String password;
  
  @NotNull
  @Size(min=2, max=30, message="{firstName.size}")
  private String firstName;

  @NotNull
  @Size(min=2, max=30, message="{lastName.size}")
  private String lastName;
  
  @NotNull
  @Email(message="{email.valid}")
  private String email;

  @NotNull
  private Boolean enabled;
  
  @NotNull
  private String role;
  
  public Spitter() {}
  
  public Spitter( String username, String password, String firstName, String lastName, String email,
		Boolean enabled, String role) {
	  this(null, username, password, firstName, lastName, email, enabled, role);
}

  public Spitter(Long id, String username, String password, String firstName, String lastName, String email,
		Boolean enabled, String role) {
	this.id = id;
	this.username = username;
	this.password = password;
	this.firstName = firstName;
	this.lastName = lastName;
	this.email = email;
	this.enabled = enabled;
	this.role = role;
}

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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  
  public String getEmail() {
    return email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public boolean equals(Object that) {
    return EqualsBuilder.reflectionEquals(this, that, "firstName", "lastName", "username", "password", "email");
  }
  
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, "firstName", "lastName", "username", "password", "email");
  }

public Boolean getEnabled() {
	return enabled;
}

public void setEnabled(Boolean enabled) {
	this.enabled = enabled;
}

public String getRole() {
	return role;
}

public void setRole(String role) {
	this.role = role;
}

@Override
public String toString() {
	return "Spitter [id=" + id + ", username=" + username + ", password=" + password + ", firstName=" + firstName
			+ ", lastName=" + lastName + ", email=" + email + ", enabled=" + enabled + ", role=" + role + "]";
}

}
