package br.com.fisius.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Prontuário simplificado do paciente.
 * pacienteId é @Transient: ele vem do formulário, mas não cria coluna duplicada no banco.
 */
@Entity
public class Prontuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @Transient
    private Long pacienteId;

    @Column(length = 4000)
    private String evolucao;
    private String anexoUrl;
    private LocalDateTime criadoEm;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long pacienteId) { this.pacienteId = pacienteId; }
    public String getEvolucao() { return evolucao; }
    public void setEvolucao(String evolucao) { this.evolucao = evolucao; }
    public String getAnexoUrl() { return anexoUrl; }
    public void setAnexoUrl(String anexoUrl) { this.anexoUrl = anexoUrl; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }
}
