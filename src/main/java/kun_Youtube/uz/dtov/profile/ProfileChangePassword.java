package kun_Youtube.uz.dtov.profile;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileChangePassword {

    @NotNull(message = "username Enter")
    private String username;
    @NotBlank(message = "Old PAssword Enter")
    private String oldPassword;
    @NotBlank(message = "New password")
    private String newPassword;

}
