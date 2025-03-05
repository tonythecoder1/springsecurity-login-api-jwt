package com.example.springprojeto3.Web.Controller;

import com.example.springprojeto3.DTO.UsuarioCreateDTO;
import com.example.springprojeto3.DTO.UsuarioMapper;
import com.example.springprojeto3.DTO.UsuarioResponseDTO;
import com.example.springprojeto3.DTO.UsuarioSenhaDTO;
import com.example.springprojeto3.Exception.ErrorMessage;
import com.example.springprojeto3.Usuario;
import com.example.springprojeto3.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Usuarios", description = "Contem todoas as operacoes relacionadas aos recursos para os usuarios")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Operation(summary = "Criar um novo usuario", description = "Recurso para criar um novo usuario",
                responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            UsuarioResponseDTO.class))),
                        @ApiResponse(responseCode = "409", description = "Usuario email ja registado no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                                    ErrorMessage.class))),
                        @ApiResponse(responseCode = "422", description = "Dados de entrada errados",
                                content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                                        ErrorMessage.class))),
                })
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> create(@Valid @RequestBody UsuarioCreateDTO createDTO){
       Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(createDTO));
       return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDTO(user));
    }


    @Operation(summary = "Procurar usuario por ID", description = "Recurso para procurar usuario",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                                    UsuarioResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Nao encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                                    ErrorMessage.class))),
            })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> getById(@PathVariable Long id){
        Usuario user = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(UsuarioMapper.toDTO(user));
    }

    @Operation(summary = "Update de password", description = "Recurso para update de password",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Password atualizada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                                    Void.class))),
                    @ApiResponse(responseCode = "400", description = "Senha nao confirmada",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                                    ErrorMessage.class))),
            })
    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> updatePassword(@PathVariable Long id,@Valid @RequestBody UsuarioSenhaDTO dto){
        Usuario user = usuarioService.editarSenha(id, dto.getSenhaAtual(), dto.getNovaSenha(), dto.getConfirmaSenha());
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<List<UsuarioResponseDTO>> getAll(){
        List<Usuario> users = usuarioService.buscarTodos();
        return ResponseEntity.ok(UsuarioMapper.toListDto(users));
    }


}
