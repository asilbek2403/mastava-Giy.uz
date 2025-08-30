package kun_Youtube.uz.service;


import kun_Youtube.uz.dtov.AttachDto;
import kun_Youtube.uz.dtov.playList.*;
import kun_Youtube.uz.dtov.profile.ProfileDto;
import kun_Youtube.uz.entity.ChannelEntity;
import kun_Youtube.uz.entity.PlayListEntity;
import kun_Youtube.uz.enumh.PlayStatus;
import kun_Youtube.uz.enumh.ProfileRoleEnum;
import kun_Youtube.uz.exseption.AppBadRequestException;
import kun_Youtube.uz.exseption.AppForbiddenException;
import kun_Youtube.uz.repository.ChannelRepository;
import kun_Youtube.uz.repository.PlayListRepository;
import kun_Youtube.uz.repository.PlayListVideoRepository;
import kun_Youtube.uz.util.SpringSecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class PlayListService {

    @Autowired
    private PlayListVideoRepository playListVideoRepository;

    @Autowired
    private VideoViewService videoViewService;
    @Autowired
    private PlayListRepository playListRepository;
    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private AttachService attachService;


    public PlayListDto create(PlayListCreate dto) {

        ChannelEntity channel = channelRepository.findById(dto.getChannelId())
                .orElseThrow(() -> new AppBadRequestException("Channel not found"));

        PlayListEntity playListEntity = new PlayListEntity();
        playListEntity.setName(dto.getName());
        playListEntity.setId(UUID.randomUUID().toString());
        playListEntity.setDescription(dto.getDescription());
        playListEntity.setChannelId(dto.getChannelId());
        playListEntity.setCreatedDate(LocalDateTime.now());
        playListEntity.setStatus( dto.getStatus() != null ? dto.getStatus() : PlayStatus.PUBLIC);


            Long maxLong = playListRepository.findMaxOrderNumByChannelId(dto.getChannelId());
            playListEntity.setOrderNum(maxLong != null ? maxLong + 1 : 1L);

        playListRepository.save(playListEntity);
        return toDto(playListEntity);
    }


    private PlayListDto toDto(PlayListEntity entity) {
        PlayListDto dto = new PlayListDto();
        dto.setName(entity.getName());
        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setChannelId(entity.getChannelId());
        dto.setOrderNum(entity.getOrderNum());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }



    //Playlistda raqamlar almashishi

//    public void reorderPlaylists(String channelId, List<String> playlistIds) {
//        // 1. Channelga tegishli playlistlarni chiqarish
//        List<PlayListEntity> playlists =
//                playListRepository.findByChannelIdOrderByOrderNum(channelId);
//
//        if (playlistIds.size() != playlists.size()) {
//            throw new AppBadRequestException("Invalid playlist order size");
//        }
//
//        // 2. Reorder qilish
//        for (int i = 0; i < playlistIds.size(); i++) {
//            String playlistId = playlistIds.get(i);
//
//            PlayListEntity entity = playlists.stream()
//                    .filter(p -> p.getId().equals(playlistId))
//                    .findFirst()
//                    .orElseThrow(() ->
//                            new AppBadRequestException("Playlist not found: " + playlistId));
//
//            entity.setOrder_num((long) (i + 1));
//        }
//
//        // 3. Saqlash
//        playListRepository.saveAll(playlists);
//    }


//2 Update
    public PlayListDto updateUser(String playListID , PlayListUpdate dto){

        Integer userId = SpringSecurityUtil.currentProfileId();
        PlayListEntity entity = playListRepository.findById(playListID)
                .orElseThrow(() ->  new AppBadRequestException (" The Playlist not found "));

        if( !entity.getChannel().getProfileId().equals(userId)){
            throw new AppBadRequestException("Afsuski notog'ri user profile");
        }
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
//        entity.setStatus(dto.getStatus());
        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }
        playListRepository.save(entity);
        return toDto(entity);

    }


