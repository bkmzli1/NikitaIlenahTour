package ru.tour.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tour.domain.City;
import ru.tour.domain.Country;
import ru.tour.domain.Tours;

import java.util.Set;

public interface CountryRepo extends JpaRepository<Country, String> {

    Country findByName(String name);

    Country findActionByCities(City city);

    Country findActionByCitiesAddressesTours(Tours tours);
    Country findActionByCitiesAddressesToursId(String cities_addresses_tours_id);

    Set<Country> findByNameAndCitiesName(String name, String cities_name);


    Country findByCitiesId(String cities_id);
}
