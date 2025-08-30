package kun_Youtube.uz.dtov;

import kun_Youtube.uz.enumh.ReportType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportCreateDto {

    private String id;
    private String content;
    private String entityId;
    private ReportType type; // CHANNEL, VIDEO

}

