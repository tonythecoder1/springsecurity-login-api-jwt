package com.example.springprojeto3.JWT;

import com.example.springprojeto3.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class JwtUserDetails extends User {

    private Usuario usuario;

    public JwtUserDetails(Usuario usuario) {
        super(usuario.getUsername(), usuario.getPassword(),
                AuthorityUtils.createAuthorityList(usuario.getRole().name()));
        this.usuario = usuario;
    }

    private Long id; // 🔥 Adicionando campo para armazenar o ID manualmente

   public Long getId() {
       return this.usuario.getId();
   }

    public void setId(Long id) {  
        this.id = id;
    }
}