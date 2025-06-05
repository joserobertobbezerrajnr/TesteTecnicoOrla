package com.jose.projeto_funcionario.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FuncionarioResponseDTO {
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private BigDecimal salario;
}
