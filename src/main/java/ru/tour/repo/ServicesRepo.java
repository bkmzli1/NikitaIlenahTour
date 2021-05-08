package ru.tour.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tour.domain.Services;

public interface ServicesRepo extends JpaRepository<Services, String> {

}
