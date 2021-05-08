package ru.tour.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.tour.domain.Img;
import ru.tour.domain.Services;
import ru.tour.repo.ImgRepo;
import ru.tour.repo.ServicesRepo;
import ru.tour.repo.ToursRepo;
import ru.tour.services.impl.ImgService;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@CrossOrigin
@RequestMapping("/img")
public class ImgController {
    private final ImgRepo imgRepo;
    private final ImgService imgService;
    private final ServicesRepo servicesRepo;
    private final ToursRepo toursRepo;


    @Value("${upload.path}")
    private String uploadPath;

    public ImgController(ImgRepo imgRepo, ImgService imgService, ServicesRepo servicesRepo, ToursRepo toursRepo) {
        this.imgRepo = imgRepo;
        this.imgService = imgService;
        this.servicesRepo = servicesRepo;
        this.toursRepo = toursRepo;
    }

    @PostMapping(value = "/{type}")
    @ResponseBody
    public Object addImg(@RequestBody @Valid MultipartFile[] mfImg, @PathVariable String type, Authentication authentication) {
        File fileUploadPath = new File(uploadPath);


        Set<Img> images = new HashSet<>();
        try {
            for (MultipartFile mf : mfImg) {
                String upFile = "/img";
                File uploadPDir = new File(fileUploadPath.getAbsolutePath() + upFile);
                if (!uploadPDir.exists()) {
                    uploadPDir.mkdirs();
                }
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + mf.getOriginalFilename();

                try {
                    mf.transferTo(new File(uploadPDir + "/" + resultFilename));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Img imgDB = new Img();
                imgDB.setName(mf.getResource().getFilename());
                imgDB.setImg("static" + upFile + "/" + resultFilename);
                images.add(imgDB);
                imgDB.setTitle(type);
                imgRepo.save(imgDB);

            }
            AtomicBoolean profile = new AtomicBoolean(false);


        } catch (NullPointerException e) {
            return null;
        }

        Services services = new Services();
        services.setImg(images);
        services.setName(type);
        servicesRepo.save(services);
        Set<String> imgIDs = new HashSet<>();
        images.forEach(img -> imgIDs.add(img.getId()));
        return imgIDs;
    }

    @PostMapping(value = "/tour/{type}")
    @ResponseBody
    public Object addImgTour(@RequestBody @Valid MultipartFile[] mfImg, @PathVariable String type, Authentication authentication) {
        File fileUploadPath = new File(uploadPath);


        Set<Img> images = new HashSet<>();
        try {
            for (MultipartFile mf : mfImg) {
                String upFile = "/img";
                File uploadPDir = new File(fileUploadPath.getAbsolutePath() + upFile);
                if (!uploadPDir.exists()) {
                    uploadPDir.mkdirs();
                }
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + mf.getOriginalFilename();

                try {
                    mf.transferTo(new File(uploadPDir + "/" + resultFilename));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Img imgDB = new Img();
                imgDB.setName(mf.getResource().getFilename());
                imgDB.setImg("static" + upFile + "/" + resultFilename);
                images.add(imgDB);
                imgDB.setTitle(type);
                imgRepo.save(imgDB);

            }
            AtomicBoolean profile = new AtomicBoolean(false);


        } catch (NullPointerException e) {
            return null;
        }

        Set<String> imgIDs = new HashSet<>();
        images.forEach(img -> imgIDs.add(img.getId()));
        return imgIDs;
    }
}
