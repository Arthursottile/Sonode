// ProjetoController.java
package br.edu.utfpr.sonode.controller;

import br.edu.utfpr.sonode.dao.ProjetoDAO;
import br.edu.utfpr.sonode.model.Projeto;
import br.edu.utfpr.sonode.model.Usuario;
import java.util.List;

public class ProjetoController {
    private final ProjetoDAO dao = new ProjetoDAO();
    public void cadastrarProjeto(Projeto p) {
        dao.salvar(p);
    }
    public List<Projeto> listarProjetos() {
        return dao.listarTodos();
    }
    public Projeto buscarPorId(Long id) {
        return dao.buscarPorId(id);
    }
    public List<Projeto> listarPorCriador(Usuario u) {
        return dao.listarPorCriador(u);
    }
}
