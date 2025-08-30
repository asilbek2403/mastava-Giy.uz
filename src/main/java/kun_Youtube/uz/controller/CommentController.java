package kun_Youtube.uz.controller;

import kun_Youtube.uz.dtov.commitLike.CommentCreateDto;
import kun_Youtube.uz.dtov.commitLike.CommentDto;
import kun_Youtube.uz.dtov.commitLike.CommentInfoDto;
import kun_Youtube.uz.service.CommentService;
import kun_Youtube.uz.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // 1. Create
    @PostMapping("/create-comment/{videoId}")
    public CommentDto create(@PathVariable String videoId,
                             @RequestParam Integer profileId,
                             @RequestBody CommentCreateDto dto) {
        return commentService.create(videoId, profileId, dto);
    }

    // 2. Update
    @PutMapping("/update-comment/{commentId}")
    public CommentDto update(@PathVariable String commentId,
                             @RequestBody CommentCreateDto dto) {
        return commentService.update(commentId, dto);
    }

    // 3. Delete
    @DeleteMapping("/delete-comment/{id}")
    public Boolean delete(@PathVariable String id,
                          @RequestParam Integer profileId) {
        return commentService.delete(id, profileId);
    }

    // 4. Get all comments by videoId with pagination
    @GetMapping("/video-by-comment/{videoId}")
    public Page<CommentDto> getAllByVideoId(@PathVariable String videoId,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size) {
        return commentService.getAllByVideoId(videoId, page, size);
    }

    // 5. Get all comments by profileId (ADMIN)
    @GetMapping("/profile-commentAll/{profileId}")
    public Page<CommentDto> getAllByProfileId(@PathVariable Integer profileId,
                                              @RequestParam(defaultValue = "1") int page,
                                              @RequestParam(defaultValue = "8") int size) {
        return commentService.getAllByProfileId(profileId, PageUtil.page(page), size);
    }

    // 6. Get replies of a comment
    @GetMapping("/replies-comment/{commentId}")
    public List<CommentDto> getReplies(@PathVariable String commentId) {
        return commentService.getReplies(commentId);
    }

    // 7. Get all comments (pagination)
    @GetMapping("/commentBall")
    public Page<CommentDto> getAll(@RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "10") int size) {
        return commentService.getAll(PageUtil.page(page), size);
    }


    // 8. Get replied comments by comment Id
    @GetMapping("/replies-info/{commentId}")
    public List<CommentInfoDto> getRepliesInfo(@PathVariable String commentId) {
        return commentService.getRepliesInfo(commentId);
    }

}
