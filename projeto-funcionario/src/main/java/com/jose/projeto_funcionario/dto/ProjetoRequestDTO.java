package com.jose.projeto_funcionario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class ProjetoRequestDTO {
    @NotBlank(message = "O nome do projeto é obrigatório")
    private String nome;

    @NotNull(message = "A data de criação é obrigatória")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataCriacao;
}
