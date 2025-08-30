package kun_Youtube.uz.dtov.jwtDto;


import kun_Youtube.uz.enumh.ProfileRoleEnum;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
@AllArgsConstructor
public class JWTDto {
        private String username;
        private List<ProfileRoleEnum> role;
        private Integer code;
    private Integer profileId; // ✅ Mana shu yerda

        public JWTDto() {

        }
        public JWTDto(String username, List<ProfileRoleEnum> role) {
            this.username = username;
            this.role = role;
        }


}
