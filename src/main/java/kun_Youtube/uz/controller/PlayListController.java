package kun_Youtube.uz.controller;


import jakarta.validation.Valid;
import kun_Youtube.uz.dtov.playList.*;
import kun_Youtube.uz.enumh.PlayStatus;
import kun_Youtube.uz.exseption.AppForbiddenException;
import kun_Youtube.uz.service.PlayListService;
import kun_Youtube.uz.util.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/profile/playList")
public class PlayListController {


    @Autowired
    private PlayListService playListService;


    @PostMapping("/create")
    public ResponseEntity<PlayListDto> createPlayList(@Valid @RequestBody PlayListCreate dto) {
      log.info("@@@@@@@@@@@@@@ createPlayList: " + dto.getName() + " \n");
        return ResponseEntity.ok(playListService.create(dto));
    }




    @PutMapping("/update-user/{playListId}")
    public ResponseEntity<PlayListDto> userUpdate(@Valid @PathVariable String playListId, @RequestBody PlayListUpdate request) {

        return ResponseEntity.ok(playListService.updateUser(playListId, request));
    }

//
    @PutMapping("/update-status/{playListId}")
    public ResponseEntity<PlayListDto> updateStatus(@Valid @PathVariable String playListId, @RequestBody PlayStatus status) {
        return ResponseEntity.ok(playListService.changeStatus(playListId,status));
    }



    @DeleteMapping("/{playlistId}")
    public ResponseEntity<?> deletePlaylit(@PathVariable("playlistId") String playlistId) throws AppForbiddenException {
        playListService.deletePlaylist(playlistId);
        return ResponseEntity.ok("Playlist deleted successfully");
    }

//    @GetMapping("/{id}/short-info")
//    public PlayListShortInfo getShortInfoList(@Valid @PathVariable("id") String playlistId) {
//        return playListService.getShortInfo(playlistId);
//    }

//    @GetMapping("/all")
//    public ResponseEntity<List<PlayListDto>> getAllPlayLists() {
//        return ResponseEntity.ok(playListService.getAllPlayLists());
//    }
        @GetMapping("/pagination")
        public ResponseEntity<PageImpl<PlayListInfo>> pagination(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        // PageUtil.page(page) bilan 0-indexga moslash
         PageImpl<PlayListInfo> result = playListService.pagination(PageUtil.page(page), size);
             return ResponseEntity.ok(result);
            }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<PlayListShortInfo>> getByUserId(@PathVariable Integer userId) {
        List<PlayListShortInfo> playlists = playListService.getByUserId(userId);
        return ResponseEntity.ok(playlists);
    }


    @GetMapping("/by-user-plays/{userId}")
    public ResponseEntity<List<PlayListShortInfo>> getUserPlayLists(@RequestParam Integer userId) {
        List<PlayListShortInfo> lists = playListService.getUserPlayLists(userId);
        return ResponseEntity.ok(lists);
    }





    //viewCount
    @GetMapping("/{playlistId}/views/count")
    public ResponseEntity<Long> getPlaylistViewsCount(@PathVariable String playlistId) {
        long totalViews = playListService.getPlaylistViewsCount(playlistId);
        return ResponseEntity.ok(totalViews);
    }



}
