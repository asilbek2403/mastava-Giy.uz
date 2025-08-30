package kun_Youtube.uz.dtov.playList;


import jakarta.validation.constraints.NotBlank;
import kun_Youtube.uz.enumh.PlayStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayListUpdate {

    @NotBlank(message = "Name is required! ")
    private String name;
    private String description;

    @NotBlank(message = "Name is required! ")
    private PlayStatus status;

}
