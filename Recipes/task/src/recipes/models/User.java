package recipes.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.*;

@Data
@Entity
@NoArgsConstructor
public class User {

    @Id
    @NotNull
    @Email(regexp = ".+@.+\\..+")
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;

    private final String role = "USER";
}
