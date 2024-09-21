package com.example.guaraniapi.service;

import com.example.guaraniapi.model.Usuario;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data // Lombok para gerar automaticamente getters, setters, equals, hashCode, etc.
public class JwtUserDetails implements UserDetails {

    private Long id;
    private String email;
    private String senha;
    private String tipoUsuario;

    // Construtor privado para ser usado apenas dentro da classe
    private JwtUserDetails(Long id, String email, String senha, String tipoUsuario) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
    }

    /**
     * Método estático para construir um JwtUserDetails a partir de um objeto Usuario.
     * @param usuario O objeto Usuario do qual construir os detalhes de autenticação
     * @return Uma instância de JwtUserDetails
     */
    public static JwtUserDetails build(Usuario usuario) {
        return new JwtUserDetails(
            usuario.getId(),
            usuario.getEmail(),
            usuario.getSenha(),
            usuario.getTipoUsuario()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Pode ser customizado para incluir diferentes níveis de autoridade, aqui retornamos vazio por enquanto.
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
