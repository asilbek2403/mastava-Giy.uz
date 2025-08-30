package kun_Youtube.uz.dtov;

import kun_Youtube.uz.dtov.profile.ProfileDto;
import kun_Youtube.uz.enumh.ReportType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class ReportInfoDto {

    private String id;
    private ProfileDto profile; // id, name, surname, photo
    private String content;
    private String entityId; // channelId yoki videoId
    private ReportType type; // CHANNEL, VIDEO
    private LocalDateTime createdDate;
}
