package uz.pdp.appnewssite.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

    @NotNull(message = "full name required")
    private String fullName;

    @NotNull(message = "username required")
    private String username;

    @NotNull(message = "password required")
    private String password;

    @NotNull(message = "prePassword required")
    private String prePassword;

}
