package kun_Youtube.uz.service;


import kun_Youtube.uz.dtov.AttachDto;
import kun_Youtube.uz.entity.AttachEntity;
import kun_Youtube.uz.exseption.AppBadException;
import kun_Youtube.uz.repository.AttachRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class AttachService {

    @Autowired
    private AttachRepository attachRepository;


    public String saveToSystem(MultipartFile file){
        try {
            File folder = new File("asil");
            if (!folder.exists()) {
                folder.mkdir();
            }
            byte[] bytes = file.getBytes();
            Path path = Paths.get("asil/" + file.getOriginalFilename());
            Files.write(path, bytes);
            return file.getOriginalFilename();
        } catch (IOException e) {
            e.printStackTrace();
            log.info("  @@@******"+file.getOriginalFilename()+"Yaratdim ****@@@@ ");
            throw new RuntimeException("File save error", e);

        }
//        return null;
    }
//upload


    public AttachDto upload(MultipartFile file){
        if(file.isEmpty()){
            throw new AppBadException("File is NOT FOUND");
        }
        log.info("******* File topilmagan******");


        try{
            String pathFolder=getYmDString();//2025/06/24
            String key = UUID.randomUUID().toString();//asasas-asas-assa
            String extension = getExtension(file.getOriginalFilename()); // jpg , png , .mp4

            //create folder if not exist
            File folder = new File("asil" + "/"+ pathFolder);
            if(!folder.exists()){
                boolean folderCreated = folder.mkdirs();
            }
            //saved to system
            byte[] bytes = file.getBytes();
            Path path = Paths.get("asil" + "/" + pathFolder + "/" + key + "." + extension);
            Files.write(path,bytes);//java.nio.file.NoSuchFileException: attaches\2025\6\24\f184beb6-4fc7-4cd5-83cb-9a2b5e6db36a.jpg


            AttachEntity entity = new AttachEntity();
            entity.setId(key + "." + extension);
            entity.setPath(pathFolder);
//            entity.setSize(file.getSize());
            entity.setOrigenName(file.getOriginalFilename());
            entity.setExtension(extension);
            entity.setVisible(true);
            entity.setCreatedDate(LocalDateTime.now());
            attachRepository.save(entity);

            return toDTO(entity);
        }
        catch( IOException e){
            throw new AppBadException("Upload went wrong");
        }
//        return null;
    }// create holati Fayl

//public AttachDto upload(MultipartFile file){
//    if(file.isEmpty()){
//        throw new AppBadException("File is NOT FOUND");
//    }
//    log.info("******* File topilmagan******");
//
//    try{
//        String pathFolder = getYmDString(); // 2025/06/24
//        String key = UUID.randomUUID().toString(); // UUID
//        String extension = getExtension(file.getOriginalFilename()); // jpg, png, mp4
//
//        // create folder if not exist
//        File folder = new File("asil" + "/" + pathFolder);
//        if(!folder.exists()){
//            folder.mkdirs();
//        }
//
//        // save file to system
//        Path path = Paths.get("asil" + "/" + pathFolder + "/" + key + "." + extension);
//        Files.write(path, file.getBytes());
//
//        // save entity
//        AttachEntity entity = new AttachEntity();
//        entity.setId(key); // faqat UUID
//        entity.setPath(pathFolder);
//        entity.setSize(file.getSize());
//        entity.setOrigenName(file.getOriginalFilename());
//        entity.setExtension(extension);
//        entity.setVisible(true);
//        entity.setCreatedDate(LocalDateTime.now());
//        entity.setName(key + "." + extension); // serverdagi fayl nomi
//        attachRepository.save(entity);
//
//        return toDTO(entity);
//    } catch(IOException e){
//        throw new AppBadException("Upload went wrong");
//    }
//}
    private String getExtension(String fileName) { // dasd.asdasd.zari.jpg
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }// nomi. type
    private String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);
        return year + "/" + month + "/" + day;

    }

    public AttachDto toDTO(AttachEntity entity) {
        AttachDto attachDTO = new AttachDto();
        attachDTO.setId(entity.getId());
        attachDTO.setOriginName(entity.getOrigenName());
//        attachDTO.setSize(entity.getSize());
        attachDTO.setExtension(entity.getExtension());
        attachDTO.setCreatedDate(entity.getCreatedDate());
        attachDTO.setUrl(openURL(entity.getId()));
//        attachDTO.setUrl(openURL(entity.getId()));

        return attachDTO;
    }

    public AttachEntity getEntity(String id) {
        log.info("File yoq hali ");
        return attachRepository.findById(id).orElseThrow(() -> new AppBadException("File not found"));
    }
//open Name

    public ResponseEntity<Resource> openF(String fileName) {
        Path filePath = Paths.get("asil/" + fileName).normalize();
        Resource resource = null;
        try {
            resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                throw new RuntimeException("File not found: " + fileName);
            }
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream"; // Fallback content type
            }
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }



//open id

    public ResponseEntity<Resource> openId(String id) { // d5ab71b2-39a8-4ad2-80b3-729c91c932be.jpg
        AttachEntity entity = getEntity(id);
        Path filePath = Paths.get("asil" + "/" + entity.getPath() + "/" + entity.getId()).normalize();
        // attaches/2025/06/09/d5ab71b2-39a8-4ad2-80b3-729c91c932be.jpg
        Resource resource = null;
        try {
            resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                throw new RuntimeException("File not found: " + filePath);
            }
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream"; // Fallback content type
            }
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


//open downloadasil

    public ResponseEntity<Resource> download(String filename) {
        AttachEntity entity = getEntity(filename);
        try {
            Path file = Paths.get("asil" + "/" + entity.getPath() + "/" + entity.getId()).normalize();
            Resource resource = new UrlResource(file.toUri());

            if (!resource.exists()) {
                throw new AppBadException("File not found");
            }
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + entity.getOrigenName() + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (Exception e) {
            throw new AppBadException("Something went wrong");
        }
    }


    //delete

    public Boolean sremove(String fileId) {
        AttachEntity entity = getEntity(fileId);

        try {
            Path file = Paths.get("asil" + "/" + entity.getPath() + "/" + entity.getId()).normalize();
            boolean deleted = Files.deleteIfExists(file);

            if (!deleted) {
                throw new AppBadException("Could not delete file from filesystem");
            }

            attachRepository.delete(entity);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppBadException("Something went wrong");
        }
    }


    public Page<AttachDto> pagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AttachEntity> entities = attachRepository.findAllByOrderByCreatedDateDesc(pageable);

        List<AttachEntity> entityList = entities.getContent();
        long totalElement = entities.getTotalElements();

        List<AttachDto> dtos = new LinkedList<>();
        entityList.forEach(e -> dtos.add(toDTO(e)));
        return new PageImpl<>(dtos, pageable, totalElement);
    }



    public String openURL(String fileName) {
        return  "/v1/yu-attach/" + fileName;
    }

    public AttachDto openDTO(String id) {
        String baseUrl = "http://localhost:8081"; // yoki https://yourdomain.com
//     String attachUrl;
        AttachDto attachDTO = new AttachDto();
        attachDTO.setUrl(baseUrl+ "/v1/yu-attach/" + id);
        return attachDTO;
    }

}
