package kun_Youtube.uz.controller;


import kun_Youtube.uz.dtov.ReportCreateDto;
import kun_Youtube.uz.dtov.ReportInfoDto;
import kun_Youtube.uz.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping("")
    public ResponseEntity<ReportInfoDto> create(@RequestBody ReportCreateDto dto) {
        return ResponseEntity.ok(reportService.create(dto));
    }

    @GetMapping("/admin/pagination")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<ReportInfoDto>> pagination(@RequestParam int page,
                                                          @RequestParam int size) {
        return ResponseEntity.ok(reportService.pagination(page, size));
    }

    @DeleteMapping("/admin/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> delete(@PathVariable String id) {
        return ResponseEntity.ok(reportService.delete(id));
    }

    @GetMapping("/admin/user/{userId}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReportInfoDto>> getByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(reportService.getByUser(userId));
    }
}

