package kun_Youtube.uz.service;


import kun_Youtube.uz.controller.ResponseRegistrationDto;
import kun_Youtube.uz.dtov.auth.AuthorizationDto;
import kun_Youtube.uz.dtov.auth.RegistrationDto;
import kun_Youtube.uz.dtov.profile.ProfileDto;
import kun_Youtube.uz.entity.ProfileEntity;
import kun_Youtube.uz.enumh.ProfileRoleEnum;
import kun_Youtube.uz.enumh.ProfileStatusEnum;
import kun_Youtube.uz.exseption.AppBadException;
import kun_Youtube.uz.repository.ProfileRepository;
import kun_Youtube.uz.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AuthService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ProfileRoleService profileRoleService;//serviceda rollarni create qilar edik

//    @Autowired
//    private EmailSenderService emailSenderService;

//    @Autowired
//    private EmailHistoryService emailHistoryService;
//    @Autowired
//    private SmsSendService smsSendService;


    public ResponseRegistrationDto registration (RegistrationDto dto) {
        //validation >> DTo
        //check

        Optional<ProfileEntity> existOptional = profileRepository.findByUsernameAndVisibleIsTrue(dto.getUsername());
        if(existOptional.isPresent()) {
            ProfileEntity profileEntity = existOptional.get();
            if (profileEntity.getStatus().equals(ProfileStatusEnum.NOT_ACTIVE)) {
                profileRoleService.deleteRolesByProfileId(profileEntity.getId());
                profileRepository.deleteById(profileEntity.getId());//agar activ bo'lmasa delete
            }//active bo'lsa bu
            else {
                throw new AppBadException(" BU mana Username aleready exists ekanda ");
            }
        }
//CREATE profile
        ProfileEntity newProfileEntity = new ProfileEntity();
        newProfileEntity.setName(dto.getName());
        newProfileEntity.setSurname(dto.getSurname());
        newProfileEntity.setUsername(dto.getUsername());
        newProfileEntity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        newProfileEntity.setVisible(Boolean.TRUE);
        newProfileEntity.setStatus(ProfileStatusEnum.NOT_ACTIVE);
        profileRepository.save(newProfileEntity);

        //save
        //send sms
//        emailSenderService.sendSimpleMessage("Registrations Sinove" , " Code SMS : 1234",
//                dto.getUsername());


        //create profile roles ***

        profileRoleService.create(newProfileEntity.getId(), ProfileRoleEnum.ROLE_USER);//send

//        emailSenderService.sendRegistrationStyledEmail(newProfileEntity.getUsername());// user emailini beraib yubor
//        smsSendService.sendRegistrationSms(newProfileEntity.getUsername());//sms

        ResponseRegistrationDto responseRegistrationDto = new ResponseRegistrationDto();
        responseRegistrationDto.setUsername(dto.getUsername());
        responseRegistrationDto.setMessage("**** Registration Successful BO'LIBDI ****");

        return responseRegistrationDto;//respons

    }

    //code accountda bormi
//    public String regEmailVerification(String token) {
//        JWTDto jwtDTO = null;
//        try {
//            jwtDTO = JWTUtil.decodeRegistrationToken(token);
//        } catch (ExpiredJwtException e) {
//            throw new AppBadException("JWT Expired");
//        } catch (JwtException e) {
//            throw new AppBadException("JWT Not Valid");
//        }
//
//
//        String username = jwtDTO.getUsername();
//
//        Optional<ProfileEntity> verProfile = profileRepository.findByUsernameAndVisibleIsTrue(username);
//        if(verProfile.isEmpty()) {
//            throw new AppBadException("Username not Found");}
//
//        ProfileEntity profile = verProfile.get();
//        if(!profile.getStatus().equals(ProfileStatusEnum.NOT_ACTIVE)){
//            throw new AppBadException("Username int wrong status");}
//
//
//        boolean emailYed = emailHistoryService.isSmsSendToAccount(username,jwtDTO.getCode());
//        if(emailYed){//check sms code to email
//            profile.setProfileStatus(ProfileStatusEnum.ACTIVE);
//            profileRepository.save(profile);
//            return "Verification successfully completed OK!";
//        }
//        throw new AppBadException("Not Completed");
//
//    }

    public ProfileDto login(AuthorizationDto dto) {
        Optional<ProfileEntity> profileOptional = profileRepository.findByUsernameAndVisibleIsTrue(dto.getUsername());
        if(profileOptional.isEmpty()) {
            throw new AppBadException("Username or password wrong");
        }
        ProfileEntity profile = profileOptional.get();
        if( !bCryptPasswordEncoder.matches(dto.getPassword(), profile.getPassword())) {
            throw new AppBadException("username or password Wrong");
        }
//
        if( !profile.getStatus().equals(ProfileStatusEnum.ACTIVE)) {
            throw new AppBadException("Username in wrong  status");
        }
        ProfileDto responseProfileDto = new ProfileDto();
        responseProfileDto.setName(profile.getName());
        responseProfileDto.setSurname(profile.getSurname());
        responseProfileDto.setUsername(profile.getUsername());//        responseProfileDto.setPassword((dto.getPassword()));
        responseProfileDto.setStatus(profile.getStatus());
        responseProfileDto.setRoleList(profileRoleService.getByProfileId(profile.getId()));//  profileRoleService.getByProfileId(responseProfileDto.getId());
        responseProfileDto.setJwt(JWTUtil.encode(profile.getUsername(),profile.getId(),responseProfileDto.getRoleList()));
        log.info("MANABU JWTDA profileID >>>>>>> " + profile.getId());

        return responseProfileDto;
    }




}
