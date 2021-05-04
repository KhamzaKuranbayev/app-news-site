package uz.pdp.appnewssite.service;

import org.springframework.stereotype.Service;
import uz.pdp.appnewssite.entity.Role;
import uz.pdp.appnewssite.payload.Response;
import uz.pdp.appnewssite.payload.RoleDto;
import uz.pdp.appnewssite.repository.RoleRepository;


@Service
public class RoleService {

    final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Response addRole(RoleDto roleDto) {
        if (roleRepository.existsByName(roleDto.getName()))
            return new Response("Such role name already exists!", false);

        Role role = new Role(roleDto.getName(), roleDto.getPermissionList(), roleDto.getDescription());
        roleRepository.save(role);
        return new Response("Role saved!", true);
    }


    public Response editRole(Long id, RoleDto roleDto) {
        return new Response("", true);
    }
}
