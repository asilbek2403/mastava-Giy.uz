package kun_Youtube.uz.service;


import jakarta.transaction.Transactional;
import kun_Youtube.uz.dtov.videol.VideoEntity;
import kun_Youtube.uz.entity.ProfileEntity;
import kun_Youtube.uz.entity.video.VideoViewEntity;
import kun_Youtube.uz.repository.ProfileRepository;
import kun_Youtube.uz.repository.VideoRepository;
import kun_Youtube.uz.repository.VideoViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VideoViewService {

    @Autowired
    private VideoViewRepository videoViewRepository;
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private ProfileRepository profileRepository;


    @Transactional
    public void saveView(String videoId, Integer profileId) {
        VideoEntity video = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found"));

        ProfileEntity profile = null;
        if (profileId != null) {
            profile = profileRepository.findById(profileId)
                    .orElseThrow(() -> new RuntimeException("Profile not found"));
            if (videoViewRepository.existsByVideoAndProfile(video, profile)) return;
        }

        VideoViewEntity entity = new VideoViewEntity();
        entity.setVideo(video);
        entity.setProfile(profile);
        entity.setViewedDate(LocalDateTime.now());
        videoViewRepository.save(entity);

        // View count ni atomik oshirish
        videoRepository.incrementViewCount(videoId);
    }


    /**
     * Count views by video
     */
    public long getViewsCount(String videoId) {
        return videoViewRepository.countByVideo_Id(videoId);
    }


}
