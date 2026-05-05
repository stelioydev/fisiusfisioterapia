package br.com.fisius.controller;

import br.com.fisius.entity.Paciente;
import br.com.fisius.repository.PacienteRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/** CRUD de pacientes. */
@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {
    private final PacienteRepository repo;
    public PacienteController(PacienteRepository repo) { this.repo = repo; }

    @GetMapping public List<Paciente> listar() { return repo.findAll(); }
    @PostMapping public Paciente salvar(@RequestBody Paciente p) { return repo.save(p); }

    @PutMapping("/{id}")
    public Paciente atualizar(@PathVariable Long id, @RequestBody Paciente p) {
        p.setId(id);
        return repo.save(p);
    }

    @DeleteMapping("/{id}") public void excluir(@PathVariable Long id) { repo.deleteById(id); }
}
