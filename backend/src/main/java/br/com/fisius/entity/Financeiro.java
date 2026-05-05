package br.com.fisius.entity;

import br.com.fisius.enums.*;
import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Lançamento financeiro simples: entrada/saída, status e valor.
 */
@Entity
public class Financeiro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private Double valor;
    @Enumerated(EnumType.STRING)
    private TipoFinanceiro tipo;
    @Enumerated(EnumType.STRING)
    private StatusPagamento status;
    private LocalDate data;
    private Long pacienteId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }
    public TipoFinanceiro getTipo() { return tipo; }
    public void setTipo(TipoFinanceiro tipo) { this.tipo = tipo; }
    public StatusPagamento getStatus() { return status; }
    public void setStatus(StatusPagamento status) { this.status = status; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long pacienteId) { this.pacienteId = pacienteId; }
}
