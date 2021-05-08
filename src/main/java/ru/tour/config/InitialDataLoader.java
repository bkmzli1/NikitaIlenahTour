package ru.tour.config;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import ru.tour.model.RoleServiceModel;
import ru.tour.model.UserRegisterBindingModel;
import ru.tour.services.impl.RoleService;
import ru.tour.services.impl.UserService;


@Component
@CrossOrigin(origins = "http://localhost:4200")
public class InitialDataLoader implements ApplicationRunner {

    private final RoleService roleService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public InitialDataLoader(RoleService roleService, UserService userService, ModelMapper modelMapper) {
        this.roleService = roleService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    public void run(ApplicationArguments args) {
        RoleServiceModel adminRole = this.roleService.findByAuthority("ADMIN");
        RoleServiceModel userRole = this.roleService.findByAuthority("USER");

        UserRegisterBindingModel userRoot = this.userService.findByUsername("root");


        if (adminRole == null) {
            RoleServiceModel roleServiceModel = new RoleServiceModel();
            roleServiceModel.setAuthority("ADMIN");
            this.roleService.addRole(roleServiceModel);
        }

        if (userRole == null) {
            RoleServiceModel roleServiceModel = new RoleServiceModel();
            roleServiceModel.setAuthority("USER");
            this.roleService.addRole(roleServiceModel);
        }

        if (userRoot == null){
            UserRegisterBindingModel user = new UserRegisterBindingModel();
            user.setPassword("root");
            user.setFirstName("root");
            user.setLastName("root");
            user.setMiddleName("root");
            user.setEmail("root@root.root");
            user.setUsername("root");
            user.setAdmin(true);
            this.userService.create(user);
        }

    }
}