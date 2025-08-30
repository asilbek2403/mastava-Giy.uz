package kun_Youtube.uz.dtov;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CategoryDto {
    private Integer id;
    @NotBlank(message = "name required enter")
    private String name;
    private LocalDateTime createdDate;

}
