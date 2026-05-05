package br.com.fisius.controller;

import br.com.fisius.dto.StatusRequest;
import br.com.fisius.entity.Agendamento;
import br.com.fisius.enums.StatusAgendamento;
import br.com.fisius.repository.AgendamentoRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/** CRUD de agenda com atualização rápida de status. */
@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoController {
    private final AgendamentoRepository repo;
    public AgendamentoController(AgendamentoRepository repo) { this.repo = repo; }

    @GetMapping public List<Agendamento> listar() { return repo.findAll(); }

    @PostMapping
    public Agendamento salvar(@RequestBody Agendamento a) {
        if (a.getStatus() == null) a.setStatus(StatusAgendamento.AGENDADO);
        return repo.save(a);
    }

    @PutMapping("/{id}")
    public Agendamento atualizar(@PathVariable Long id, @RequestBody Agendamento a) {
        a.setId(id);
        if (a.getStatus() == null) a.setStatus(StatusAgendamento.AGENDADO);
        return repo.save(a);
    }

    @PatchMapping("/{id}/status")
    public Agendamento status(@PathVariable Long id, @RequestBody StatusRequest req) {
        Agendamento a = repo.findById(id).orElseThrow();
        a.setStatus(StatusAgendamento.valueOf(req.status()));
        return repo.save(a);
    }

    @DeleteMapping("/{id}") public void excluir(@PathVariable Long id) { repo.deleteById(id); }
}