//3 Update Status
    public PlayListDto changeStatus(String playlistId, PlayStatus newStatus) {
    Integer currentProfileId = SpringSecurityUtil.currentProfileId();
        log.info(currentProfileId+"<><><><><><><><><><> Id sini");

    PlayListEntity playlist = playListRepository.findById(playlistId)
            .orElseThrow(() -> new AppBadRequestException("Playlist not found"));

    // faqat o‘zining channelidagi playlistni update qilishga ruxsat
        if (!playlist.getChannel().getProfileId().equals(currentProfileId)) {
            System.out.println("Not allowed to change status of this playlist");
        }

    playlist.setStatus(newStatus);
    playlist.setLastUpdateDate(LocalDateTime.now());
    playListRepository.save(playlist);

    return toDto(playlist);
}

//4 Delete

    public void deletePlaylist(String playlistId) throws AppForbiddenException {
        Integer currentUserId = SpringSecurityUtil.currentProfileId();
        PlayListEntity playlist = playListRepository.findById(playlistId)
                .orElseThrow(() -> new AppBadRequestException("Playlist not found"));

        // faqat owner yoki admin delete qila oladi
        if (!playlist.getChannel().getProfileId().equals(currentUserId) &&
                !SpringSecurityUtil.hasAnyRoles(ProfileRoleEnum.ROLE_ADMIN)) {
            throw new AppForbiddenException("You are not allowed to delete this playlist");
        }

        // Soft delete
        playlist.setDeleted(true);  // yoki playlist.setVisible(false);
        playlist.setLastUpdateDate(LocalDateTime.now());
        playListRepository.save(playlist);
    }


    //8
//get List


//5
//
//    public Page<PlayListInfo> pagination(int page, int size) {
//        PageRequest pageable = PageRequest.of(page, size);
//
//        Page<PlayListEntity> entityPage = playListRepository.findAllByDeletedFalse(pageable);
//
//        return entityPage.map(this::toDTO);
//    }
//
//    private PlayListInfo toDTO(PlayListEntity entity) {
//        PlayListInfo dto = new PlayListInfo();
//        dto.setId(entity.getId());
//        dto.setName(entity.getName());
//        dto.setDescription(entity.getDescription());
//        dto.setStatus(entity.getStatus());
//        dto.setOrderNum(entity.getOrderNum());
//        dto.setCreatedDate(entity.getCreatedDate());
//
//        // Channel DTO
//        ChannelDetailDTO channelDTO = new ChannelDetailDTO();
//        channelDTO.setId(entity.getChannel().getId());
//        channelDTO.setName(entity.getChannel().getName());
//
//        if (entity.getChannel().getPhoto() != null) {
//            channelDTO.setPhoto();
//        }
//
//        // Profile DTO
//        ProfileDto profileDTO = new ProfileDto();
//        profileDTO.setId(entity.getChannel().getProfile().getId());
//        profileDTO.setName(entity.getChannel().getProfile().getName());
//        profileDTO.setSurname(entity.getChannel().getProfile().getSurname());
//
//        if (entity.getChannel().getProfile().getPhoto() != null) {
//            profileDTO.setPhoto();}
//
//        channelDTO.setProfile(profileDTO);
//        dto.setChannel(channelDTO);
//
//        return dto;
//    }
        public PageImpl<PlayListInfo> pagination(int page, int size) {
            PageRequest pageable = PageRequest.of(page, size);
            Page<PlayListEntity> entityPage = playListRepository.findAllByDeletedFalse(pageable);

    // DTO ga map qilish
            List<PlayListInfo> dtoList = entityPage.stream()
                .map(this::toInfoDTO)
                .toList();

    return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
        }






    private PlayListInfo toInfoDTO(PlayListEntity entity) {
        PlayListInfo dto = new PlayListInfo();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus());
        dto.setOrderNum(entity.getOrderNum());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setLastUpdateDate(entity.getLastUpdateDate());

        // Channel DTO
        ChannelDetailDTO channelDTO = new ChannelDetailDTO();
        channelDTO.setId(entity.getChannel().getId());
        channelDTO.setName(entity.getChannel().getName());

        // Channel photo
        if (entity.getChannel().getPhoto() != null) {
            AttachDto photoDTO = new AttachDto();
            photoDTO.setId(entity.getChannel().getPhoto().getId());
            photoDTO.setUrl(entity.getChannel().getPhoto().getUrl());

            channelDTO.setPhoto(photoDTO);
        }

        // Profile DTO
        ProfileDto profileDTO = new ProfileDto();
        profileDTO.setId(entity.getChannel().getProfile().getId());
        profileDTO.setName(entity.getChannel().getProfile().getName());
        profileDTO.setSurname(entity.getChannel().getProfile().getSurname());

