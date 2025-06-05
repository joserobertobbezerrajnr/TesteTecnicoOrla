package com.jose.projeto_funcionario.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "funcionarios")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private BigDecimal salario;

    @ManyToMany(mappedBy = "funcionarios", fetch = FetchType.LAZY)
    private Set<Projeto> projetos = new HashSet<>();

    public Funcionario(String nome, String cpf, String email, BigDecimal salario) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.salario = salario;
    }
}
