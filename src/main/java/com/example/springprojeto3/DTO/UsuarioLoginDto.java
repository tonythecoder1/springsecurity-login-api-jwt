package com.example.springprojeto3.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioLoginDto {

    @NotBlank
    @Email(message = "O formato do e-mail está invalido")
    private String username;

    @NotBlank
    @Size(min = 8, max = 10)
    private String password;
}
