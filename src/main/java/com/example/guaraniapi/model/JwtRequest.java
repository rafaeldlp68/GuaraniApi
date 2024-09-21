package com.example.guaraniapi.model;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Gera automaticamente getters, setters, equals, hashCode e toString
@NoArgsConstructor // Construtor sem argumentos
@AllArgsConstructor // Construtor com todos os argumentos
public class JwtRequest {

    @NotBlank(message = "O email é obrigatório") // Valida que o campo email não esteja vazio
    private String email;

    @NotBlank(message = "A senha é obrigatória") // Valida que o campo senha não esteja vazio
    private String senha;
}
