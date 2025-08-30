package kun_Youtube.uz.service;


import kun_Youtube.uz.dtov.commitLike.CommentCreateDto;
import kun_Youtube.uz.dtov.commitLike.CommentDto;

import kun_Youtube.uz.dtov.commitLike.CommentInfoDto;
import kun_Youtube.uz.dtov.profile.ProfileDto;
import kun_Youtube.uz.dtov.videol.VideoEntity;
import kun_Youtube.uz.entity.ProfileEntity;
import kun_Youtube.uz.entity.commentLike.CommentEntity;
import kun_Youtube.uz.exseption.AppBadException;
import kun_Youtube.uz.repository.CommentRepository;
import kun_Youtube.uz.repository.ProfileRepository;
import kun_Youtube.uz.repository.VideoRepository;
import kun_Youtube.uz.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;


import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import java.util.UUID;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private AttachService attachService;

    // 1. Create
    public CommentDto create(String videoId, Integer profileId, CommentCreateDto dto) {
        ProfileEntity profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new AppBadException("Profile not found"));

        VideoEntity video = videoRepository.findById(videoId)
                .orElseThrow(() -> new AppBadException("Video not found"));

        CommentEntity entity = new CommentEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setContent(dto.getContent());
        entity.setVideo(video);
        entity.setProfile(profile);
        entity.setCreatedDate(LocalDateTime.now());

        // agar reply comment bo‘lsa
        if (dto.getReplyId() != null) {
            CommentEntity reply = get(dto.getReplyId());
            entity.setReply(reply);
        }

        commentRepository.save(entity);
        return toDto(entity);
    }

    // 2. Update
    public CommentDto update(String id, CommentCreateDto dto) {
        CommentEntity entity = get(id);
        Integer profileId = SpringSecurityUtil.currentProfileId();
        if (!entity.getProfile().getId().equals(profileId)) {
            throw new AppBadException("You can not update this comment");
        }
        entity.setContent(dto.getContent());

        if (dto.getReplyId() != null) {
            CommentEntity reply = get(dto.getReplyId());
            entity.setReply(reply);
        }

        commentRepository.save(entity);
        return toDto(entity);
    }

    // 3. Delete (soft delete)
    public Boolean delete(String id, Integer profileId) {
        CommentEntity entity = get(id);

        if (!entity.getProfile().getId().equals(profileId)) {
            throw new AppBadException("You can not delete this comment");
        }

        commentRepository.delete(entity); // yoki entity.setVisible(false) qilib saqlash
        return true;
    }

    // 4. Get all comments by videoId (with pagination)
    public Page<CommentDto> getAllByVideoId(String videoId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<CommentEntity> pageObj = commentRepository.findByVideoId(videoId, pageRequest);

        List<CommentDto> dtoList = new LinkedList<>();
        pageObj.forEach(entity -> dtoList.add(toDto(entity)));

        return new PageImpl<>(dtoList, pageRequest, pageObj.getTotalElements());
    }

    // 5. Get replies of a comment
    public List<CommentDto> getReplies(String commentId) {
        List<CommentEntity> list = commentRepository.findByReplyId(commentId);
        List<CommentDto> dtoList = new LinkedList<>();
        list.forEach(entity -> dtoList.add(toDto(entity)));
        return dtoList;
    }

    // ---------- Helpers ----------
    private CommentDto toDto(CommentEntity entity) {
        CommentDto dto = new CommentDto();
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setLikeCount(entity.getLikeCount());
        dto.setDisLikeCount(entity.getDislikeCount());

        // Profile qo‘shish
        ProfileDto profile = new ProfileDto();
        profile.setId(entity.getProfile().getId());
        profile.setName(entity.getProfile().getName());
        profile.setSurname(entity.getProfile().getSurname());
        if (entity.getProfile().getPhoto() != null) {
            profile.setPhoto(entity.getProfile().getPhoto().getId());
        }
        dto.setProfile(profile);

        // Agar reply bo‘lsa
        if (entity.getReply() != null) {
            CommentDto replyDto = new CommentDto();
            replyDto.setId(entity.getReply().getId());
            replyDto.setContent(entity.getReply().getContent());

            dto.setReply(replyDto); // dto ichiga qo‘shib qo‘yamiz
        }


        return dto;
    }

    private CommentEntity get(String id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new AppBadException("Comment not found"));
    }




    // 6. Get all comments by profileId (with pagination)
    public Page<CommentDto> getAllByProfileId(Integer profileId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<CommentEntity> pageObj = commentRepository.findByProfileId(profileId, pageRequest);

        List<CommentDto> dtoList = new LinkedList<>();
        pageObj.forEach(entity -> dtoList.add(toDto(entity)));

        return new PageImpl<>(dtoList, pageRequest, pageObj.getTotalElements());
    }






    public List<CommentInfoDto> getRepliesInfo(String commentId) {
        List<CommentEntity> replies = commentRepository.findByReplyId(commentId);
        List<CommentInfoDto> result = new LinkedList<>();

        for (CommentEntity entity : replies) {
            CommentInfoDto dto = new CommentInfoDto();
            dto.setId(entity.getId());
            dto.setContent(entity.getContent());
            dto.setCreatedDate(entity.getCreatedDate());
            dto.setLikeCount(entity.getLikeCount());
            dto.setDislikeCount(entity.getDislikeCount());

            // Profile qo'shish
            ProfileDto profile = new ProfileDto();
            profile.setId(entity.getProfile().getId());
            profile.setName(entity.getProfile().getName());
            profile.setSurname(entity.getProfile().getSurname());
            if (entity.getProfile().getPhoto() != null) {
                profile.setPhoto(entity.getProfile().getPhoto().getId());
            }
            dto.setProfile(profile);

            result.add(dto);
        }

        return result;
    }








    public Page<CommentDto> getAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<CommentEntity> pageObj = commentRepository.findAll(pageRequest);

        List<CommentDto> dtoList = new LinkedList<>();
        pageObj.forEach(entity -> dtoList.add(toDto(entity)));

        return new PageImpl<>(dtoList, pageRequest, pageObj.getTotalElements());
    }

}
