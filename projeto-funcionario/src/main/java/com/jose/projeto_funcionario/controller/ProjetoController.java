package com.jose.projeto_funcionario.controller;

import com.jose.projeto_funcionario.dto.ProjetoRequestDTO;
import com.jose.projeto_funcionario.dto.ProjetoResponseDTO;
import com.jose.projeto_funcionario.service.ProjetoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projetos")
public class ProjetoController {

    private final ProjetoService projetoService;

    public ProjetoController(ProjetoService projetoService) {
        this.projetoService = projetoService;
    }

    @PostMapping
    public ResponseEntity<ProjetoResponseDTO> criarProjeto(@Valid @RequestBody ProjetoRequestDTO projetoDTO) {
        ProjetoResponseDTO novoProjeto = projetoService.criarProjeto(projetoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProjeto);
    }

    @GetMapping
    public ResponseEntity<List<ProjetoResponseDTO>> listarProjetos() {
        List<ProjetoResponseDTO> projetos = projetoService.listarTodosProjetos();
        return ResponseEntity.ok(projetos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjetoResponseDTO> buscarProjetoPorId(@PathVariable Long id) {
        ProjetoResponseDTO projeto = projetoService.buscarProjetoPorId(id);
        return ResponseEntity.ok(projeto);
    }

    @PostMapping("/{projetoId}/associar-funcionario/{funcionarioId}")
    public ResponseEntity<ProjetoResponseDTO> associarFuncionarioAProjeto(
            @PathVariable Long projetoId, @PathVariable Long funcionarioId) {
        ProjetoResponseDTO projetoAtualizado = projetoService.adicionarFuncionarioAProjeto(projetoId, funcionarioId);
        return ResponseEntity.ok(projetoAtualizado);
    }
}
