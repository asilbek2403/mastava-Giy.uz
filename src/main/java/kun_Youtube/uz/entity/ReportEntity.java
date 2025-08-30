package kun_Youtube.uz.entity;

import jakarta.persistence.*;
import kun_Youtube.uz.enumh.ReportType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "report")
public class ReportEntity {


        @Id
        private String id;

        @ManyToOne
        @JoinColumn(name = "profile_id")
        private ProfileEntity profile;

        private String content;

        private String entityId; // videoId yoki channelId

        @Enumerated(EnumType.STRING)
        private ReportType type;

        private LocalDateTime createdDate;
}
