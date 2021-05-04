package uz.pdp.appnewssite.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginDto {

    @NotNull(message = "username required")
    private String username;

    @NotNull(message = "password required")
    private String password;

}
