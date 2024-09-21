package com.example.guaraniapi.dto;

import com.example.guaraniapi.model.Usuario;
import org.springframework.data.domain.Page;
import lombok.Getter; // Importa a anotação Getter para Lombok

/**
 * DTO para listar os dados do usuário, incluindo os campos necessários da entidade Usuario.
 */
@Getter // Lombok gera automaticamente os getters para todos os campos
public class UsuarioListagemDto {

    private Long id;
    private String nome;
    private String ra;
    private String instituicao;
    private String email;
    private String tipoUsuario;

    // Construtor que converte uma entidade Usuario em DTO
    public UsuarioListagemDto(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.ra = usuario.getRa();
        this.instituicao = usuario.getInstituicao();
        this.email = usuario.getEmail();
        this.tipoUsuario = usuario.getTipoUsuario();
    }

    // Método para conversão de uma lista paginada de usuários em DTO
    public static Page<UsuarioListagemDto> converter(Page<Usuario> usuarios) {
        return usuarios.map(UsuarioListagemDto::new);
    }
}
