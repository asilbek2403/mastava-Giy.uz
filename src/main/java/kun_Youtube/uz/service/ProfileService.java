package kun_Youtube.uz.service;

import kun_Youtube.uz.dtov.profile.*;
import kun_Youtube.uz.entity.ProfileEntity;
import kun_Youtube.uz.enumh.ProfileRoleEnum;
import kun_Youtube.uz.enumh.ProfileStatusEnum;
import kun_Youtube.uz.exseption.AppBadException;
import kun_Youtube.uz.repository.ProfileRepository;
import kun_Youtube.uz.util.MapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileRoleService profileRoleService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;





//ADMIN qilish kk    6. Create Profile (ADMIN)
    public ProfileDto create(ProfileDto dto) {
        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleIsTrue(dto.getUsername());
        if (optional.isPresent()) {
            throw new AppBadException("User exists");
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));//uni qilish kk axir authning o'zida ham borbu
        entity.setUsername(dto.getUsername());
        entity.setStatus(ProfileStatusEnum.ACTIVE);
        entity.setVisible(Boolean.TRUE);

        profileRepository.save(entity);
        profileRoleService.merge(entity.getId(), dto.getRoleList()); // TODO understand
//        profileRoleService.create(entity.getId(), ProfileRoleEnum.ROLE_USER);//send authservice da bor ammo tepadagi bo'ladi




        ProfileDto response = toDTO(entity);
        response.setRoleList(dto.getRoleList());
        return response;
    }


    //usernamedan top
    public ProfileEntity getu(String username) {
        return profileRepository.findByUsernameAndVisibleIsTrue(username).orElseThrow(() -> {
            throw new AppBadException("Profile not found");
        });
    }
    public Boolean delete(String username) {
        ProfileEntity entity = getu(username);  // mavjud profilni olib keladi
        entity.setVisible(false);        // soft delete
        profileRepository.save(entity);
        return true;
    }
    //id sidan top
    public Boolean delete(Integer id) {
        ProfileEntity entity = get(id);  // mavjud profilni olib keladi
        entity.setVisible(false);        // soft delete
        profileRepository.save(entity);
        return true;
    }
    public ProfileEntity get(Integer id) {
        return profileRepository.findByIdAndVisibleIsTrue(id).orElseThrow(() -> {
            throw new AppBadException("Profile not found");
        });
    }


    //5. Get Profile Detail
    public ProfileDetailDto getDetailDto(String username) {
        ProfileEntity entity = getu(username);
        ProfileDetailDto dto = new ProfileDetailDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setUsername(entity.getUsername());
        dto.setPhoto(entity.getPhotoId());
//        dto.setRoleList(entity.getRoleList());


        return dto;
    }
/*
etPhoto() — photo sizda qanday tur bo‘yicha saqlanganiga e’tibor bering:
Agar photo bu yerda UUID bo‘lsa → dto.setPhoto(entity.getPhoto()) yaxshi
Agar photo AttachEntity bo‘lsa → entity.getPhoto().getId() qilish kerak bo‘ladi
 */



//	3. Update Profile Detail(name,surname)
        public ProfileDto update(ProfileUpdateDto dto) {
           Optional<ProfileEntity> entity = profileRepository.findByUsernameAndVisibleIsTrue(dto.getUsername());
            if( !entity.isPresent() ) {
                throw new AppBadException("User NOT found");
            }
            ProfileEntity profileEntity = entity.get();
            profileEntity.setName(dto.getName());//ame(dto.getUsername());
            profileEntity.setSurname(dto.getSurname());
            profileEntity.setUsername(dto.getUsername());
            profileRepository.save(profileEntity);
            ProfileDto profileDto = toDTO(profileEntity);
            log.info("<><><><>  bunda update user: {}", profileDto.getUsername());
            return profileDto;
        }

        public ProfileDto updateEmail(ProfileEmailUser dto) {
           Optional<ProfileEntity> entity = profileRepository.findByUsernameAndVisibleIsTrue(dto.getOldUsername());
            if( !entity.isPresent() ) {
                throw new AppBadException("User NOT found");
            }
            ProfileEntity profileEntity = entity.get();
            profileEntity.setUsername(dto.getNewUsername());//ame(dto.getUsername());
            profileRepository.save(profileEntity);
            ProfileDto profileDto = toDTO(profileEntity);

            return profileDto;
        }

        //All
//    public List<ProfileDto> getAll(){
//            return ;
//    }


//        1. Change password(user role)
        public String changePassword(ProfileChangePassword dto) {
            Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleIsTrue(dto.getUsername());
            if (optional.isEmpty()) {
                throw new AppBadException("User not found");
            }

            ProfileEntity profile = optional.get();

            // Eski parolni tekshiramiz (bu holatda oddiy string solishtirish)
            if (!profile.getPassword().equals(dto.getOldPassword())) {
                throw new AppBadException("Old password is incorrect");
            }

            // Yangi parolni o‘rnatamiz
            profile.setPassword(dto.getNewPassword());

            // Saqlaymiz
            profileRepository.save(profile);
            return "Successful Password";
        }



    public ProfileDto toDTO(ProfileEntity entity) {
        ProfileDto dto = new ProfileDto();
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setUsername(entity.getUsername());
        dto.setPhoto(entity.getPhotoId());
        dto.setStatus(entity.getStatus());
//        dto.setPassword(entity.getPassword());
//        buni createning o'zida beraman //dto.setRoleList(entity.getRoleList());
        return dto;
    }

    //ANIQ USTOZDAN filter uchun ishlataman

public ProfileDto toDto(Object[] mapper) {
    ProfileDto dto = new ProfileDto();
    dto.setId((Integer) mapper[0]);
    dto.setName((String) mapper[1]);
    dto.setSurname((String) mapper[2]);
    dto.setUsername((String) mapper[3]);
    if (mapper[4] != null) {
        dto.setStatus(ProfileStatusEnum.valueOf((String) mapper[4]));
    }
    dto.setCreatedDate(MapperUtil.localDateTime(mapper[5]));
    // String[] → List<ProfileRoleEnum>
    String[] roles = (String[]) mapper[6];
    if (roles != null) {
        List<ProfileRoleEnum> roleList = Arrays.stream(roles)
                .map(ProfileRoleEnum::valueOf)
                .collect(Collectors.toList());
        dto.setRoleList(roleList);
    } else {
        dto.setRoleList(Collections.emptyList());
    }
    return dto;
}


}
/*Profile
1. Change password(user role)
	2. Update Email (with email verification)
	3. Update Profile Detail(name,surname)
	4. Update Profile Attach (main_photo) (delete old attach)
	5. Get Profile Detail (id,name,surname,email,main_photo((url)))
    6. Create Profile (ADMIN)
        (id,name,surname,username(email),Role(ADMIN,MODERATOR))
 */