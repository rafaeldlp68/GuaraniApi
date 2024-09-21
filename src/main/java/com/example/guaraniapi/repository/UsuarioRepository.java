package com.example.guaraniapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.guaraniapi.model.Usuario;

/**
 * Interface para realizar operações de CRUD e consultas customizadas na entidade Usuario.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Consulta para encontrar um usuário pelo email
    Optional<Usuario> findByEmail(String email);

    // Consulta para encontrar usuários cujo nome contenha uma sequência de caracteres
    List<Usuario> findByNomeContains(String nome);

    // Consulta para encontrar usuários pelo tipo (aluno ou professor)
    List<Usuario> findByTipoUsuario(String tipoUsuario);
}
