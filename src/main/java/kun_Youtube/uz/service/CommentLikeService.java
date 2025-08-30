package kun_Youtube.uz.service;


import kun_Youtube.uz.dtov.CommentLikeInfo;
import kun_Youtube.uz.entity.CommentLikeEntity;
import kun_Youtube.uz.enumh.LikeType;
import kun_Youtube.uz.repository.CommentLikeRepository;
import kun_Youtube.uz.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentLikeService {

    @Autowired
    private CommentLikeRepository repository;

    /** Create Like/Dislike */
    public void create(String commentId, LikeType status) {
        Integer profileId = SpringSecurityUtil.currentProfileId();

        CommentLikeEntity entity = repository
                .findByCommentIdAndProfileId(commentId, profileId)
                .orElse(null);

        if (entity != null) {
            entity.setType(status);
            repository.save(entity);
        } else {
            CommentLikeEntity newEntity = new CommentLikeEntity();
            newEntity.setCommentId(commentId);
            newEntity.setProfileId(profileId);
            newEntity.setType(status);
            newEntity.setCreatedDate(LocalDateTime.now());
            repository.save(newEntity);
        }
    }

    /** Remove Like */
    public boolean remove(String commentId) {
        Integer profileId = SpringSecurityUtil.currentProfileId();
        if (repository.existsByCommentIdAndProfileId(commentId, profileId)) {
            repository.deleteByCommentIdAndProfileId(commentId, profileId);
            return true;
        }
        return false;
    }

    /** User liked comments (CommentLikeInfo) */
    public List<CommentLikeInfo> userLikedComments() {
        Integer profileId = SpringSecurityUtil.currentProfileId();
        List<CommentLikeEntity> entityList = repository.findByProfileIdOrderByCreatedDateDesc(profileId);

        List<CommentLikeInfo> dtoList = new ArrayList<>();
        for (CommentLikeEntity entity : entityList) {
            CommentLikeInfo dto = new CommentLikeInfo();
            dto.setId(entity.getId());
            dto.setProfileId(entity.getProfileId());
            dto.setCommentId(entity.getCommentId());
            dto.setCreatedDate(entity.getCreatedDate());
            dto.setType(entity.getType());
            dtoList.add(dto);
        }
        return dtoList;
    }


    /** ADMIN: get by userId (CommentLikeInfo) */
    public List<CommentLikeInfo> getUserLikedCommentsByUserId(Integer userId) {
        List<CommentLikeEntity> entityList = repository.findByProfileIdOrderByCreatedDateDesc(userId);

        List<CommentLikeInfo> dtoList = new ArrayList<>();
        for (CommentLikeEntity entity : entityList) {
            CommentLikeInfo dto = new CommentLikeInfo();
            dto.setId(entity.getId());
            dto.setProfileId(entity.getProfileId());
            dto.setCommentId(entity.getCommentId());
            dto.setCreatedDate(entity.getCreatedDate());
            dto.setType(entity.getType());
            dtoList.add(dto);
        }
        return dtoList;
    }


    //Like countni
    public Long getLikeCount(String commentId) {
        return repository.countLikeByCommentId(commentId);
    }
//dislike Countni
    public Long getDislikeCount(String commentId) {
        return repository.countDislikeByCommentId(commentId);
    }


    //Reaction larni userning
    public LikeType getUserReaction(String commentId) {
        Integer profileId = SpringSecurityUtil.currentProfileId();
        CommentLikeEntity entity = repository.findByCommentIdAndProfileId(commentId, profileId).orElse(null);
        return entity != null ? entity.getType() : null;
    }
}
