package br.edu.utfpr.sonode.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @OneToMany(mappedBy = "dono", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Projeto> projetosCriados = new HashSet<>();

    @ManyToMany(mappedBy = "colaboradores")
    private Set<Projeto> projetosColaborados = new HashSet<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public Set<Projeto> getProjetosCriados() { return projetosCriados; }
    public void setProjetosCriados(Set<Projeto> projetosCriados) { this.projetosCriados = projetosCriados; }
    public Set<Projeto> getProjetosColaborados() { return projetosColaborados; }
    public void setProjetosColaborados(Set<Projeto> projetosColaborados) { this.projetosColaborados = projetosColaborados; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return id != null && id.equals(usuario.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}