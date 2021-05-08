package ru.tour.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tour.domain.Roles;


public interface RoleRepo extends JpaRepository<Roles, String> {
    Roles findByAuthority(String authority);
}
