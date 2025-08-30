package kun_Youtube.uz.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kun_Youtube.uz.dtov.CategoryDto;
import kun_Youtube.uz.service.CategoryService;
import kun_Youtube.uz.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/v1/category")
@Tag(name = "<><><>     Category APIs", description = "API Category")
public class CategoryController {


        @Autowired
        private CategoryService categoryService;

        @PostMapping("/create")
        public ResponseEntity<CategoryDto> createCategory(@Valid  @RequestBody CategoryDto dto) {
            log.info("*** Category yaratdim >>>>>>Category request: {}", dto.getName());
            return ResponseEntity.ok(categoryService.create(dto));

        }

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<CategoryDto> update(@PathVariable("id") Integer id, @RequestBody CategoryDto dto) {
        log.info("*** Update category qilish >>>>>>Category request: {}", dto.getName());
        return ResponseEntity.ok(categoryService.update(id, dto));
    }

//ListALL
    @GetMapping("/c/all")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        log.info("  >>>>>>>>>   All Category List");
        return ResponseEntity.ok(categoryService.getAllByList());
    }


    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
            log.info("*** Delete category qilish >>>>>>Category request: {} ", id);
            return ResponseEntity.ok(categoryService.delete(id));
    }
    @GetMapping("/admin/getList")
    public ResponseEntity<List<CategoryDto>> getAll() {
            log.info("Bu Nomi bilan edi ");
        return ResponseEntity.ok(categoryService.getAllByOrder());
    }
    @GetMapping("/list-by-date")
    public ResponseEntity<List<CategoryDto>> getAllByCreatedDateDesc() {
            log.info("Bu List Category ");
        return ResponseEntity.ok(categoryService.getAllByOrder());
    }
//PAge
    @GetMapping("/c/page")
    public ResponseEntity<Page<CategoryDto>> getCategoriesPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(categoryService.getAllByPage(page, size));
    }

}
