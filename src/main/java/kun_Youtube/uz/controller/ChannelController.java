package kun_Youtube.uz.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import kun_Youtube.uz.dtov.channel.ChannelCreateDto;
import kun_Youtube.uz.dtov.channel.ChannelDto;
import kun_Youtube.uz.dtov.channel.ChannelUpdateDto;
import kun_Youtube.uz.enumh.ChannelStatus;
import kun_Youtube.uz.service.ChannelService;
import kun_Youtube.uz.util.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/profile/channel")
@Tag(name = "<><<<<>>>>     Channel APIs", description = "API Channel")
public class ChannelController {

    @Autowired
    private ChannelService channelService;

                                        //    1. Create Channel (USER)

     @PreAuthorize("hasAnyRole('USER', 'OWNER', 'MODERATOR')")
     @PostMapping("/create")
        public ResponseEntity<ChannelDto> create(@Valid @RequestBody ChannelCreateDto dto){
        log.info(" ************** Channel create >>>>>>>>>>>>>>>>> {}",dto.getName());
        return ResponseEntity.ok(channelService.create(dto));
     }

                //    2. Update Channel ( USER and OWNER)
     @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
     @PutMapping("/update/{id}")
     public ResponseEntity<ChannelDto> update(@Valid @PathVariable String id ,@RequestBody ChannelUpdateDto dto){
         log.info("************** Channel update >>>>>>>>>>>>>>>>> {}",dto);
         return ResponseEntity.ok(channelService.updateChannel(id,dto));
//        return null;
     }


     @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
     @PatchMapping("/update/photo/{channelId}")
     public ResponseEntity<ChannelDto> updatePhotoID(
            @PathVariable String channelId,
            @RequestParam String photoId) {

        ChannelDto updatedChannel = channelService.updatePhoto(channelId, photoId);
        log.info("      <<<<<<< Photo updated >>>>>>>>>>>>>>>>> ");
        return ResponseEntity.ok(updatedChannel);
     }



     @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
     @PutMapping("/update-banner/{channelId}/{bannerId}")
     public ResponseEntity<ChannelDto> upBanner(@Valid @PathVariable String channelId,
                                               @PathVariable String bannerId) {
        log.info("<><><<>>  Banner id is {} update", bannerId);
        return ResponseEntity.ok(channelService.updateBanner(channelId, bannerId));
     }




     @PreAuthorize("hasRole('ADMIN')")
     @GetMapping("/pageList")
     public ResponseEntity<PageImpl<ChannelDto>> pageChannel(
            @RequestParam (value = "page" , defaultValue = "1") int page,
            @RequestParam (value = "size",defaultValue = "3") int size){
            return ResponseEntity.ok(channelService.pagination(PageUtil.page(page) , size));
     }




     @PermitAll
     @GetMapping("/getId/{channelId}")
     public ResponseEntity<ChannelDto> getByChannelId(@Valid @PathVariable String channelId){
            return ResponseEntity.ok(channelService.getById(channelId));
     }



     @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
     @PatchMapping("/update-status/{id}/status")
     public ResponseEntity<ChannelDto> updateStatus(@Valid @PathVariable String id,
                                                   @RequestParam ChannelStatus status) {
        return ResponseEntity.ok(channelService.statusChange(id, status));
     }



     @PreAuthorize("hasRole('USER')")
     @GetMapping("/my-channels")
     public ResponseEntity<PageImpl<ChannelDto>> getUserChannels(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PageImpl<ChannelDto> result = channelService.getUserChannels(page, size);
        return ResponseEntity.ok(result);
     }


}
