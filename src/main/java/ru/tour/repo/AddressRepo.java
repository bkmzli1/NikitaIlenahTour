package ru.tour.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tour.domain.Address;
import ru.tour.domain.Tours;

public interface AddressRepo extends JpaRepository<Address, String> {

    Address findByName(String name);
    Address findActionByTours(Tours tours);
}
