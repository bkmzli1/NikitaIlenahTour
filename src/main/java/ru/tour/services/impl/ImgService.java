package ru.tour.services.impl;


import ru.tour.domain.Img;
import ru.tour.model.ImgCreateModel;

public interface ImgService {
   Img imgCrate(ImgCreateModel taskCreate);
   Img imgCrate(Img img);
}
