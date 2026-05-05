package br.com.fisius.controller;

import br.com.fisius.entity.Servico;
import br.com.fisius.repository.ServicoRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/** Serviços da clínica. */
@RestController
@RequestMapping("/api/servicos")
public class ServicoController {
    private final ServicoRepository repo;
    public ServicoController(ServicoRepository repo) { this.repo = repo; }
    @GetMapping public List<Servico> listar() { return repo.findAll(); }
    @PostMapping public Servico salvar(@RequestBody Servico s) { return repo.save(s); }
    @PutMapping("/{id}") public Servico atualizar(@PathVariable Long id, @RequestBody Servico s) { s.setId(id); return repo.save(s); }
    @DeleteMapping("/{id}") public void excluir(@PathVariable Long id) { repo.deleteById(id); }
}
