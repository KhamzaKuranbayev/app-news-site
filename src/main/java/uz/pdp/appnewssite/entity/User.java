package uz.pdp.appnewssite.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.pdp.appnewssite.entity.enums.Permission;
import uz.pdp.appnewssite.entity.template.AbstractEntity;

import javax.persistence.*;
import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User extends AbstractEntity implements UserDetails {

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Role role;


    // UserDetails - Springdagi User ni detallarini yig'uvchi interface

    private boolean enabled;

    private boolean accountNonExpired = true;

    private boolean accountNonLocked = true;

    private boolean credentialsNonExpired = true;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<Permission> permissionList = this.role.getPermissions();
        Set<GrantedAuthority> grantedAuthorityList = new HashSet<>();

        // 1-usul
        /*for (Permission permission : permissionList) {
            grantedAuthorityList.add((GrantedAuthority) permission::name);
        }*/

        // 2-usul
        for (Permission permission : permissionList) {
            grantedAuthorityList.add(new SimpleGrantedAuthority(permission.name()));
        }

        return grantedAuthorityList;
    }

    public User(String fullName, String username, String password, Role role, boolean enabled) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
    }
}
