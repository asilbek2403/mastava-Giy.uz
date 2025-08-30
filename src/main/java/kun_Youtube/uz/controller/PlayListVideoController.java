package kun_Youtube.uz.controller;

import kun_Youtube.uz.dtov.PlayListVideoCreateDto;
import kun_Youtube.uz.dtov.PlayListVideoInfo;
import kun_Youtube.uz.service.PlayListVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;




import java.util.List;

@RestController
@RequestMapping("/playlist-video")
public class PlayListVideoController {


        @Autowired
        private PlayListVideoService playlistVideoService;

        // 1. Create
        @PostMapping("/create")
        public void create(@RequestBody PlayListVideoCreateDto dto) {
            playlistVideoService.create(dto);
        }

        // 2. Update
        @PutMapping("/update")
        public void update(@RequestBody PlayListVideoCreateDto dto) {
            playlistVideoService.update(dto);
        }

        // 3. Delete
        @DeleteMapping("/delete")
        public void delete(@RequestParam String playlistId,
                           @RequestParam String videoId) {
            playlistVideoService.delete(playlistId, videoId);
        }

        // 4. Get Video list by PlaylistId
//        @GetMapping("/list/{playlistId}")
//        public List<PlayListVideoInfo> getList(@PathVariable String playlistId) {
//            return playlistVideoService.getVideoListByPlaylistId(playlistId);
//        }


}
