package uz.pdp.appnewssite.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appnewssite.aop.CheckPermission;
import uz.pdp.appnewssite.payload.Response;
import uz.pdp.appnewssite.payload.RoleDto;
import uz.pdp.appnewssite.service.RoleService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/role")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class RoleController {

    final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PreAuthorize(value = "hasAuthority('ADD_ROLE')")
    @PostMapping
    public HttpEntity<?> addRole(@Valid @RequestBody RoleDto roleDto) {
        Response response = roleService.addRole(roleDto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    //@PreAuthorize(value = "hasAuthority('EDIT_ROLE')")
    @CheckPermission(value = "EDIT_ROLE")
    @PutMapping("/{id}")
    public HttpEntity<?> editRole(@PathVariable Long id, @Valid @RequestBody RoleDto roleDto) {
        Response response = roleService.editRole(id, roleDto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }
}
