package ru.tour.services.impl;


import org.springframework.security.core.userdetails.UserDetailsService;
import ru.tour.domain.User;
import ru.tour.model.UserEditBindingModel;
import ru.tour.model.UserRegisterBindingModel;


public interface UserService extends UserDetailsService {
    void create(UserRegisterBindingModel userServiceModele);
    void edit(UserEditBindingModel userServiceModele);

    void edit(User userServiceModele);

    boolean isUsernameTaken(String username);

    boolean isEmailTaken(String email);

    UserRegisterBindingModel findByUsername(String username);

    User findById(String id);

}