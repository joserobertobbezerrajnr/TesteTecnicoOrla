package com.jose.projeto_funcionario.service;

import com.jose.projeto_funcionario.dto.FuncionarioResponseDTO;
import com.jose.projeto_funcionario.dto.ProjetoRequestDTO;
import com.jose.projeto_funcionario.dto.ProjetoResponseDTO;
import com.jose.projeto_funcionario.exception.ResourceNotFoundException;
import com.jose.projeto_funcionario.model.Funcionario;
import com.jose.projeto_funcionario.model.Projeto;
import com.jose.projeto_funcionario.repository.FuncionarioRepository;
import com.jose.projeto_funcionario.repository.ProjetoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjetoService {

    private final ProjetoRepository projetoRepository;
    private final FuncionarioRepository funcionarioRepository;

    public ProjetoService(ProjetoRepository projetoRepository, FuncionarioRepository funcionarioRepository) {
        this.projetoRepository = projetoRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    @Transactional
    public ProjetoResponseDTO criarProjeto(ProjetoRequestDTO projetoDTO) {
        if (projetoRepository.findByNome(projetoDTO.getNome()).isPresent()) {
            throw new IllegalArgumentException("Já existe um projeto com o nome: " + projetoDTO.getNome());
        }
        Projeto projeto = new Projeto(projetoDTO.getNome(), projetoDTO.getDataCriacao());
        projeto = projetoRepository.save(projeto);
        return convertToProjetoResponseDTO(projeto);
    }

    @Transactional(readOnly = true)
    public List<ProjetoResponseDTO> listarTodosProjetos() {
        return projetoRepository.findAll().stream()
                .map(this::convertToProjetoResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProjetoResponseDTO buscarProjetoPorId(Long id) {
        Projeto projeto = projetoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado com ID: " + id));
        return convertToProjetoResponseDTO(projeto);
    }

    @Transactional
    public ProjetoResponseDTO adicionarFuncionarioAProjeto(Long projetoId, Long funcionarioId) {
        Projeto projeto = projetoRepository.findById(projetoId)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado com ID: " + projetoId));
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado com ID: " + funcionarioId));

        projeto.addFuncionario(funcionario);
        projetoRepository.save(projeto);

        return convertToProjetoResponseDTO(projeto);
    }

    private ProjetoResponseDTO convertToProjetoResponseDTO(Projeto projeto) {
        ProjetoResponseDTO dto = new ProjetoResponseDTO();
        dto.setId(projeto.getId());
        dto.setNome(projeto.getNome());
        dto.setDataCriacao(projeto.getDataCriacao());
        if (projeto.getFuncionarios() != null && !projeto.getFuncionarios().isEmpty()) {
            dto.setFuncionarios(projeto.getFuncionarios().stream()
                    .map(this::convertToFuncionarioResponseDTO)
                    .collect(Collectors.toList()));
        }
        return dto;
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
