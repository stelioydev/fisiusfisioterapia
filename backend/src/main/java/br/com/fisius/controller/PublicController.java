package br.com.fisius.controller;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

/** Endpoint público usado para testar se o backend está no ar. */
@RestController
@RequestMapping("/api/public")
public class PublicController {
    @GetMapping("/status")
    public Map<String, String> status() {
        return Map.of("mensagem", "Backend Fisius rodando com sucesso!");
    }
}
