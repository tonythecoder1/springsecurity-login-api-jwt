package com.example.springprojeto3.DTO;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UsuarioResponseDTO {

    private Long id;
    private String username;
    private String role;
}
