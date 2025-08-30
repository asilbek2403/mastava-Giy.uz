package kun_Youtube.uz.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kun_Youtube.uz.dtov.AttachDto;
import kun_Youtube.uz.service.AttachService;
import kun_Youtube.uz.util.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/v1/yu-attach")
@Tag(name = "ATTACH APIs", description = "API ATTACH")
public class AttachController {


    @Autowired
    private AttachService attachService;


    @PostMapping("/upload-create")
    @Operation(summary = "Attach", description = "Api used for Attach")
    public String upload(@RequestParam("file") MultipartFile file) {
        log.info("***** Attach bilan ishlaganimiz UPLOAD *****");
        String fileName = attachService.saveToSystem(file);
        return fileName;

    }
// professional create

    @PostMapping("/upload")
    public ResponseEntity<AttachDto> create(@RequestParam("file") MultipartFile file) {
        log.info("***** Attach bilan professional API UPLOAD *****");
        return ResponseEntity.ok(attachService.upload(file));
    }
    // OPEN name with
    @GetMapping("/open/fileName/{fileName}")
    public ResponseEntity<Resource> openFile(@PathVariable("fileName") String fileName){
        log.info("@@@@*** Attach bilan professional API OPEN id V-OR-V name *****");
        return attachService.openF(fileName);
    }

    @GetMapping("/open/fileId/{fileId}")
    public ResponseEntity<Resource> openI(@PathVariable("fileId") String fileId){
        log.info("****Open file ni id bilan ochishimiz****");
        return attachService.openId( fileId);
    }


    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFileId(@PathVariable String fileId) {
        log.info("****Download file ni id bilan ochishimiz****");
        return attachService.download(fileId);
    }


    @DeleteMapping("/delete/{fileId}")
    public ResponseEntity<Boolean> deleteF(@PathVariable String fileId) {
        log.info("***Delete file ni id bilan ****");
        return ResponseEntity.ok(attachService.sremove(fileId));
    }


    //page
    @GetMapping("/paginationAll")
    public ResponseEntity<Page<AttachDto>> getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("##@@@@@@@Pagination is list Attaches *****@@@@@@ ");
        return ResponseEntity.ok(attachService.pagination(PageUtil.page(page), size));
    }



}
