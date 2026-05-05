package br.com.fisius.controller;

import br.com.fisius.entity.Financeiro;
import br.com.fisius.repository.FinanceiroRepository;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

/** Controle financeiro simples com CRUD para relatórios no frontend. */
@RestController
@RequestMapping("/api/financeiro")
public class FinanceiroController {
    private final FinanceiroRepository repo;
    public FinanceiroController(FinanceiroRepository repo) { this.repo = repo; }

    @GetMapping public List<Financeiro> listar() { return repo.findAll(); }
    @PostMapping public Financeiro salvar(@RequestBody Financeiro f) { if (f.getData() == null) f.setData(LocalDate.now()); return repo.save(f); }
    @PutMapping("/{id}") public Financeiro atualizar(@PathVariable Long id, @RequestBody Financeiro f) { f.setId(id); if (f.getData() == null) f.setData(LocalDate.now()); return repo.save(f); }
    @DeleteMapping("/{id}") public void excluir(@PathVariable Long id) { repo.deleteById(id); }
}
