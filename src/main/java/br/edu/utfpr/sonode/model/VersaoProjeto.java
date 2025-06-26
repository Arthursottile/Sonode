package br.edu.utfpr.sonode.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "versoes_projeto")
public class VersaoProjeto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeArquivo;

    @Column(nullable = false)
    private String tipoArquivo; 

    private int numeroVersao;

    @Column(nullable = false)
    private LocalDateTime dataUpload;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projeto_id", nullable = false)
    private Projeto projeto;
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNomeArquivo() { return nomeArquivo; }
    public void setNomeArquivo(String nomeArquivo) { this.nomeArquivo = nomeArquivo; }
    public String getTipoArquivo() { return tipoArquivo; }
    public void setTipoArquivo(String tipoArquivo) { this.tipoArquivo = tipoArquivo; }
    public int getNumeroVersao() { return numeroVersao; }
    public void setNumeroVersao(int numeroVerso) { this.numeroVersao = numeroVerso; }
    public LocalDateTime getDataUpload() { return dataUpload; }
    public void setDataUpload(LocalDateTime dataUpload) { this.dataUpload = dataUpload; }
    public Projeto getProjeto() { return projeto; }
    public void setProjeto(Projeto projeto) { this.projeto = projeto; }
}