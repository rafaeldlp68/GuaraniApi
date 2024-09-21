package com.example.guaraniapi.dto;

import com.example.guaraniapi.model.Usuario;
import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * DTO para receber os dados do usuário no momento do cadastro.
 */
@Data
public class UsuarioCadastro {

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "O RA ou matrícula é obrigatório")
    private String ra;

    @NotBlank(message = "A instituição é obrigatória")
    private String instituicao;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "O email deve ser válido")
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    private String senha;

    @NotBlank(message = "O tipo de usuário é obrigatório")
    private String tipoUsuario;

    /**
     * Método para converter o DTO em uma entidade Usuario.
     * @return Entidade Usuario com os dados convertidos.
     */
    public Usuario converterCadastro() {
        Usuario usuario = new Usuario();
        usuario.setNome(this.nome);
        usuario.setRa(this.ra);
        usuario.setInstituicao(this.instituicao);
        usuario.setEmail(this.email);
        usuario.setSenha(this.senha);
        usuario.setTipoUsuario(this.tipoUsuario);
        return usuario;
    }
}
