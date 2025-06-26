// VersaoProjeto.java
package br.edu.utfpr.sonode.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="arquivos_versao")
public class VersaoProjeto {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String nomeArquivo;
    private String descricao;
    private LocalDateTime dataHora;
    private int tamanhoKB;
    private int numero;

    @ManyToOne
    private Projeto projeto;

    public VersaoProjeto() {}
    public VersaoProjeto(String nomeArquivo, String descricao,
                         LocalDateTime dataHora, int tamanhoKB, Projeto projeto) {
        this.nomeArquivo = nomeArquivo;
        this.descricao   = descricao;
        this.dataHora    = dataHora;
        this.tamanhoKB   = tamanhoKB;
        this.projeto     = projeto;
    }
    // getters/setters
    public Long            getId()         { return id; }
    public String          getNomeArquivo(){ return nomeArquivo; }
    public String          getDescricao()  { return descricao; }
    public LocalDateTime   getDataHora()   { return dataHora; }
    public int             getTamanhoKB()  { return tamanhoKB; }
    public int             getNumero()     { return numero; }
    public Projeto         getProjeto()    { return projeto; }
    public void setNumero(int n)           { this.numero = n; }

    @Override
    public String toString() {
        return numero + " - " + nomeArquivo;
    }
}
