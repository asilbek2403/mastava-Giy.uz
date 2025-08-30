package kun_Youtube.uz.config;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kun_Youtube.uz.dtov.jwtDto.JWTDto;
import kun_Youtube.uz.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.Arrays;
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailService customUserDetailsService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return Arrays
                .stream(SpringSecurityConfig.openApiList)
                .anyMatch(p -> pathMatcher.match(p, request.getServletPath()));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            log.info(">>>>>>>>>>>>>>>>>>Authorization header not found");
            return;
        }

        try {
            String token = header.substring(7).trim();
            log.info(">>>>>>>>>>>>>>>>>>>>>>Token: Olindi" + token);

            JWTDto jwtDTO = JWTUtil.decode(token);
            log.info(">>>>>>>>>jwt decod qilindi    "+jwtDTO);
            String username = jwtDTO.getUsername();





//            UserDetails userDetails =
//                    customUserDetailsService.loadUserByUsername(username);
//           log.info("UserDetails yuklandi: " + userDetails.getUsername());

            UserDetails userDetails =
                    customUserDetailsService.loadUserByUsername(username);
            log.info("UserDetails yuklandi: " + userDetails.getUsername());

//  2.1. Agar CustomUserDetails bo‘lsa, profileId ni set qilamiz
            if (userDetails instanceof CustomUserDetails customUser) {
                customUser.setId(jwtDTO.getProfileId());
                log.info(" profileId set qilindi: " + jwtDTO.getProfileId());
            }



            //  3. SecurityContext-ga yuboramiz
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails, // PRINCIPAL — String emas!
                            null,
                            userDetails.getAuthorities()
                    );




            authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );




            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            log.info(" Authentication security contextga qo‘shildi");
        } catch (JwtException | UsernameNotFoundException e) {
            // Optional: log qilishingiz mumkin
            log.info(" JWT yoki UserDetails yuklashda xato: " + e.getMessage());
            e.printStackTrace(); // vaqtinchalik

        }

        filterChain.doFilter(request, response);
    }
}
