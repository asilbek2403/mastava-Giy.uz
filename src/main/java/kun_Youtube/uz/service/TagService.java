package kun_Youtube.uz.service;


import jakarta.validation.Valid;
import kun_Youtube.uz.dtov.tag.TagCreateDto;
import kun_Youtube.uz.dtov.tag.TagDto;
import kun_Youtube.uz.dtov.tag.TagResponseDto;
import kun_Youtube.uz.entity.TagEntity;
import kun_Youtube.uz.enumh.LanguageList;
import kun_Youtube.uz.exseption.NotFoundException;
import kun_Youtube.uz.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class TagService {


    @Autowired
    private TagRepository tagRepository;

    public TagDto create(@Valid TagDto dto) {
         Optional<TagEntity> optional = tagRepository.findByOrderNumber(dto.getOrderNumber());
            if (optional.isPresent()) {
//            throw new AppBadException("OrderNumber " + dto.getOrderNumber() + " already exist");
                return null;
            }
            TagEntity entity = new TagEntity();
            entity.setOrderNumber(dto.getOrderNumber());
            entity.setName(dto.getName());
            entity.setNameUz(dto.getNameUz());
            entity.setNameRu(dto.getNameRu());
            entity.setNameEn(dto.getNameEn());
            entity.setKey(dto.getKey());
            entity.setCreatedDate(LocalDateTime.now());

            tagRepository.save(entity);
            dto.setId(entity.getId());
            dto.setCreatedDate(entity.getCreatedDate());
            return dto;
        }


    public TagDto update(Integer id, TagDto newDto) {
        Optional<TagEntity> optional = tagRepository.findById(id);
        if (optional.isEmpty() || optional.get().getVisible() == Boolean.FALSE) {
            throw new NotFoundException("Section not found");

        }
        TagEntity entity = optional.get();
        entity.setOrderNumber(newDto.getOrderNumber());
        entity.setNameUz(newDto.getNameUz());
        entity.setNameRu(newDto.getNameRu());
        entity.setNameEn(newDto.getNameEn());
        entity.setKey(newDto.getKey());
        newDto.setId(entity.getId());
        newDto.setCreatedDate(entity.getCreatedDate());
//        newDto.setImageId(entity.getImageId());
        tagRepository.save(entity);
        return newDto;
    }


    public Boolean delete(Integer id) {
        var entity = tagRepository.findByIdAndVisibleIsTrue(id)
                .orElseThrow(() -> new NotFoundException("Section not found"));
        int i = tagRepository.updateVisibleById(entity.getId());
        return i == 1;

    }


    public List<TagDto> getAllByOrder() {
        Iterable<TagEntity> iterable = tagRepository.getAllByOrderSorted();
        List<TagDto> dtos = new LinkedList<>();
        iterable.forEach(entity -> dtos.add(toDto(entity)));
        return dtos;
    }

    private TagDto toDto(TagEntity entity) {
        TagDto dto = new TagDto();
        dto.setId(entity.getId());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setKey(entity.getKey());
        dto.setCreatedDate(entity.getCreatedDate());
//        dto.setImageId(entity.getImageId());
        return dto;
    }


    //language
    public List<TagResponseDto> getAllbyLang(LanguageList lang) {
        Iterable<TagEntity> iterable = tagRepository.getAllByOrderSorted();
        List<TagResponseDto> dtos = new LinkedList<>();
        iterable.forEach(entity -> dtos.add(toLangResponseDto(lang, entity)));
        return dtos;
    }

    private TagResponseDto toLangResponseDto(LanguageList lang, TagEntity entity) {
        TagResponseDto dto = new TagResponseDto();
        dto.setId(entity.getId());
        dto.setKey(entity.getKey());
        switch (lang) {
            case UZ:
                dto.setName(entity.getNameUz());
                break;
            case RU:
                dto.setName(entity.getNameRu());
                break;
            case EN:
                dto.setName(entity.getNameEn());
                break;
        }
        return dto;
    }





    //*********Video *********
    public TagDto create(TagCreateDto dto) {
        if (tagRepository.existsByName(dto.getName())) {
            throw new RuntimeException("Tag already exists!");
        }
        TagEntity entity = new TagEntity();
        entity.setName(dto.getName());
        tagRepository.save(entity);

        TagDto response = new TagDto();
        response.setId(entity.getId());
        response.setName(entity.getName());
        return response;
    }

    public List<TagDto> getAll() {
        Iterable<TagEntity> entityList = tagRepository.findAll();
        List<TagDto> dtoList = new ArrayList<>();

        for (TagEntity tag : entityList) {
            TagDto dto = new TagDto();
            dto.setId(tag.getId());
            dto.setName(tag.getName());
            dtoList.add(dto);
        }

        return dtoList;
    }



}
