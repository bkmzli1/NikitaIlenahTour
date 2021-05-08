package ru.tour.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tour.domain.Img;

public interface ImgRepo extends JpaRepository<Img, String> {


}
