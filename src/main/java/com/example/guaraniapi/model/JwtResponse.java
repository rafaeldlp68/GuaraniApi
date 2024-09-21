package com.example.guaraniapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Gera automaticamente getters, setters, equals, hashCode e toString
@NoArgsConstructor // Construtor sem argumentos
@AllArgsConstructor // Construtor com todos os argumentos
public class JwtResponse {

    private String token; // Armazena o token JWT que será retornado ao cliente
}
