// Projeto.java
package br.edu.utfpr.sonode.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="projeto")
public class Projeto {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String tom;
    private int bpm;

    @ManyToOne
    private Usuario criador;

    private LocalDate dataCriacao;

    public Projeto() {}
    // getters/setters
    public Long       getId()           { return id; }
    public String     getNome()         { return nome; }
    public void       setNome(String n) { this.nome = n; }
    public String     getTom()          { return tom; }
    public void       setTom(String t)  { this.tom = t; }
    public int        getBpm()          { return bpm; }
    public void       setBpm(int b)     { this.bpm = b; }
    public Usuario    getCriador()      { return criador; }
    public void       setCriador(Usuario u) { this.criador = u; }
    public LocalDate  getDataCriacao()  { return dataCriacao; }
    public void       setDataCriacao(LocalDate d) { this.dataCriacao = d; }

    @Override
    public String toString() {
        return nome;
    }
}
