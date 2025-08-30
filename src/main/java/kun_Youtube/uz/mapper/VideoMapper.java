package kun_Youtube.uz.mapper;




import kun_Youtube.uz.dtov.AttachDto;
import kun_Youtube.uz.dtov.CategoryDto;
import kun_Youtube.uz.dtov.channel.ChannelDto;
import kun_Youtube.uz.dtov.playList.ChannelDetailDTO;
import kun_Youtube.uz.dtov.playList.PlayListDto;
import kun_Youtube.uz.dtov.tag.TagDto;
import kun_Youtube.uz.dtov.videol.*;
import kun_Youtube.uz.entity.PlayListEntity;
import kun_Youtube.uz.entity.ProfileEntity;
import kun_Youtube.uz.entity.TagEntity;
import kun_Youtube.uz.enumh.PlayStatus;

import java.util.List;
import java.util.Optional;

public class VideoMapper {


    // Entity -> DTO
    public static VideoDto toDto(VideoEntity video) {
        VideoDto dto = new VideoDto();
        dto.setId(video.getId());
        dto.setTitle(video.getTitle());
        dto.setDescription(video.getDescription());
        dto.setDuration(video.getDuration());
        if (video.getAttach() != null) {
            dto.setAttachId(video.getAttach().getId());
        }
        if (video.getPreviewAttach() != null) {
            dto.setPreviewAttachId(video.getPreviewAttach().getId());
        }
        if (video.getCategory() != null) {
            dto.setCategoryId(video.getCategory().getId());
        }
        if (video.getChannel() != null) {
            dto.setChannelId(video.getChannel().getId());
        }
        if (video.getTagList() != null) {
            dto.setTagList(video.getTagList().stream().map(TagEntity::getId).toList());
        }


        dto.setStatus(PlayStatus.valueOf(video.getStatus().toString()));
        dto.setCreatedDate(video.getCreatedDate());
        return dto;


        }


    public static VideoShortInfo toShortInfoDto(VideoEntity video) {
        VideoShortInfo dto = new VideoShortInfo();
        dto.setId(video.getId());
        dto.setTitle(video.getTitle());
        dto.setPublishedDate(video.getPublishedDate());
        dto.setViewCount(video.getViewCount());
        dto.setDuration(video.getDuration());

        if (video.getPreviewAttach() != null) {
            AttachDto preview = new AttachDto();
            preview.setId(video.getPreviewAttach().getId());
            preview.setUrl(video.getPreviewAttach().getUrl());
            dto.setPreviewAttach(preview);
        }

        if (video.getChannel() != null) {
            ChannelDetailDTO channelDto = new ChannelDetailDTO();
            channelDto.setId(video.getChannel().getId());
            channelDto.setName(video.getChannel().getName());
            if (video.getChannel().getPhoto() != null) {
                AttachDto photo = new AttachDto();
                photo.setId(video.getChannel().getPhoto().getId());
                photo.setUrl(video.getChannel().getPhoto().getUrl());
                channelDto.setPhoto(photo);
            }
            dto.setChannel(channelDto);
        }

        return dto;
    }


