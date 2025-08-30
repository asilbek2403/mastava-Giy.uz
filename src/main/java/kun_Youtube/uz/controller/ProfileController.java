package kun_Youtube.uz.controller;


import jakarta.validation.Valid;
import kun_Youtube.uz.dtov.profile.*;
import kun_Youtube.uz.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

@Slf4j//Log
@RestController
@RequestMapping("/api/v1/profile")
@Tag(name = "BU >>>>>>>>>   Profile APIs", description = "  APIs Profile ")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

//    private static Logger log = LoggerFactory.getLogger(ProfileController.class); //lombok bor shunga static kerakmas

   // 6. Create Profile (ADMIN)

    @PostMapping("/admin/create")
    public ResponseEntity<ProfileDto> create(@Valid @RequestBody ProfileDto dto) {
        log.info("*** yaratdim >>>>>>   Login request: {}", dto.getUsername());
        return ResponseEntity.ok(profileService.create(dto));
    }

// delete
    @DeleteMapping("/admin/deleteID/{user}")
    public ResponseEntity<Boolean> delete(@Valid @PathVariable String username) {
        log.info("***    visible-false(delete) >>>>>>Delete: {}", username);
      return ResponseEntity.ok(profileService.delete(username));
    }


//    @PutMapping("/admin/update/id/{id}")
//    public ResponseEntity<ProfileDto> update ( @PathVariable Integer id, @Valid @RequestBody ProfileDto dto) {
//        return ResponseEntity.ok(profileService.update(id,dto));
//    } // ADMIN





//    @PutMapping("/updateDetails")           // ANY Roldagilar qilaoldi
//    public ResponseEntity<ProfileDto> updateDetail ( ) {
//        return null;
//    }




    // ADMIN
//    @GetMapping("/admin/getlist")
//    public ResponseEntity<Page<ProfileDto>>
//    getAllProfiles(@RequestParam(defaultValue = "1") int page,
//                   @RequestParam(defaultValue = "3") int size) {
//        Page<ProfileDto> profilePage = profileService.getAllProfiles(PageUtil.page(page), size);
//        return ResponseEntity.ok(profilePage);
//    }

    //    @PutMapping("/{id}")
//    public ResponseEntity<CategoryDTO> update(@PathVariable("id") Integer id, @RequestBody CategoryDTO newDto) {
//        return ResponseEntity.ok(service.update(id, newDto));
//    }
//



//    @DeleteMapping("/admin/{id}")
//    public ResponseEntity<Boolean>
//    delete(@PathVariable Integer id) {
//        return ResponseEntity.ok(profileService.delete(id));
//    }
//
//    @GetMapping("")
//    public ResponseEntity<List<CategoryDTO>> getAll() {
//        return ResponseEntity.ok(service.getAllByOrder());
//    }
//
//    // /api/v1/category/lang?language=uz
//    @GetMapping("/lang")
//    public ResponseEntity<List<ProfileDto>> getByLang(@RequestHeader(name = "Accept-Language", defaultValue = "uz") LanguageList language) {
//        List<ProfileDto> list = profileService.getAllLang(language);
//        return ResponseEntity.ok(list);
//    }

    // ADMIN


    // ADMIN
//Get Profile Detail
    @GetMapping("/user/getDetail/{user}")
    public ResponseEntity<ProfileDetailDto> getDetail(@Valid @PathVariable String user) {
        log.info("***    View Profile (GetDetail) >>>>>>Get: {}", user);
        return ResponseEntity.ok(profileService.getDetailDto(user));
    }

    //	3. Update Profile Detail(name,surname)
    @GetMapping("/update/getDetail/{user}")
    public ResponseEntity<ProfileDto> updateM(@Valid @RequestBody ProfileUpdateDto dto) {
        log.info("***   Update Profile (GetDetail name and surname) >>>>>>Get: {}", dto.getUsername());
        return ResponseEntity.ok(profileService.update(dto));
    }

    //	2. Update Email (with email verification)

    @GetMapping("/update/username/{user}")
    public ResponseEntity<ProfileDto> updateU(@Valid @RequestBody ProfileEmailUser dto) {
        log.info("*** Update User--username : {}--->{}",dto.getOldUsername(),dto.getNewUsername());
        return ResponseEntity.ok(profileService.updateEmail(dto));
    }

//    1. Change password(user role)
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ProfileChangePassword dto) {
        profileService.changePassword(dto);
        return ResponseEntity.ok("Password successfully updated");
    }



}
