package kun_Youtube.uz.dtov.playList;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kun_Youtube.uz.enumh.PlayStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class PlayListDto {

    private String id;
    @NotBlank(message = "****** >>> Name is required")
    private String name;

    @Size(max = 1000, message = "Description max 1000 chars")
    private String description;

    private String channelId;

    private PlayStatus status;

    private Long orderNum;//


      private LocalDateTime createdDate;

}
