package uz.pdp.appnewssite.entity.enums;

public enum  Permission {

    ADD_USER,       // ADMIN
    EDIT_USER,      // ADMIN
    DELETE_USER,    // ADMIN
    VIEW_USERS,     // ADMIN

    ADD_ROLE,       // ADMIN
    EDIT_ROLE,      // ADMIN
    DELETE_ROLE,    // ADMIN
    VIEW_ROLES,     // ADMIN

    ADD_POST,       // ADMIN, ...
    EDIT_POST,      // ADMIN, ...
    DELETE_POST,    // ADMIN, ...

    ADD_COMMENT,        // ALL
    EDIT_COMMENT,       // ALL
    DELETE_MY_COMMENT,  // ALL
    DELETE_COMMENT      // ADMIN, ...

}
