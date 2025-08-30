package kun_Youtube.uz.dtov.playList;

import kun_Youtube.uz.dtov.AttachDto;
import kun_Youtube.uz.dtov.profile.ProfileDto;
import kun_Youtube.uz.entity.AttachEntity;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ChannelDetailDTO {
    private String id;
    private String name;
    private AttachDto photo;   // channel photo

    private ProfileDto profile;
}
