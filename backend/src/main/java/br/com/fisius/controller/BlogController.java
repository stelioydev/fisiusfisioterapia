package br.com.fisius.controller;

import br.com.fisius.entity.BlogPost;
import br.com.fisius.repository.BlogPostRepository;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

/** Posts do blog, agora com campo imagemUrl. */
@RestController
@RequestMapping("/api/blog")
public class BlogController {
    private final BlogPostRepository repo;
    public BlogController(BlogPostRepository repo) { this.repo = repo; }

    @GetMapping public List<BlogPost> listar() { return repo.findAll(); }
    @PostMapping public BlogPost salvar(@RequestBody BlogPost b) { if (b.getCriadoEm() == null) b.setCriadoEm(LocalDate.now()); return repo.save(b); }
    @PutMapping("/{id}") public BlogPost atualizar(@PathVariable Long id, @RequestBody BlogPost b) { b.setId(id); if (b.getCriadoEm() == null) b.setCriadoEm(LocalDate.now()); return repo.save(b); }
    @DeleteMapping("/{id}") public void excluir(@PathVariable Long id) { repo.deleteById(id); }
}
