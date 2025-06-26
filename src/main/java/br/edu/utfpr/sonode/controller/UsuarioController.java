// UsuarioController.java
package br.edu.utfpr.sonode.controller;

import br.edu.utfpr.sonode.dao.UsuarioDAO;
import br.edu.utfpr.sonode.model.Usuario;
import java.util.List;

public class UsuarioController {
    private final UsuarioDAO dao = new UsuarioDAO();
    public void cadastrarUsuario(Usuario u) {
        dao.salvar(u);
    }
    public List<Usuario> listarUsuarios() {
        return dao.listarTodos();
    }
    public Usuario buscarPorId(Long id) {
        return dao.buscarPorId(id);
    }
}
