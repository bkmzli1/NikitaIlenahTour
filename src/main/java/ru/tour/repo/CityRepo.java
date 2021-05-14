package ru.tour.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tour.domain.City;
import ru.tour.domain.Tours;

public interface CityRepo extends JpaRepository<City, String> {

    City findByName(String name);
    City findActionByAddressesTours(Tours tours);
    City findByAddressesId(String addresses_id);
}
