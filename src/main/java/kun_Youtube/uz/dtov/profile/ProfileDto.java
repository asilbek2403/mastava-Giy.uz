package kun_Youtube.uz.dtov.profile;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import kun_Youtube.uz.dtov.AttachDto;
import kun_Youtube.uz.entity.AttachEntity;
import kun_Youtube.uz.enumh.ProfileRoleEnum;
import kun_Youtube.uz.enumh.ProfileStatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDto {

    private Integer id;

    @NotBlank(message = "Ism bo‘sh bo‘lmasligi kerak")
    private String name;

    @NotBlank(message = "Familiya bo‘sh bo‘lmasligi kerak")
    private String surname;

    @NotBlank(message = "Username(email)  bo‘sh bo‘lmasligi kerak")
    private String username;

    @NotBlank(message = "Parol bo‘sh bo‘lmasligi kerak")
    private String password;

    private String photo;

    @NotEmpty(message = "Role bo‘sh bo‘lmasligi kerak")
    private List<ProfileRoleEnum> roleList;

//    @Enumerated(EnumType.STRING)
    private ProfileStatusEnum status;

    private LocalDateTime createdDate;

    private String jwt;
//    private AttachEntity photo;

}
