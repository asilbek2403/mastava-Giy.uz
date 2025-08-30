package kun_Youtube.uz.dtov.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationDto { // TODO write validation
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Surname is required")
    private String surname;
    @NotBlank(message = "Username (email) is required")
    @Email(message = "Email format is invalid")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;

}