package kun_Youtube.uz.dtov.subscription;


import kun_Youtube.uz.dtov.channel.ChannelDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SubscriptionInfoDto {
    private String id;
    private Boolean status;
    private String notificationType;
    private ChannelDto channel;
    private LocalDateTime createdDate;
}

