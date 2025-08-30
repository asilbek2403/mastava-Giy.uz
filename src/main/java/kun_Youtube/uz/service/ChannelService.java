package kun_Youtube.uz.service;

import kun_Youtube.uz.dtov.channel.ChannelCreateDto;
import kun_Youtube.uz.dtov.channel.ChannelDto;
import kun_Youtube.uz.dtov.channel.ChannelUpdateDto;
import kun_Youtube.uz.entity.AttachEntity;
import kun_Youtube.uz.entity.ChannelEntity;
import kun_Youtube.uz.entity.ProfileRoleEntity;
import kun_Youtube.uz.enumh.ChannelStatus;
import kun_Youtube.uz.enumh.ProfileRoleEnum;
import kun_Youtube.uz.repository.AttachRepository;
import kun_Youtube.uz.repository.ChannelRepository;
import kun_Youtube.uz.repository.ProfileRoleRepository;
import kun_Youtube.uz.util.SpringSecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class ChannelService {

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private ProfileRoleRepository profileRoleRepository;

    @Autowired
    private AttachRepository attachRepository;

    // ---------------- CREATE ----------------
    public ChannelDto create(ChannelCreateDto dto) {
        Integer profileId = SpringSecurityUtil.currentProfileId();

        // OWNER roli bor-yo‘qligini tekshirish
        List<ProfileRoleEnum> roleList = profileRoleRepository.getRoleListByProfileId(profileId);
        if (!roleList.contains(ProfileRoleEnum.ROLE_OWNER)) {
            ProfileRoleEntity roleEntity = new ProfileRoleEntity();
            roleEntity.setProfileId(profileId);
            roleEntity.setRoles(ProfileRoleEnum.ROLE_OWNER);
            profileRoleRepository.save(roleEntity);
            log.info("ROLE_OWNER added to profileId = {}", profileId);
        }

        // Channel yaratish
        ChannelEntity entity = new ChannelEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setProfileId(profileId);
        entity.setStatus(ChannelStatus.ACTIVE);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setPhotoId(dto.getPhotoId());
        // photoId → AttachEntity mapping
        AttachEntity photo = attachRepository.findById(dto.getPhotoId())
                .orElseThrow(() -> new RuntimeException("Photo not found"));
        entity.setPhoto(photo);

        // bannerId saqlash (string tipida)
        entity.setBannerId(dto.getBannerId());

        channelRepository.save(entity);

        return toDTO(entity);
    }

    // ---------------- TO DTO ----------------
    public ChannelDto toDTO(ChannelEntity entity) {
        if (entity == null) return null;
        ChannelDto dto = new ChannelDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setProfileId(entity.getProfileId());
        dto.setBannerId(entity.getBannerId());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setPhotoId(entity.getPhotoId());
        return dto;
    }

    // ---------------- UPDATE ----------------
    public ChannelDto updateChannel(String channelId, ChannelUpdateDto dto) {
        Integer currentProfileId = SpringSecurityUtil.currentProfileId();
        ChannelEntity entity = channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found"));

        List<ProfileRoleEnum> roleList = profileRoleRepository.getRoleListByProfileId(currentProfileId);
        boolean isAdmin = roleList.contains(ProfileRoleEnum.ROLE_ADMIN);
        boolean isOwner = roleList.contains(ProfileRoleEnum.ROLE_OWNER) &&
                entity.getProfileId().equals(currentProfileId);

        if(!isAdmin && !isOwner) {
            throw new RuntimeException("Not authorized to update this channel");
        }

        if(dto.getName() != null) entity.setName(dto.getName());
        if(dto.getDescription() != null) entity.setDescription(dto.getDescription());

        channelRepository.save(entity);
        return toDTO(entity);
    }

    // ---------------- UPDATE PHOTO ----------------
    public ChannelDto updatePhoto(String channelId, String photoId) {
        Integer currentProfileId = SpringSecurityUtil.currentProfileId();
        ChannelEntity entity = channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found"));

        List<ProfileRoleEnum> roleList = profileRoleRepository.getRoleListByProfileId(currentProfileId);
        boolean isAdmin = roleList.contains(ProfileRoleEnum.ROLE_ADMIN);
        boolean isOwner = roleList.contains(ProfileRoleEnum.ROLE_OWNER) &&
                entity.getProfileId().equals(currentProfileId);

        if(!isAdmin && !isOwner) {
            throw new RuntimeException("Not authorized to update photo");
        }

        AttachEntity photo = attachRepository.findById(photoId)
                .orElseThrow(() -> new RuntimeException("Photo not found"));
        entity.setPhotoId(photoId);
        entity.setPhoto(photo);

        channelRepository.save(entity);
        return toDTO(entity);
    }

    // ---------------- UPDATE BANNER ----------------
    public ChannelDto updateBanner(String channelId, String bannerId) {
        Integer currentProfileId = SpringSecurityUtil.currentProfileId();
        ChannelEntity entity = channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found"));

        List<ProfileRoleEnum> roleList = profileRoleRepository.getRoleListByProfileId(currentProfileId);
        boolean isAdmin = roleList.contains(ProfileRoleEnum.ROLE_ADMIN);
        boolean isOwner = roleList.contains(ProfileRoleEnum.ROLE_OWNER) &&
                entity.getProfileId().equals(currentProfileId);

        if(!isAdmin && !isOwner) {
            throw new RuntimeException("Not authorized to update banner");
        }

        entity.setBannerId(bannerId);
        channelRepository.save(entity);
        return toDTO(entity);
    }

    // ---------------- GET BY ID ----------------
    public ChannelDto getById(String channelId) {
        ChannelEntity entity = channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found"));
        return toDTO(entity);
    }

    // ---------------- PAGINATION ----------------
    public PageImpl<ChannelDto> pagination(int page , int size) {
        Pageable pageable = PageRequest.of(page, size , Sort.by("createdDate").descending());
        Page<ChannelEntity> channels = channelRepository.findAllActive(pageable);

        List<ChannelDto> dtoList = new ArrayList<>();
        for(ChannelEntity channel : channels.getContent()) {
            dtoList.add(toDTO(channel));
        }
        return new PageImpl<>(dtoList, pageable, channels.getTotalElements());
    }

    // ---------------- STATUS CHANGE ----------------
    public ChannelDto statusChange(String channelId , ChannelStatus status) {
        Integer currentId = SpringSecurityUtil.currentProfileId();
        ChannelEntity entity = channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found"));

        List<ProfileRoleEnum> roleList = profileRoleRepository.getRoleListByProfileId(currentId);
        boolean isAdmin = roleList.contains(ProfileRoleEnum.ROLE_ADMIN);
        boolean isOwner = roleList.contains(ProfileRoleEnum.ROLE_OWNER) &&
                entity.getProfileId().equals(currentId);

        if(!isAdmin && !isOwner) {
            throw new RuntimeException("Not authorized to change status");
        }

        entity.setStatus(status);
        channelRepository.save(entity);
        return toDTO(entity);
    }

    // ---------------- USER CHANNELS ----------------
    public PageImpl<ChannelDto> getUserChannels(int page, int size) {
        Integer currentProfileId = SpringSecurityUtil.currentProfileId();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<ChannelEntity> channels = channelRepository.findAllByProfileId(currentProfileId, pageable);

        List<ChannelDto> dtoList = new ArrayList<>();
        for (ChannelEntity channel : channels.getContent()) {
            dtoList.add(toDTO(channel));
        }
        return new PageImpl<>(dtoList, pageable, channels.getTotalElements());
    }

}
