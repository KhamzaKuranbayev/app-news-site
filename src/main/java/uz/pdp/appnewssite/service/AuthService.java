package uz.pdp.appnewssite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.appnewssite.entity.User;
import uz.pdp.appnewssite.exceptions.ResourceNotFoundException;
import uz.pdp.appnewssite.payload.RegisterDto;
import uz.pdp.appnewssite.payload.Response;
import uz.pdp.appnewssite.repository.RoleRepository;
import uz.pdp.appnewssite.repository.UserRepository;
import uz.pdp.appnewssite.utils.AppConstants;

@Service
public class AuthService implements UserDetailsService {

    final UserRepository userRepository;
    final RoleRepository roleRepository;
    final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Response registerUser(RegisterDto registerDto) {
        if (!registerDto.getPassword().equals(registerDto.getPrePassword()))
            return new Response("The Passwords do not match!", false);

        if (userRepository.existsByUsername(registerDto.getUsername()))
            return new Response("Such username already exists!", false);

        User user = new User(
                registerDto.getFullName(),
                registerDto.getUsername(),
                passwordEncoder.encode(registerDto.getPassword()),
                roleRepository.findByName(AppConstants.USER).orElseThrow(() -> new ResourceNotFoundException("Not found role with " + AppConstants.USER)),
                true
        );

        userRepository.save(user);

        return new Response("Successfully registered!", true);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