//        if (entity.getChannel().getProfile().getPhoto() != null) {
//            AttachDto profilePhoto = new AttachDto();
//            profilePhoto.setId(entity.getChannel().getPhoto().getId());
//            profilePhoto.setUrl(attachService.openURL(entity.getChannel().getPhoto().getId()));
//            profileDTO.setPhoto(entity.getChannel().getProfile().getPhoto());
//        }

//        if (entity.getChannel().getProfile().getPhoto() != null) {
//            AttachDto profilePhoto = new AttachDto();
//            profilePhoto.setId(entity.getChannel().getProfile().getPhoto()); // String photoId
//            profilePhoto.setUrl(attachService.openURL(entity.getChannel().getProfile().getPhoto()));
//            profileDTO.setPhoto(profilePhoto);  // AttachDto beramiz
//        }

        channelDTO.setProfile(profileDTO);
        dto.setChannel(channelDTO);

        return dto;
    }


    //6
    // Service
    public List<PlayListShortInfo> getByUserId(Integer userId) {
        // Userga tegishli playlistlarni orderNum bo‘yicha kamayish tartibida olish
        Integer currentUserId = SpringSecurityUtil.currentProfileId();
                    log.info("USer id : " + currentUserId);
        if(userId !=currentUserId) {
            System.out.println("Not user profile");
        }
        List<PlayListEntity> entities = playListRepository
                    .findByDeletedFalseAndChannel_ProfileIdOrderByOrderNumDesc(userId);

        // DTO ga map qilish (sodda for-each bilan)
        List<PlayListShortInfo> dtoList = new ArrayList<>();
        for (PlayListEntity entity : entities) {
            dtoList.add(toShortDTO(entity));
        }

        return dtoList;
    }

    // DTO ga konvertatsiya qiluvchi metod
    private PlayListShortInfo toShortDTO(PlayListEntity entity) {
        PlayListShortInfo dto = new PlayListShortInfo();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setOrderNum(entity.getOrderNum());
        return dto;
    }







    /// ///////////////////////////////////////////////////////
    public List<PlayListShortInfo> getUserPlayLists(Integer profileId) {
        List<PlayListEntity> lists = playListRepository.findByDeletedFalseAndChannel_ProfileIdOrderByOrderNumDesc(profileId);

        // Oddiy for-each bilan DTO ga o'zgartirish
        List<PlayListShortInfo> dtoList = new ArrayList<>();
        for (PlayListEntity pl : lists) {
            PlayListShortInfo dto = new PlayListShortInfo();
            dto.setId(pl.getId());
            dto.setName(pl.getName());
            dto.setOrderNum(pl.getOrderNum());
            dtoList.add(dto);
        }

        return dtoList;
    }









    public long getPlaylistViewsCount(String playlistId) {
        return playListVideoRepository.findByPlaylistId(playlistId)
                .stream()
                .mapToLong(pv -> videoViewService.getViewsCount(pv.getVideo().getId()))
                .sum();
    }



}
