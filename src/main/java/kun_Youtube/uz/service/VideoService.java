package kun_Youtube.uz.service;


import kun_Youtube.uz.dtov.videol.*;
import kun_Youtube.uz.entity.*;
import kun_Youtube.uz.enumh.PlayStatus;
import kun_Youtube.uz.enumh.ProfileRoleEnum;
import kun_Youtube.uz.mapper.VideoMapper;
import kun_Youtube.uz.repository.*;
import kun_Youtube.uz.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class VideoService {


    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private AttachRepository attachRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ProfileRepository profileRepository;


    public VideoDto create(VideoCreateDto dto ,Integer profileId ) {

//        ChannelEntity channel = channelRepository.findByProfileId(profileId)
//                .orElseThrow(() -> new RuntimeException("Channel not found")); //buni pastda topib video uchun setchannel qilingan

        VideoEntity video = new VideoEntity();
        video.setId(UUID.randomUUID().toString());
        video.setTitle(dto.getTitle());
        video.setDescription(dto.getDescription());
        video.setDuration(dto.getDuration());
        video.setPreviewAttach(attachRepository.findById(dto.getPreviewAttachId())
                .orElseThrow(() -> new RuntimeException("Preview not found")));

        AttachEntity attach = attachRepository.findById(dto.getAttachId())
                .orElseThrow(() -> new RuntimeException("Video attach not found"));
        video.setAttach(attach);

        CategoryEntity category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        video.setCategory(category);


        // channel (profilega tegishli channel topiladi)
//        ChannelEntity channelGroup = channelRepository.findByProfileId(profileId)
//                .orElseThrow(() -> new RuntimeException("Channel not found"));
//        if(channelGroup.getId() == dto.getChannelId()){
//        video.setChannel(channelGroup);}
        ChannelEntity channel = channelRepository.findById(dto.getChannelId())
                .orElseThrow(() -> new RuntimeException("Channel not found"));
        video.setChannel(channel);




        // tag list
        if (dto.getTagList() != null && !dto.getTagList().isEmpty()) {
            List<TagEntity> tags = new ArrayList<>();
            tagRepository.findAllById(dto.getTagList()).forEach(tags::add);
            video.setTagList(tags);
        }



        video.setStatus(PlayStatus.PRIVATE);
        video.setCreatedDate(LocalDateTime.now());
        videoRepository.save(video);
        return VideoMapper.toDto(video);
    }



//2

    public VideoDto updateVideo(String videoId, VideoUpdateDto dto, Integer profileId) {
        VideoEntity video = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found"));

        // faqat OWNER yoki ADMIN update qilsin
// Adminligini tekshiramiz
        boolean isOwner = video.getChannel() != null
                && video.getChannel().getProfile() != null
                && video.getChannel().getProfile().getId().equals(profileId);

        boolean isAdmin = profileRepository.findById(profileId)
                .map(p -> p.getRoleList().contains(ProfileRoleEnum.ROLE_ADMIN))
                .orElse(false);

// OWNER yoki ADMIN bo‘lmasa xato
        if (!isOwner && !isAdmin) {
            throw new RuntimeException("Not allowed to update this video");
        }

        // kelgan qiymatlar bo‘yicha update qilish
        if (dto.getTitle() != null) {
            video.setTitle(dto.getTitle());
        }
        if (dto.getDescription() != null) {
            video.setDescription(dto.getDescription());
        }
        if (dto.getPreviewAttachId() != null) {
            AttachEntity preview = attachRepository.findById(dto.getPreviewAttachId())
                    .orElseThrow(() -> new RuntimeException("Preview not found"));
            video.setPreviewAttach(preview);
        }
        if (dto.getAttachId() != null) {
            AttachEntity attach = attachRepository.findById(dto.getAttachId())
                    .orElseThrow(() -> new RuntimeException("Attach not found"));
            video.setAttach(attach);
        }
        if (dto.getCategoryId() != null) {
            CategoryEntity category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            video.setCategory(category);
        }
        if (dto.getTagList() != null && !dto.getTagList().isEmpty()) {
            List<TagEntity> tags = new ArrayList<>();
            tagRepository.findAllById(dto.getTagList()).forEach(tags::add);
            video.setTagList(tags);
        }

        // update vaqtini yozib qo‘yamiz
        video.setPublishedDate(LocalDateTime.now());

        videoRepository.save(video);
        return VideoMapper.toDto(video);
    }









    public void changeVideoStatus(String videoId, PlayStatus status, Integer profileId) {
        VideoEntity video = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found"));

        boolean isOwner = video.getChannel() != null
                && video.getChannel().getProfile() != null
                && video.getChannel().getProfile().getId().equals(profileId);

        boolean isAdmin = profileRepository.findById(profileId)
                .map(p -> p.getRoleList().contains(ProfileRoleEnum.ROLE_ADMIN))
                .orElse(false);

        if (!isOwner && !isAdmin) {
            throw new RuntimeException("Not allowed to change status");
        }

        video.setStatus(status);
        videoRepository.save(video);
    }


    public String increaseViewCount(String videoId) {
        videoRepository.findById(videoId).ifPresent(video -> {
            video.setViewCount(video.getViewCount() + 1);
            videoRepository.save(video);
        });

        return ("viewCount plus 1");
    }


    public Page<VideoShortInfo> getVideosByCategory(Integer categoryId, Pageable pageable) {
        return videoRepository.findByCategoryIdAndStatus(categoryId, PlayStatus.PUBLIC, pageable)
                .map(VideoMapper::toShortInfoDto);
    }

    public Page<VideoShortInfo> searchVideosByTitle(String title, Pageable pageable) {
        return videoRepository.findByTitleContainingIgnoreCaseAndStatus(title, PlayStatus.PUBLIC, pageable)
                .map(VideoMapper::toShortInfoDto);
    }


    public Page<VideoShortInfo> getVideosByTag(String tagId, Pageable pageable) {
        return videoRepository.findByTagListIdAndStatus(tagId, PlayStatus.PUBLIC, pageable)
                .map(VideoMapper::toShortInfoDto);
    }


//8
    public VideoFullInfo getVideoById(String videoId, Integer profileId) {
        VideoEntity video = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found"));

        boolean isOwner = video.getChannel() != null
                && video.getChannel().getProfile() != null
                && video.getChannel().getProfile().getId().equals(profileId);

        boolean isAdmin = profileRepository.findById(profileId)
                .map(p -> p.getRoleList().contains(ProfileRoleEnum.ROLE_ADMIN))
                .orElse(false);

        // PRIVATE bo‘lsa faqat OWNER yoki ADMIN ko‘ra oladi
        if (video.getStatus().equals(PlayStatus.PRIVATE) && !isOwner && !isAdmin) {
            throw new RuntimeException("Not allowed to view this video");
        }

        return VideoMapper.toFullInfoDto(video, profileId);
    }






    // 9. Get Video List Pagination (ADMIN)
    public Page<VideoShortInfo> getVideoListForAdmin(Pageable pageable) {
        return videoRepository.findAll(pageable)
                .map(VideoMapper::toShortInfoAdminDto);
    }

    // 10. Get Channel Video List Pagination (created_date desc)
    public Page<VideoPlayListInfo> getChannelVideos(String channelId, Pageable pageable) {
        return videoRepository.findByChannelIdOrderByCreatedDateDesc(channelId, pageable)
                .map(VideoMapper::toPlayListInfoDto);
    }

}
