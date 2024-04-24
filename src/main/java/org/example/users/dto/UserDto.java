package org.example.users.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDto {
    private Long id;
    @NotNull(message = "{error.field.empty}")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "{error.field.email}")
    private String email;
    @Size(max = 100, message = "{error.field.size.max}")
    @NotNull(message = "{error.field.empty}")
    private String firstName;
    @Size(max = 100, message = "{error.field.size.max}")
    @NotNull(message = "{error.field.empty}")
    private String lastName;
    @NotNull(message = "{error.field.empty}")
    private LocalDate birthdate;
    @Size(max = 100, message = "{error.field.size.max}")
    @NotNull(message = "{error.field.empty}")
    private String address;
    @NotNull(message = "{error.field.empty}")
    @Pattern(regexp = "^\\+380(31|32|33|34|35|36|37|38|39|41|42|43|44|45|46|47|48|49|50|59|61|63|66|67|68|73|89|91|92|93|94|95|96|97|98|99)\\d{7}$", message = "{error.field.telephone}")
    private String phoneNumber;
}