    public static VideoFullInfo toFullInfoDto(VideoEntity video, Integer profileId) {
        VideoFullInfo dto = new VideoFullInfo();
        dto.setId(video.getId());
        dto.setTitle(video.getTitle());
        dto.setDescription(video.getDescription());
        dto.setPublishedDate(video.getPublishedDate());
        dto.setViewCount(video.getViewCount());
        dto.setSharedCount(video.getSharedCount());
        dto.setDuration(video.getDuration());

        // Preview
        if (video.getPreviewAttach() != null) {
            AttachDto preview = new AttachDto();
            preview.setId(video.getPreviewAttach().getId());
            preview.setUrl(video.getPreviewAttach().getUrl());
            dto.setPreviewAttach(preview);
        }

        // Attach (video file)
        if (video.getAttach() != null) {
            AttachDto attach = new AttachDto();
            attach.setId(video.getAttach().getId());
            attach.setUrl(video.getAttach().getUrl());
            attach.setDuration(video.getDuration());
            dto.setAttach(attach);
        }

        // Category
        if (video.getCategory() != null) {
            CategoryDto category = new CategoryDto();/*
            video.getCategory().getId(),
                    video.getCategory().getName()*/
            category.setId(video.getCategory().getId());
            category.setName(video.getCategory().getName());
            dto.setCategory(category);
        }

        // Tags
        if (video.getTagList() != null) {
            dto.setTagList(
                    video.getTagList().stream().map(tag -> {
                        TagDto tagDto = new TagDto();
                        tagDto.setId(tag.getId());
                        tagDto.setName(tag.getName());
                        return tagDto;
                    }).toList()
            );
        } else {
            dto.setTagList(List.of());
        }



        // Channel
        if (video.getChannel() != null) {
            ChannelDto channel = new ChannelDto();
            channel.setId(video.getChannel().getId());
            channel.setName(video.getChannel().getName());
            if (video.getChannel().getPhoto() != null) {
                channel.setPhotoId(video.getChannel().getPhoto().getId());
            }

            dto.setChannel(channel);
        }

        // Like/Dislike (masalan trigger yoki service orqali)
        LikeInfo newLike = new LikeInfo();
        newLike.setLikeCount(video.getLikeCount());
        newLike.setDislikeCount(video.getDislikeCount());
        dto.setLike(newLike);


        return dto;
    }






    //9-10
    // 9. ADMIN uchun DTO
    public static VideoShortInfo toShortInfoAdminDto(VideoEntity entity) {
        VideoShortInfo dto = new VideoShortInfo();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());

        // preview attach
        if (entity.getPreviewAttach() != null) {
            AttachDto attachDto = new AttachDto();
            attachDto.setId(entity.getPreviewAttach().getId());
            attachDto.setUrl(entity.getPreviewAttach().getUrl());
            dto.setPreviewAttach(attachDto);
        }

        dto.setPublishedDate(entity.getPublishedDate());
        dto.setViewCount(entity.getViewCount());
        dto.setDuration(entity.getDuration());

        // owner (profile)
        if (entity.getChannel() != null && entity.getChannel().getProfile() != null) {
            ProfileEntity p = entity.getChannel().getProfile();
            p.setId(entity.getChannel().getProfile().getId());
            p.setName(entity.getChannel().getProfile().getName());
            p.setUsername(entity.getChannel().getProfile().getUsername());
        }


        // Playlist
        if (entity.getPlaylist() != null) {
            PlayListEntity pl = new PlayListEntity();

            PlayListDto p = new PlayListDto();
            p.setId(pl.getId());
            p.setName(pl.getName());
            p.setDescription(pl.getDescription());

            dto.setPlayList(p);
        }


        return dto;
    }

    // 10. Channel videolari uchun DTO
    public static VideoPlayListInfo toPlayListInfoDto(VideoEntity entity) {
        VideoPlayListInfo dto = new VideoPlayListInfo();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());

        // preview attach
        if (entity.getPreviewAttach() != null) {
            AttachDto dtoPreview = new AttachDto();
            dtoPreview.setId(entity.getPreviewAttach().getId());
            dtoPreview.setUrl(entity.getPreviewAttach().getUrl());
        }

        dto.setViewCount(entity.getViewCount());
        dto.setPublishedDate(entity.getPublishedDate());
        dto.setDuration(entity.getDuration());

        return dto;
    }





        public static VideoShortInfo toShortInfo(VideoEntity entity) {
            if (entity == null) {
                return null;
            }

            VideoShortInfo dto = new VideoShortInfo();
            dto.setId(entity.getId());
            dto.setTitle(entity.getTitle());
            dto.setDuration(entity.getDuration());

            // preview attach
            if (entity.getPreviewAttach() != null) {
//                dto.setPreviewAttach(entity.getPreviewAttach());
            }

            // channel mapping
            if (entity.getChannel() != null) {
                ChannelDetailDTO channelDto = new ChannelDetailDTO();
                channelDto.setId(entity.getChannel().getId());
                channelDto.setName(entity.getChannel().getName());
//                channelDto.setPhotoUrl(entity.getChannel().getPhotoUrl());
                dto.setChannel(channelDto);
            }

            return dto;
        }



}

