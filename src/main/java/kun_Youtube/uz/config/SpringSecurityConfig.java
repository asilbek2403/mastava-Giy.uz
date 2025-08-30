package kun_Youtube.uz.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {



//public class SpringSecurityConfigC {
//    @Autowired
//    private UserDetailsService userDetailsService;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    public static String[] openApiList = {
            "/swagger-ui/**",
            "/v3/api-docs",
            "/v3/api-docs/**"
    };



    @Bean
    public AuthenticationProvider authenticationProvider() {
        // authentication - Foydalanuvchining identifikatsiya qilish.
        // Ya'ni berilgan login va parolli user bor yoki yo'qligini aniqlash.
        // authentication (login,password)
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return authenticationProvider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // authorization - Foydalanuvchining tizimdagi huquqlarini tekshirish.
        // Ya'ni foydalanuvchi murojat qilayotgan API-larni ishlatishga ruxsati bor yoki yo'qligini tekshirishdir.
        http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
            authorizationManagerRequestMatcherRegistry
//                    .requestMatchers("/profile/channel/**").permitAll()
                    .requestMatchers("/api/auth/login").permitAll()
                    .requestMatchers("/api/auth", "/api/auth/**").permitAll()
                    .requestMatchers("/api/auth/registration").permitAll()
                    .requestMatchers("/api/v1/profile/**").permitAll()
                    .requestMatchers("/v1/yu-attach/**").permitAll()
                    .requestMatchers(   "/api/auth/**").permitAll()
                    .requestMatchers(openApiList).permitAll()

//                    .requestMatchers("/category/lang").permitAll()
                    // Buni 6 tasini qarab to'g'irlab chiqaman
                    .anyRequest()
                    .authenticated();
//        }).formLogin(Customizer.withDefaults());
          })
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);



        http.httpBasic(Customizer.withDefaults());//Http Basic dan foydalanaman deganim securityga
//        http.csrf(Customizer.withDefaults()); // csrf yoqilgan
//        http.cors(Customizer.withDefaults()); // cors yoqilgan


        http.csrf(AbstractHttpConfigurer::disable); // csrf o'chirilgan
        http.cors(AbstractHttpConfigurer::disable); // cors o'chirilgan


        return http.build();
    }


}

