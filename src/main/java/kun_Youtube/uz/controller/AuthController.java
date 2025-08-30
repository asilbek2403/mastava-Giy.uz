package kun_Youtube.uz.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kun_Youtube.uz.dtov.auth.AuthorizationDto;
import kun_Youtube.uz.dtov.auth.RegistrationDto;
import kun_Youtube.uz.dtov.profile.ProfileDto;
import kun_Youtube.uz.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




@Slf4j
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth APIs", description = "API list for managing Authorization and Authentication")
public class AuthController {

    @Autowired
    private AuthService authService;

//    private static Logger log = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/registration")
    public ResponseEntity<ResponseRegistrationDto> registration(@Valid @RequestBody RegistrationDto dto) {
        log.info("Profile registration");
        return ResponseEntity.ok(authService.registration(dto));
    }

    @PostMapping("/login")
    @Operation(summary = "Authorization", description = "Api used for Authorization")
    public ResponseEntity<ProfileDto> login(@Valid @RequestBody AuthorizationDto dto) {
        log.info("Profile login");
        return ResponseEntity.ok(authService.login(dto));
    }




}