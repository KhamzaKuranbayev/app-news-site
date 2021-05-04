package uz.pdp.appnewssite.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appnewssite.entity.User;
import uz.pdp.appnewssite.payload.LoginDto;
import uz.pdp.appnewssite.payload.RegisterDto;
import uz.pdp.appnewssite.payload.Response;
import uz.pdp.appnewssite.security.JwtProvider;
import uz.pdp.appnewssite.service.AuthService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    final AuthService authService;
    final AuthenticationManager authenticationManager;
    final JwtProvider jwtProvider;

    public AuthController(AuthService authService,
                          AuthenticationManager authenticationManager,
                          JwtProvider jwtProvider) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/register")
    public HttpEntity<?> registerUser(@Valid @RequestBody RegisterDto registerDto) {
        Response response = authService.registerUser(registerDto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PostMapping("/login")
    public HttpEntity<?> loginUser(@Valid @RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        User authenticationUser = (User) authentication.getPrincipal();
        String token = jwtProvider.generateToken(authenticationUser.getUsername(), authenticationUser.getRole());
        return ResponseEntity.ok(token);
    }
}
