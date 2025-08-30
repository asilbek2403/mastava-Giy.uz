package kun_Youtube.uz.dtov.tag;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TagDto {

//    private TagDto(){
//
//    }
    private Integer id;

    private String name;

    @NotBlank( message= "name_uz required")
    private String nameUz;
    @NotBlank(message = "name_ru required")
    private String nameRu;

    @NotBlank(message="name_en required")
    private String nameEn;
    @NotNull(message = "order_number")
    @Min(value = 1, message = "orderNumber have to higher than 0")
    private Integer orderNumber;



    private String key;

    private LocalDateTime createdDate;


//    public TagDto(Integer id, String name) {
//        this.id = id;
//        this.name = name;
//    }
}
