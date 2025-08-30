package kun_Youtube.uz.service;

import kun_Youtube.uz.dtov.channel.ChannelDto;
import kun_Youtube.uz.dtov.subscription.SubscriptionCreateDto;
import kun_Youtube.uz.dtov.subscription.SubscriptionInfoDto;
import kun_Youtube.uz.entity.ChannelEntity;
import kun_Youtube.uz.entity.ProfileEntity;
import kun_Youtube.uz.entity.SubscriptionEntity;
import kun_Youtube.uz.exseption.AppBadException;
import kun_Youtube.uz.repository.ChannelRepository;
import kun_Youtube.uz.repository.ProfileRepository;
import kun_Youtube.uz.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ChannelRepository channelRepository;

    // 1. Create User Subscription
    public SubscriptionInfoDto createSubscription(Integer profileId, SubscriptionCreateDto dto) {
        ProfileEntity profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new AppBadException("Profile not found"));

        ChannelEntity channel = channelRepository.findById(dto.getChannelId())
                .orElseThrow(() -> new AppBadException("Channel not found"));

        SubscriptionEntity entity = new SubscriptionEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setProfile(profile);
        entity.setChannel(channel);
        entity.setNotificationType(dto.getNotificationType());
        entity.setStatus(true);
        entity.setCreatedDate(LocalDateTime.now());

        subscriptionRepository.save(entity);

        return toDto(entity);
    }

    // 2. Change Subscription Status
    public SubscriptionInfoDto changeStatus(Integer profileId, String channelId, Boolean status) {
        SubscriptionEntity entity = subscriptionRepository
                .findByProfileIdAndChannelId(profileId, channelId)
                .orElseThrow(() -> new AppBadException("Subscription not found"));

        entity.setStatus(status);
        subscriptionRepository.save(entity);

        return toDto(entity);
    }

    // 3. Change Subscription Notification Type
    public SubscriptionInfoDto changeNotification(Integer profileId, String channelId, String notificationType) {
        SubscriptionEntity entity = subscriptionRepository
                .findByProfileIdAndChannelId(profileId, channelId)
                .orElseThrow(() -> new AppBadException("Subscription not found"));

        entity.setNotificationType(notificationType);
        subscriptionRepository.save(entity);

        return toDto(entity);
    }

    // 4. Get User Subscription List (only Active)
    public List<SubscriptionInfoDto> getUserSubscriptions(Integer profileId) {
        List<SubscriptionEntity> subscriptions = subscriptionRepository.findByProfileIdAndStatus(profileId, true);

        List<SubscriptionInfoDto> dtoList = new ArrayList<>();
        for (SubscriptionEntity entity : subscriptions) {
            dtoList.add(toDto(entity));
        }
        return dtoList;
    }

    // 5. Get User Subscription List By UserId (ADMIN)
    public List<SubscriptionInfoDto> getUserSubscriptionsByUserId(Integer userId) {
        List<SubscriptionEntity> subscriptions = subscriptionRepository.findByProfileIdAndStatus(userId, true);

        List<SubscriptionInfoDto> dtoList = new ArrayList<>();
        for (SubscriptionEntity entity : subscriptions) {
            SubscriptionInfoDto dto = toDto(entity);
            dto.setCreatedDate(entity.getCreatedDate()); // Admin uchun createdDate qo‘shiladi
            dtoList.add(dto);
        }
        return dtoList;
    }

    // ---------- Helper ----------
    private SubscriptionInfoDto toDto(SubscriptionEntity entity) {
        SubscriptionInfoDto dto = new SubscriptionInfoDto();
        dto.setId(entity.getId());
        dto.setNotificationType(entity.getNotificationType());

        ChannelDto channelDto = new ChannelDto();
        channelDto.setId(entity.getChannel().getId());
        channelDto.setName(entity.getChannel().getName());
        if (entity.getChannel().getPhoto() != null) {
            channelDto.setPhotoId(entity.getChannel().getPhoto().getId());
        }
        dto.setChannel(channelDto);

        return dto;
    }
}
