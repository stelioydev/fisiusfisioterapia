package br.com.fisius.controller;

import br.com.fisius.dto.*;
import br.com.fisius.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Login simples para demonstração local.
 * Retorna um token fake porque a segurança JWT foi desativada temporariamente.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UsuarioRepository repo;
    private final PasswordEncoder encoder;

    public AuthController(UsuarioRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        return repo.findByEmail(req.email())
                .filter(u -> encoder.matches(req.senha(), u.getSenha()))
                .<ResponseEntity<?>>map(u -> ResponseEntity.ok(new LoginResponse("demo-token-sem-jwt", u.getNome(), u.getEmail())))
                .orElseGet(() -> ResponseEntity.status(401).body("Login ou senha inválidos"));
    }
}
