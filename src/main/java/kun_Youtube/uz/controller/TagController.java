package kun_Youtube.uz.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kun_Youtube.uz.dtov.tag.TagDto;
import kun_Youtube.uz.dtov.tag.TagResponseDto;
import kun_Youtube.uz.enumh.LanguageList;
import kun_Youtube.uz.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/tag")
@Tag(name = "<><><>     Tag APIs", description = "API Tag")
public class TagController {


    @Autowired
    private TagService tagService;

    @PostMapping("/create")
    public ResponseEntity<TagDto> create(@Valid @RequestBody TagDto dto) {
        log.info(" **** Create tag: {} *****", dto);
        return ResponseEntity.ok(tagService.create(dto));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<TagDto> update(@PathVariable("id") Integer id, @RequestBody TagDto newDto) {
       log.info("***** Update tag: {} *****", newDto);
        return ResponseEntity.ok(tagService.update(id, newDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id) {
        log.info(" ****Delete tag: {} ****", id);
        return ResponseEntity.ok(tagService.delete(id));
    }



    @GetMapping("/getAll")
    public ResponseEntity<List<TagDto>> getAll() {
        log.info("****Get all tags *****");
        return ResponseEntity.ok(tagService.getAllByOrder());
    }

    @GetMapping("/lang/{lang}")
    public ResponseEntity<List<TagResponseDto>> getByLang(@RequestHeader(name = "Accept-Language", defaultValue = "UZ") LanguageList language) {
        log.info("*** getByLang lang: {}", language+"TILIDAAA ***");
        return ResponseEntity.ok(tagService.getAllbyLang(language));
    }




}
