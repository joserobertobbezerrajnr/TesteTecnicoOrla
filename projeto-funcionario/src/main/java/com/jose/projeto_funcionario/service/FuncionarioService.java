package com.jose.projeto_funcionario.service;

import com.jose.projeto_funcionario.dto.FuncionarioRequestDTO;
import com.jose.projeto_funcionario.dto.FuncionarioResponseDTO;
import com.jose.projeto_funcionario.exception.ResourceNotFoundException;
import com.jose.projeto_funcionario.model.Funcionario;
import com.jose.projeto_funcionario.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    @Transactional
    public FuncionarioResponseDTO criarFuncionario(FuncionarioRequestDTO funcionarioDTO) {
        if (funcionarioRepository.findByCpf(funcionarioDTO.getCpf()).isPresent()) {
            throw new IllegalArgumentException("Já existe um funcionário com o CPF: " + funcionarioDTO.getCpf());
        }
        if (funcionarioRepository.findByEmail(funcionarioDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Já existe um funcionário com o email: " + funcionarioDTO.getEmail());
        }

        Funcionario funcionario = new Funcionario(
                funcionarioDTO.getNome(),
                funcionarioDTO.getCpf(),
                funcionarioDTO.getEmail(),
                funcionarioDTO.getSalario()
        );
        funcionario = funcionarioRepository.save(funcionario);
        return convertToFuncionarioResponseDTO(funcionario);
    }

    @Transactional(readOnly = true)
    public List<FuncionarioResponseDTO> listarTodosFuncionarios() {
        return funcionarioRepository.findAll().stream()
                .map(this::convertToFuncionarioResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FuncionarioResponseDTO buscarFuncionarioPorId(Long id) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado com ID: " + id));
        return convertToFuncionarioResponseDTO(funcionario);
    }

    private FuncionarioResponseDTO convertToFuncionarioResponseDTO(Funcionario funcionario) {
        FuncionarioResponseDTO dto = new FuncionarioResponseDTO();
        dto.setId(funcionario.getId());
        dto.setNome(funcionario.getNome());
        dto.setCpf(funcionario.getCpf());
        dto.setEmail(funcionario.getEmail());
        dto.setSalario(funcionario.getSalario());
        return dto;
    }
}
