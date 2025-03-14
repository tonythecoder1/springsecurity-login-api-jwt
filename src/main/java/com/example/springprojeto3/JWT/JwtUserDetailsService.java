package com.example.springprojeto3.JWT;

import com.example.springprojeto3.Usuario;
import com.example.springprojeto3.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.buscarPorUsername(username);
        return new JwtUserDetails(usuario);
    }

    public JWTToken getTokenAuthenticated(String username){
        Usuario.Role role = usuarioService.buscarRolePorUsername(username);
        Usuario usuario = usuarioService.buscarPorUsername(username);
        return JWTUtils.createToken(username, role.name().substring("ROLE_".length()), usuario.getId());
    }
}
