package kun_Youtube.uz.service;

import kun_Youtube.uz.dtov.AttachDto;
import kun_Youtube.uz.dtov.commitLike.VideoLikeInfo;
import kun_Youtube.uz.dtov.playList.ChannelDetailDTO;
import kun_Youtube.uz.dtov.videol.VideoShortInfo;
import kun_Youtube.uz.entity.commentLike.VideoLikeEntity;
import kun_Youtube.uz.enumh.LikeType;
import kun_Youtube.uz.repository.VideoLikeRepository;
import kun_Youtube.uz.repository.VideoRepository;
import kun_Youtube.uz.util.SpringSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VideoLikeService {

    private final VideoLikeRepository videoLikeRepository;
    private final VideoRepository videoRepository;

    /** Create or Update Like/Dislike */
    public void create(String videoId, LikeType status) {
        Integer profileId = SpringSecurityUtil.currentProfileId();

        Optional<VideoLikeEntity> optional =
                videoLikeRepository.findByVideoIdAndProfileId(videoId, profileId);

        if (optional.isPresent()) {
            // update
            VideoLikeEntity entity = optional.get();
            entity.setType(status);
            videoLikeRepository.save(entity);
        } else {
            // create
            VideoLikeEntity entity = new VideoLikeEntity();
            entity.setVideoId(videoId);
            entity.setProfileId(profileId);
            entity.setType(status);
            videoLikeRepository.save(entity);
        }
    }

    /** Remove like/dislike */
    public boolean remove(String videoId) {
        Integer profileId = SpringSecurityUtil.currentProfileId();
        int effected = videoLikeRepository.deleteByVideoIdAndProfileId(videoId, profileId);
        return effected > 0;
    }

    /** User own liked videos */
    public List<VideoLikeInfo> userLikedVideos() {
        Integer profileId = SpringSecurityUtil.currentProfileId();
        List<VideoLikeEntity> entities =
                videoLikeRepository.findByProfileIdOrderByCreatedDateDesc(profileId);

        List<VideoLikeInfo> dtoList = new ArrayList<>();
        for (VideoLikeEntity e : entities) {
            videoRepository.findById(e.getVideoId()).ifPresent(v -> {
                VideoLikeInfo dto = new VideoLikeInfo();
                dto.setId(e.getId());

                VideoShortInfo shortInfo = new VideoShortInfo();
                shortInfo.setId(v.getId());
                shortInfo.setTitle(v.getTitle());
                shortInfo.setDuration(v.getDuration());
                // shortInfo.setChannel(v.getChannel()); // Agar channel DTO bo‘lsa
                // shortInfo.setPreviewUrl(v.getPreviewAttach().getUrl());

                dto.setVideo(shortInfo);
                dtoList.add(dto);
            });
        }
        return dtoList;
    }

    /** Get user liked videos by userId (ADMIN) */
    public List<VideoLikeInfo> getUserLikedVideosByUserId(Integer userId) {
        List<VideoLikeEntity> entities = videoLikeRepository.findByProfileId(userId);

        List<VideoLikeInfo> dtoList = new ArrayList<>();
        for (VideoLikeEntity e : entities) {
            videoRepository.findById(e.getVideoId()).ifPresent(v -> {
                VideoLikeInfo dto = new VideoLikeInfo();
                dto.setId(e.getId());

                VideoShortInfo shortInfo = new VideoShortInfo();
                shortInfo.setId(v.getId());
                shortInfo.setTitle(v.getTitle());
                shortInfo.setDuration(v.getDuration());
                shortInfo.setPublishedDate(v.getPublishedDate());
                shortInfo.setViewCount(v.getViewCount());

                // previewAttach mapping (Entity -> DTO)
                if (v.getPreviewAttach() != null) {
                    AttachDto attachDto = new AttachDto();
                    attachDto.setId(v.getPreviewAttach().getId());
                    attachDto.setUrl("/attach/open/" + v.getPreviewAttach().getId()); // agar url builder bo‘lsa undan foydalan
                    shortInfo.setPreviewAttach(attachDto);
                }

                // channel mapping (Entity -> DTO)
                if (v.getChannel() != null) {
                    ChannelDetailDTO channelDto = new ChannelDetailDTO();
                    channelDto.setId(v.getChannel().getId());
                    channelDto.setName(v.getChannel().getName());

                    if (v.getChannel().getPhoto() != null) {
                        AttachDto photoDto = new AttachDto();
                        photoDto.setId(v.getChannel().getPhoto().getId());
                        photoDto.setUrl("/attach/open/" + v.getChannel().getPhoto().getId());
                        channelDto.setPhoto(photoDto);
                    }

                    shortInfo.setChannel(channelDto);
                }

                dto.setVideo(shortInfo);
                dtoList.add(dto);
            });
        }
        return dtoList;
    }

    /** Get like count by videoId */
    public Long getLikeCount(String videoId) {
        return videoLikeRepository.countByVideoIdAndType(videoId, LikeType.LIKE);
    }

    /** Get dislike count by videoId */
    public Long getDislikeCount(String videoId) {
        return videoLikeRepository.countByVideoIdAndType(videoId, LikeType.DISLIKE);
    }

    /** Check if current user liked/disliked this video */
    public LikeType getUserReaction(String videoId) {
        Integer profileId = SpringSecurityUtil.currentProfileId();
        return videoLikeRepository.findByVideoIdAndProfileId(videoId, profileId)
                .map(VideoLikeEntity::getType)
                .orElse(null);
    }





}
