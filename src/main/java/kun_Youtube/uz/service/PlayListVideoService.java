package kun_Youtube.uz.service;


//import kun_Youtube.uz.dtov.ChannelShortDto;
import kun_Youtube.uz.dtov.PlayListVideoCreateDto;
import kun_Youtube.uz.dtov.PlayListVideoInfo;
//import kun_Youtube.uz.dtov.VideoShortDto;
import kun_Youtube.uz.dtov.videol.VideoEntity;
import kun_Youtube.uz.entity.PlayListEntity;
import kun_Youtube.uz.entity.PlayListVideoEntity;
//import kun_Youtube.uz.enumh.PlayStatus;
import kun_Youtube.uz.repository.PlayListRepository;
import kun_Youtube.uz.repository.PlayListVideoRepository;
import kun_Youtube.uz.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class PlayListVideoService {

    @Autowired
        private PlayListVideoRepository playlistVideoRepository;
    @Autowired
    private PlayListRepository playListRepository;
    @Autowired
    private VideoRepository videoRepository;

        // 1. Create
        public void create(PlayListVideoCreateDto dto) {
            PlayListEntity playlist = playListRepository.findById(dto.getPlaylistId())
                    .orElseThrow(() -> new RuntimeException("Playlist not found"));
            VideoEntity video = videoRepository.findById(dto.getVideoId())
                    .orElseThrow(() -> new RuntimeException("Video not found"));

            PlayListVideoEntity entity = new PlayListVideoEntity();
            entity.setPlaylist(playlist);
            entity.setVideo(video);
            entity.setOrderNum(dto.getOrderNum());
            playlistVideoRepository.save(entity);
        }

        // 2. Update
        public void update(PlayListVideoCreateDto dto) {
            PlayListVideoEntity entity = playlistVideoRepository
                    .findByPlaylist_IdAndVideo_Id(dto.getPlaylistId(), dto.getVideoId())
                    .orElseThrow(() -> new RuntimeException("PlaylistVideo not found"));

            entity.setOrderNum(dto.getOrderNum());
            playlistVideoRepository.save(entity);
        }

        // 3. Delete cv
        public void delete(String playlistId, String videoId) {
            PlayListVideoEntity entity = playlistVideoRepository
                    .findByPlaylist_IdAndVideo_Id(playlistId, videoId)
                    .orElseThrow(() -> new RuntimeException("PlaylistVideo not found"));
            playlistVideoRepository.delete(entity);
        }

        // 4. Get Video list by PlaylistId (status = PUBLISHED)
//        public List<PlayListVideoInfo> getVideoListByPlaylistId(String playlistId) {
//            List<PlayListVideoEntity> list = playlistVideoRepository
//                    .findByPlaylist_IdAndVideo_StatusOrderByOrderNumAsc(playlistId, PlayStatus.PUBLIC);
//
//            List<PlayListVideoInfo> dtoList = new ArrayList<>();
//
//            for (PlayListVideoEntity entity : list) {
//                VideoEntity v = entity.getVideo();
//
//                // Video DTO
//                VideoShortDto videoDto = new VideoShortDto();
//                videoDto.setId(v.getId());
//                videoDto.setTitle(v.getTitle());
//                if (v.getPreviewAttach() != null) {
//                    videoDto.setPreviewAttach("/attach/open/" + v.getPreviewAttach().getId());
//                }
//                videoDto.setDuration(v.getDuration());
//
//                // Channel DTO
//                ChannelShortDto channelDto = new ChannelShortDto();
//                channelDto.setId(v.getChannel().getId());
//                channelDto.setName(v.getChannel().getName());
//
//                // PlaylistVideoInfo
//                PlayListVideoInfo info = new PlayListVideoInfo();
//                info.setPlaylistId(entity.getPlaylist().getId());
//                info.setVideo(videoDto);
//                info.setChannel(channelDto);
//                info.setCreatedDate(entity.getCreatedDate());
//                info.setOrderNum(entity.getOrderNum());
//
//                dtoList.add(info);
//            }
//
//            return dtoList;
//        }








}
