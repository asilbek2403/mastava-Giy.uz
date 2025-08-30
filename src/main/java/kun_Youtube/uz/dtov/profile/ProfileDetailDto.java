package kun_Youtube.uz.dtov.profile;


import kun_Youtube.uz.enumh.ProfileRoleEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProfileDetailDto {
    //main_photo((url)
    private Integer id;

    private String name;

    private String surname;

    private String username;

    private String  photo;

    private List<ProfileRoleEnum> roleList;

}
