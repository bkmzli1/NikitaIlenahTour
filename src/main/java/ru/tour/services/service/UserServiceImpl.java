package ru.tour.services.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tour.domain.Roles;
import ru.tour.domain.User;
import ru.tour.model.RoleServiceModel;
import ru.tour.model.UserEditBindingModel;
import ru.tour.model.UserRegisterBindingModel;
import ru.tour.repo.UserRepo;
import ru.tour.services.impl.RoleService;
import ru.tour.services.impl.UserService;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepo userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;
    private final RoleService roleService;


    @Autowired
    public UserServiceImpl(UserRepo userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                           ModelMapper modelMapper, RoleService roleService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
        this.roleService = roleService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findOneByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Wrong");
        }

        return user;
    }

    @Override
    public void create(UserRegisterBindingModel userServiceModel) {
        User userEntity = this.modelMapper.map(userServiceModel, User.class);
        userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
        userEntity.setAccountNonExpired(true);
        userEntity.setAccountNonLocked(true);
        userEntity.setCredentialsNonExpired(true);
        userEntity.setEnabled(true);
        userEntity.setPs(userEntity.getPassword());

        Set<Roles> authorities = new HashSet<>();
        RoleServiceModel roleServiceModel = null;

        roleServiceModel = this.roleService.findByAuthority("USER");
        Roles roles = this.modelMapper.map(roleServiceModel, Roles.class);

        authorities.add(roles);

        if (userServiceModel.isAdmin()) {
            roleServiceModel = this.roleService.findByAuthority("ADMIN");
            Roles rolesAdmin = this.modelMapper.map(roleServiceModel, Roles.class);

            authorities.add(rolesAdmin);
        }
        if (userServiceModel.isExecutor()) {
            roleServiceModel = this.roleService.findByAuthority("EXECUTOR");
            Roles rolesExecutor = this.modelMapper.map(roleServiceModel, Roles.class);

            authorities.add(rolesExecutor);
        }


        userEntity.setAuthorities(authorities);
        this.userRepository.save(userEntity);
    }

    @Override
    public void edit(UserEditBindingModel userServiceModel) {
        User userEntity = this.modelMapper.map(userServiceModel, User.class);
        if (!userServiceModel.isPassordof())
            userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
        else {
            userEntity.setPassword(userRepository.findOneById(userServiceModel.getId()).getPassword());
        }

        userEntity.setAccountNonExpired(true);
        userEntity.setAccountNonLocked(true);
        userEntity.setCredentialsNonExpired(true);
        userEntity.setEnabled(true);
        userEntity.setPs(userEntity.getPassword());


        Set<Roles> authorities = new HashSet<>();
        RoleServiceModel roleServiceModel = null;

        roleServiceModel = this.roleService.findByAuthority("USER");
        Roles roles = this.modelMapper.map(roleServiceModel, Roles.class);

        authorities.add(roles);

        if (userServiceModel.isAdmin()) {
            roleServiceModel = this.roleService.findByAuthority("ADMIN");
            Roles rolesAdmin = this.modelMapper.map(roleServiceModel, Roles.class);

            authorities.add(rolesAdmin);
        }
        if (userServiceModel.isExecutor()) {
            roleServiceModel = this.roleService.findByAuthority("EXECUTOR");
            Roles rolesExecutor = this.modelMapper.map(roleServiceModel, Roles.class);

            authorities.add(rolesExecutor);
        }


        userEntity.setAuthorities(authorities);
        this.userRepository.save(userEntity);
    }

    @Override
    public void edit(User userServiceModele) {

    }

    @Override
    public boolean isUsernameTaken(String username) {
        return this.userRepository.findOneByUsername(username) != null;
    }

    @Override
    public boolean isEmailTaken(String email) {
        return this.userRepository.findByEmail(email) != null;
    }

    @Override
    public UserRegisterBindingModel findByUsername(String username) {
        User userEntity = this.userRepository.findOneByUsername(username);
        UserRegisterBindingModel userModel = null;
        if (userEntity != null) {
            userModel = this.modelMapper.map(userEntity, UserRegisterBindingModel.class);
        }
        return userModel;
    }

    @Override
    public User findById(String id) {
        User userEntity = this.userRepository.findOneById(id);
        return userEntity;
    }


}