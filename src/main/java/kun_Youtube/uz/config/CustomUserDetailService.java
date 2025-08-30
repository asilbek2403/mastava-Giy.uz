package kun_Youtube.uz.config;

import kun_Youtube.uz.entity.ProfileEntity;
import kun_Youtube.uz.enumh.ProfileRoleEnum;
import kun_Youtube.uz.repository.ProfileRepository;
import kun_Youtube.uz.repository.ProfileRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CustomUserDetailService implements UserDetailsService {

    //  ,
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileRoleRepository profileRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleIsTrue(username);
        if (optional.isEmpty()) {
            log.info(" %%%%%%%%%%%User not found with username {} @@@ ", username);
            throw new UsernameNotFoundException(username + " BUUUUM < User >not found EKAN ");
        }

        ProfileEntity profile = optional.get();//Buni ustozim korib tekshirdi Yani BAsic bilan HasRole
        List<ProfileRoleEnum> roleEntities = profileRoleRepository.getRoleListByProfileId(profile.getId());


        return new CustomUserDetails(
                profile.getId(),
                profile.getUsername(),
                profile.getPassword(),
                profile.getStatus(),
                roleEntities        )   ;

    }



}
