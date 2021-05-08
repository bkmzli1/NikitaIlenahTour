package ru.tour.services.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tour.domain.Img;
import ru.tour.model.ImgCreateModel;
import ru.tour.repo.ImgRepo;
import ru.tour.services.impl.ImgService;


@Service
@Transactional
public class ImgServiceImpl implements ImgService {
    private final ImgRepo imgRepo;

    @Autowired
    public ImgServiceImpl(ImgRepo imgRepo) {

        this.imgRepo = imgRepo;
    }

    @Override
    public Img imgCrate(ImgCreateModel taskCreate) {
        Img img = new Img();
        img.setImg(taskCreate.getImg());
        img.setName(taskCreate.getName());
        return img;
    }

    @Override
    public Img imgCrate(Img img) {
        imgRepo.save(img);
        return null;
    }

}
