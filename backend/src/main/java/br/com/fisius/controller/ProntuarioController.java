package br.com.fisius.controller;

import br.com.fisius.entity.Prontuario;
import br.com.fisius.repository.PacienteRepository;
import br.com.fisius.repository.ProntuarioRepository;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

/** Prontuário simplificado com vínculo opcional ao paciente pelo pacienteId. */
@RestController
@RequestMapping("/api/prontuarios")
public class ProntuarioController {
    private final ProntuarioRepository repo;
    private final PacienteRepository pacientes;

    public ProntuarioController(ProntuarioRepository repo, PacienteRepository pacientes) {
        this.repo = repo;
        this.pacientes = pacientes;
    }

    @GetMapping public List<Prontuario> listar() { return repo.findAll(); }

    @PostMapping
    public Prontuario salvar(@RequestBody Prontuario p) {
        if (p.getPacienteId() != null) {
            pacientes.findById(p.getPacienteId()).ifPresent(p::setPaciente);
        }
        p.setCriadoEm(LocalDateTime.now());
        return repo.save(p);
    }

    @DeleteMapping("/{id}") public void excluir(@PathVariable Long id) { repo.deleteById(id); }
}
