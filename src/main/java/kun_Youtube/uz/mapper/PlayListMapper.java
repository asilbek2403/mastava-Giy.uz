package kun_Youtube.uz.mapper;


import kun_Youtube.uz.dtov.channel.ChannelDto;
import kun_Youtube.uz.dtov.playList.ChannelDetailDTO;
import kun_Youtube.uz.dtov.playList.PlayListShortInfo;
import kun_Youtube.uz.dtov.videol.VideoDto;
import kun_Youtube.uz.dtov.videol.VideoEntity;
import kun_Youtube.uz.entity.ChannelEntity;
import kun_Youtube.uz.entity.PlayListEntity;

import java.util.List;
import java.util.stream.Collectors;

public class PlayListMapper {
    public static PlayListShortInfo toShortInfo(PlayListEntity entity, List<VideoEntity> videos, String videoCount) {
        PlayListShortInfo dto = new PlayListShortInfo();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCreatedDate(entity.getCreatedDate());

        // channel (id, name)
        ChannelEntity channel = entity.getChannel();
        if (channel != null) {
            ChannelDetailDTO channelDto = new ChannelDetailDTO();
            channelDto.setId(channel.getId());
            channelDto.setName(channel.getName());
            dto.setChannel(channelDto);
        }

        // videoCount
        dto.setVideoCount(Long.parseLong(videoCount));

        // videoList (faqat birinchi 2 video)
        List<VideoDto> videoDtos = videos.stream()
                .limit(2)
                .map(v -> {
                    VideoDto vd = new VideoDto();
                    vd.setId(v.getId());
                    vd.setTitle(v.getTitle());
                    vd.setDuration(v.getDuration());
                    return vd;
                })
                .collect(Collectors.toList());
        dto.setVideoList(videoDtos);

        return dto;
    }

}
