package com.example.springprojeto3;

import com.example.springprojeto3.Exception.EntityNotFoundException;
import com.example.springprojeto3.Exception.PasswordInvalidException;
import com.example.springprojeto3.Exception.UsernameUniqueViolationException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar(Usuario usuario) {

        try{
            return usuarioRepository.save(usuario);
        } catch (org.springframework.dao.DataIntegrityViolationException ex){
            throw new UsernameUniqueViolationException(String.format("Username {%s} ja cadastrado",
                    usuario.getUsername()));

        }

    }

    @Transactional()
    public Usuario buscarPorId(Long id){
        return usuarioRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuario id=%s nao encontrado", id))
        );
    }

    @Transactional
    public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
        if (!novaSenha.equals(confirmaSenha)){
            throw new PasswordInvalidException("Nova Password nao confere com a confirmacao");
        }

        Usuario usuario = buscarPorId(id);
        if(!usuario.getPassword().equals(senhaAtual)){
            throw new PasswordInvalidException("A password inserida está incorreta");
        }

        usuario.setPassword(novaSenha);
        return usuario;
    }

    @Transactional
    public List<Usuario> buscarTodos() {
        List<Usuario> lista_usuarios = new ArrayList<>();
        lista_usuarios = usuarioRepository.findAll();
        return lista_usuarios;
    }


}
