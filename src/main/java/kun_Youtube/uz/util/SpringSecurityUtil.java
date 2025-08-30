package kun_Youtube.uz.util;


import kun_Youtube.uz.config.CustomUserDetails;
import kun_Youtube.uz.enumh.ProfileRoleEnum;
import kun_Youtube.uz.exseption.AppBadException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class SpringSecurityUtil {

    public static Integer currentProfileId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
//            return null; //
             throw new AppBadException("Not authenticated");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails user) {
            log.info("getId: " + user.getId());
            return user.getId();
        }    if (principal instanceof String str && str.equals("anonymousUser")) {
            throw new AppBadException("NNNNNN-❌ Anonymous user — login required");
        }

//        throw new AppBadException("❌NNNNNN Unknown principal type: " + principal.getClass().getName());

        return null; //
    }
//    public static Integer currentProfileId() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
//        return user.getId();  // ← agar bu yerga null tushayotgan bo‘lsa, unda user.getId() noto‘g‘ri
//    }

    //Comment Service

    public static Boolean hasAnyRoles(ProfileRoleEnum... roles) {
        List<String> roleList = Arrays.stream(roles).map(Enum::name).toList();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        for (GrantedAuthority authority : user.getAuthorities()) {
            if (roleList.contains(authority.getAuthority())) {
                return true;
            }
        }
        return false;
    }




}
