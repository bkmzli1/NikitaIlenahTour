package ru.tour.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tour.domain.Svazi;

public interface SvaziRepo extends JpaRepository<Svazi, String> {

}
