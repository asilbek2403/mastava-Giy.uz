package kun_Youtube.uz.service;


import kun_Youtube.uz.dtov.ReportCreateDto;
import kun_Youtube.uz.dtov.ReportInfoDto;
import kun_Youtube.uz.dtov.profile.ProfileDto;
import kun_Youtube.uz.entity.ProfileEntity;
import kun_Youtube.uz.entity.ReportEntity;
import kun_Youtube.uz.repository.ProfileRepository;
import kun_Youtube.uz.repository.ReportRepository;
import kun_Youtube.uz.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private ProfileRepository profileRepository;



    /**
     * 1. Create Report (USER)
     */
    public ReportInfoDto create(ReportCreateDto dto) {
        Integer profileId = SpringSecurityUtil.currentProfileId();
        ProfileEntity profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        ReportEntity entity = new ReportEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setProfile(profile);
        entity.setContent(dto.getContent());
        entity.setEntityId(dto.getEntityId());
        entity.setType(dto.getType());
        entity.setCreatedDate(LocalDateTime.now());

        reportRepository.save(entity);
        return toDTO(entity);
    }

    /**
     * 2. Report List Pagination (ADMIN)
     */
    public Page<ReportInfoDto> pagination(int page, int size) {
        PageRequest pageable;
        pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<ReportEntity> entityPage = reportRepository.findAll(pageable);

        List<ReportInfoDto> dtoList = new ArrayList<>();
        for (ReportEntity entity : entityPage.getContent()) {
            dtoList.add(toDTO(entity));
        }

        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }

    /**
     * 3. Remove Report by id (ADMIN)
     */
    public Boolean delete(String id) {
        ReportEntity entity = reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found"));
        reportRepository.delete(entity);
        return true;
    }


    /**
     * 4. Report List By User id (ADMIN)
     */
    public List<ReportInfoDto> getByUser(Integer userId) {
        List<ReportEntity> list = reportRepository.findByProfileId(userId);
        List<ReportInfoDto> dtoList = new ArrayList<>();

        for (ReportEntity entity : list) {
            dtoList.add(toDTO(entity));
        }
        return dtoList;
    }

    // 🔹 Entity → DTO converter
    private ReportInfoDto toDTO(ReportEntity entity) {
        ReportInfoDto dto = new ReportInfoDto();
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setEntityId(entity.getEntityId());
        dto.setType(entity.getType());
        dto.setCreatedDate(entity.getCreatedDate());

        if (entity.getProfile() != null) {
            ProfileDto profile = new ProfileDto();
            profile.setId(entity.getProfile().getId());
            profile.setName(entity.getProfile().getName());
            profile.setSurname(entity.getProfile().getSurname());
            if (entity.getProfile().getPhoto() != null) {
                profile.setPhoto(entity.getProfile().getPhoto().getId());
            }
            dto.setProfile(profile);
        }
        return dto;
    }
}
