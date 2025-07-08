package com.scm.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserForm {

    @NotBlank(message = "Username is required")
    @Size(min = 3, message = "Min 3 characters is required")
    private String name;
    @NotBlank(message = "Email is required")
    @Email(message = "invalid Email Address")
    private String email;

    @NotBlank
    @Size(min = 6, message = "Min 6 characters is required")
    private String password;
    @NotBlank(message = "About is required")
    private String about;
    @Size(min=8, max = 13, message = "Invalid Phone Number")
    private String phoneNumber;

    // we have to use @Valid  for validate the data in controller
    // also have to take a object of binding result rBindingResult.hasErrors()

}
