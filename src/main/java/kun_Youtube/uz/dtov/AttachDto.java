package kun_Youtube.uz.dtov;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AttachDto {

    private String id;
    private String originName;
    private Long size;
    private String extension;
    private LocalDateTime createdDate;//create ni qilishni keltirmagan shartda lekin buni serviceda joyi kelsa set qilib qo'yaolamiz

    private Long duration;

    private String url;

}
