package kun_Youtube.uz.entity;


import jakarta.persistence.*;
import kun_Youtube.uz.dtov.AttachDto;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "attach")
public class AttachEntity {

        @Id
//        @GeneratedValue(strategy = GenerationType.UUID)
        private String id;
        @Column(name = "name")
        private String name;
        @Column(name = "path")
        private String path;
        @Column(name = "extension")
        private String extension;
        @Column(name="size")
        private Long size;
        @Column(name = "origen_name")
        private String origenName;
        @Column(name = "visible")
        private Boolean visible = true;
        @Column(name = "created_Date")
        private LocalDateTime createdDate;

//        @Column(name = "duration")
//        private String duration;

        @Column(name = "url")
        private String url;


}
//  id(uuid),origin_name,size,type (extension),path,duration
