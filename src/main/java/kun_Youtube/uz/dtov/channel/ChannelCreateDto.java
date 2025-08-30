package kun_Youtube.uz.dtov.channel;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChannelCreateDto {

        private String id;
        @NotBlank(message = "Name is required")
        private String name;
        private String description;

        private String photoId;  // AttachEntity bilan bog'liq
        private String bannerId;// AttachEntity bilan bog'liq
        private Integer profileId;


}
