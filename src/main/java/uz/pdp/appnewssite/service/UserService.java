package uz.pdp.appnewssite.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.appnewssite.entity.User;
import uz.pdp.appnewssite.exceptions.ResourceNotFoundException;
import uz.pdp.appnewssite.payload.Response;
import uz.pdp.appnewssite.payload.UserDto;
import uz.pdp.appnewssite.repository.RoleRepository;
import uz.pdp.appnewssite.repository.UserRepository;

@Service
public class UserService {

    final UserRepository userRepository;
    final RoleRepository roleRepository;
    final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Response addUser(UserDto userDto) {

        User user = new User(
                userDto.getFullName(),
                userDto.getUsername(),
                passwordEncoder.encode(userDto.getPassword()),
                roleRepository.findById(userDto.getRoleId()).orElseThrow(() -> new ResourceNotFoundException("Not found role with id = " + userDto.getRoleId())),
                true
        );

        userRepository.save(user);
        return new Response("User saved!", true);
    }
}
