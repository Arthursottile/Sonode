package br.edu.utfpr.sonode.controller;

import br.edu.utfpr.sonode.dao.UsuarioDAO;
import br.edu.utfpr.sonode.model.Usuario;
import java.util.Optional;

public class UsuarioController {

    private final UsuarioDAO usuarioDAO;

    public UsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public boolean cadastrarUsuario(String nome, String email, String senha) {
        if (usuarioDAO.findByEmail(email).isPresent()) {
            return false;
        }
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(nome);
        novoUsuario.setEmail(email);
        novoUsuario.setSenha(senha); 
        usuarioDAO.save(novoUsuario);
        return true;
    }

    public Optional<Usuario> login(String email, String senha) {
        Optional<Usuario> usuarioOpt = usuarioDAO.findByEmail(email);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (usuario.getSenha().equals(senha)) {
                return Optional.of(usuario);
            }
        }
        return Optional.empty();
    }
    
    public Optional<Usuario> buscarUsuarioPorEmail(String email) {
        return usuarioDAO.findByEmail(email);
    }
}