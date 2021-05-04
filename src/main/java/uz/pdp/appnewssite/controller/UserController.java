package uz.pdp.appnewssite.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appnewssite.payload.Response;
import uz.pdp.appnewssite.payload.UserDto;
import uz.pdp.appnewssite.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize(value = "hasAuthority('ADD_USER')")
    @PostMapping
    public HttpEntity<?> addUser(@Valid @RequestBody UserDto userDto) {
        Response response = userService.addUser(userDto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }
}
