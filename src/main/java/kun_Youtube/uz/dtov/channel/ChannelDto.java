package kun_Youtube.uz.dtov.channel;


import jakarta.validation.constraints.NotBlank;
import kun_Youtube.uz.enumh.ChannelStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonInclude;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChannelDto {

    private String id;
    @NotBlank(message = "name is required")
    private String name;
    private String description;
    private String photoId;
    private String bannerId;
    private Integer profileId;
    private ChannelStatus status; // ACTIVE, BLOCKED, ...
    private LocalDateTime createdDate;

}
