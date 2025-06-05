package com.jose.projeto_funcionario.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class ProjetoResponseDTO {
    private Long id;
    private String nome;
    private LocalDate dataCriacao;
    private List<FuncionarioResponseDTO> funcionarios;
}
