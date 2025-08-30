package kun_Youtube.uz.entity;


import jakarta.persistence.*;
import kun_Youtube.uz.dtov.videol.VideoEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="category")
public class CategoryEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
    //id,name,created_date
        @Column(name = "name", nullable = false, unique = true)
        private String name;
        @Column
        private Boolean visible = Boolean.TRUE;

        @Column(name = "created_Date")
        private LocalDateTime createdDate;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<VideoEntity> videoList = new ArrayList<>();

}
