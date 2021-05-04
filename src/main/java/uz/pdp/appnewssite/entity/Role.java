package uz.pdp.appnewssite.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appnewssite.entity.enums.Permission;
import uz.pdp.appnewssite.entity.template.AbstractEntity;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Role extends AbstractEntity {

    @Column(unique = true, nullable = false)
    private String name;                        // ADMIN, USER, ...

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Permission> permissions;

    @Column(columnDefinition = "text")
    private String description;

}
