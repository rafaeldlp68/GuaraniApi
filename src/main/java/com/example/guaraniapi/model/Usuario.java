package com.example.guaraniapi.model;

import jakarta.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.Collections;

@Entity
@Getter
@Setter
@NoArgsConstructor // Gera automaticamente um construtor sem argumentos
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gera automaticamente o ID
    private Long id;

    @NotBlank(message = "O nome é obrigatório") // Valida se o campo não está vazio ou em branco
    private String nome;

    @NotBlank(message = "O RA ou matrícula é obrigatório") // Valida se o RA/matrícula não está vazio
    private String ra;

    @NotBlank(message = "A instituição é obrigatória") // Valida se a instituição foi preenchida
    private String instituicao;

    @NotBlank(message = "O email é obrigatório") // Valida se o email foi preenchido
    @Email(message = "O email deve ser válido") // Valida se o email tem um formato válido
    private String email;

    @NotBlank(message = "A senha é obrigatória") // Valida se a senha foi preenchida
    private String senha;

    @NotBlank(message = "O tipo de usuário é obrigatório") // Valida se o tipo de usuário foi especificado (aluno/professor)
    private String tipoUsuario;

    // Método que codifica a senha antes de persistir no banco de dados
    @PrePersist // Esse método será executado antes de salvar o objeto no banco de dados
    public void codificarSenha() {
        this.senha = new BCryptPasswordEncoder().encode(this.senha); // Codifica a senha usando BCrypt
    }

    // Implementação do método da interface UserDetails para retornar as permissões (roles) do usuário
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")); // Todos os usuários têm o papel "ROLE_USER"
    }

    // Retorna o nome de usuário (no caso, o email)
    @Override
    public String getUsername() {
        return this.email;
    }

    // Retorna a senha do usuário
    @Override
    public String getPassword() {
        return this.senha;
    }

    // Verifica se a conta do usuário não está expirada
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Verifica se a conta do usuário não está bloqueada
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Verifica se as credenciais do usuário não estão expiradas
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Verifica se a conta do usuário está habilitada
    @Override
    public boolean isEnabled() {
        return true;
    }
}
