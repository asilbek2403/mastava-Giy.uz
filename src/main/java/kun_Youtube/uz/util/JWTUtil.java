package kun_Youtube.uz.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import kun_Youtube.uz.dtov.jwtDto.JWTDto;
import kun_Youtube.uz.enumh.ProfileRoleEnum;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JWTUtil {


    private static final int tokenLiveTime = 1000 * 3600 * 24; // 1-day
    private static final String secretKey = "veryLongSecretmazgillattayevlasharaaxmojonjinnijonsurbetbekkiydirhonuxlatdibekloxovdangasabekochkozjonduxovmashaynikmaydagapchishularnioqiganbolsangizgapyoqaniqsizmazgi";

    /**
     * General
     */
    public static String encode(String username, Integer profileId, List<ProfileRoleEnum> role) {
        //Securityda >>> RoleLsitMi? (RoleAdmin, RoleUser,

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("username", username);
        extraClaims.put("role", role);
        extraClaims.put("profile_id", profileId); // 🟢 Muhim

        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenLiveTime))
                .signWith(getSignInKey())
                .compact();
    }

    public static JWTDto decode(String token) {
        Claims claims = Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        String username = claims.getSubject();
        List<ProfileRoleEnum> role = (List<ProfileRoleEnum>) claims.get("role");
        Integer profileId = (Integer) claims.get("profile_id"); // 🟢 Mana bu yerda olinyapti


        JWTDto jwtDTO = new JWTDto();
        jwtDTO.setUsername(username);
        jwtDTO.setRole(role);
        jwtDTO.setProfileId(profileId);
        return jwtDTO;
    }

    /**
     * Registration
     */
    public static String encodeForRegistration(String username, Integer code) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("code", code);

        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenLiveTime))
                .signWith(getSignInKey())
                .compact();
    }

    public static JWTDto decodeRegistrationToken(String token) {
        Claims claims = Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        String username = claims.getSubject();
        Integer code = (Integer) claims.get("code");
//        Object codeObj = claims.get("code");
//        Integer code = Integer.valueOf(codeObj.toString());

        JWTDto jwtDTO = new JWTDto();
        jwtDTO.setUsername(username);
        jwtDTO.setCode(code);
        return jwtDTO;
    }

    private static SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}

