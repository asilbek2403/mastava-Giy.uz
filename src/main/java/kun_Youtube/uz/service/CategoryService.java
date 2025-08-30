package kun_Youtube.uz.service;


import kun_Youtube.uz.dtov.CategoryDto;
import kun_Youtube.uz.entity.CategoryEntity;
import kun_Youtube.uz.exseption.AppBadException;
import kun_Youtube.uz.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDto create(CategoryDto dto){
        Optional<CategoryEntity> entity = categoryRepository.findByName(dto.getName());
        if (entity.isPresent()){
            throw new AppBadException("Category already exists");
        }
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(dto.getName());
        categoryEntity.setVisible(Boolean.TRUE);
        categoryEntity.setCreatedDate(LocalDateTime.now());

        categoryRepository.save(categoryEntity);
        categoryEntity.setId(dto.getId());
        dto.setCreatedDate(categoryEntity.getCreatedDate());
        return dto;

    }


    //update
    public CategoryDto update(Integer id, CategoryDto newDto) {// Jahon
        Optional<CategoryEntity> optional = categoryRepository.findByIdAndVisibleIsTrue(id);
        //buni databasedan berib olamiz keyin ifga (avval bilamy ifning ichida edi
        if (optional.isEmpty()) {
            throw new AppBadException ("Category not found");
        }
        Optional<CategoryEntity> keyOptional = categoryRepository.findByName(newDto.getName()); // Jahon
        if (keyOptional.isPresent() && !id.equals(keyOptional.get().getId())) {
            throw new AppBadException ("Category ispresent");
        }
        // 1-Jahon,2-Iksodiyot,3-Sport
        CategoryEntity entity = optional.get();
        entity.setName(newDto.getName());
        categoryRepository.save(entity);

        newDto.setId(entity.getId());
        return newDto;

    }


    public Boolean delete(Integer id) {
        return categoryRepository.updateVisibleById(id) == 1;
    }

    public List<CategoryDto> getAllByOrder() {
        Iterable<CategoryEntity> iterable = categoryRepository.getAllByCreatedDateDesc();
        List<CategoryDto> dtos = new LinkedList<>();
        iterable.forEach(entity -> dtos.add(toDto(entity)));
        return dtos;
    }

    //PageNation
public Page<CategoryDto> getAllByPage(int page, int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
    return categoryRepository.findAllByOrderByCreatedDateDesc(pageable)
            .map(this::toDto);
}

    private CategoryDto toDto(CategoryEntity entity) {
        CategoryDto dto = new CategoryDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
//AllList
    public List<CategoryDto> getAllByList() {
        List<CategoryEntity> entities = categoryRepository.findAllByOrderByCreatedDateDesc();
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


}


/*
3. Category
    1. Create Category (ADMIN)
    2. Update Category (ADMIN)
    3. Delete Category (ADMIN)
    4. Category List
 */