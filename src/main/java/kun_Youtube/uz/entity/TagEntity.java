package kun_Youtube.uz.entity;


import jakarta.persistence.*;
//import jakarta.validation.constraints.NotBlank;
import kun_Youtube.uz.dtov.videol.VideoEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tag")
public class TagEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
//        @Column(nullable = false)
        private String key;
//        @Column(nullable = false)

        @Column(name = "name_uz")
        private String nameUz;
        @Column(name = "name_ru")
        private String nameRu;

        @Column(name="name_en")
        private String nameEn;

    @Column
    private Boolean visible = true;

    @Column
    private LocalDateTime createdDate;
    @Column(name = "order_number")
    private Integer orderNumber;


    @Column(nullable = false)
    private String name;


    // ManyToMany inverse side (mappedBy = "tagList")
    @ManyToMany(mappedBy = "tagList", fetch = FetchType.LAZY)
    private List<VideoEntity> videoList;


}
