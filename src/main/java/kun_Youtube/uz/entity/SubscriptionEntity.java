package kun_Youtube.uz.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "subscription")
public class SubscriptionEntity {

    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private ProfileEntity profile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", nullable = false)
    private ChannelEntity channel;

    @Column(nullable = false)
    private Boolean status = true; // active/inactive

    @Column(name = "notification_type")
    private String notificationType; // push, email, sms

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;
}

