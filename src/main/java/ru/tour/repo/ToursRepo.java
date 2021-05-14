package ru.tour.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.tour.domain.Tours;

import java.time.LocalDate;
import java.util.Set;

public interface ToursRepo extends JpaRepository<Tours, String> {
    @Query(value = "SELECT * FROM tours where data_stop = (SELECT max(data_stop)FROM tours) limit 1", nativeQuery = true)
    Tours getMaxCreates();

    @Query(value = "SELECT * FROM tours where data_start = (SELECT min(data_start)FROM tours) limit 1", nativeQuery = true)
    Tours getMinCreates();

    Set<Tours> findByDataStartAfter(LocalDate dataStart);
    Tours findBySvazisId(String svazis_id);
    Tours findByIdGen(String idGen);

    Tours findActionBySvazisId(String svazis_id);
}
