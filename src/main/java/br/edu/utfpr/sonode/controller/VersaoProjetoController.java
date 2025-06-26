// VersaoProjetoController.java
package br.edu.utfpr.sonode.controller;

import br.edu.utfpr.sonode.dao.VersaoProjetoDAO;
import br.edu.utfpr.sonode.model.Projeto;
import br.edu.utfpr.sonode.model.VersaoProjeto;
import java.util.List;

public class VersaoProjetoController {
    private final VersaoProjetoDAO dao = new VersaoProjetoDAO();
    public void cadastrarVersao(VersaoProjeto v) {
        // calcula o número automático
        List<VersaoProjeto> ant = dao.listarPorProjeto(v.getProjeto());
        v.setNumero(ant.size() + 1);
        dao.salvar(v);
    }
    public List<VersaoProjeto> listarVersoesDoProjeto(Long projetoId) {
        Projeto p = new ProjetoController().buscarPorId(projetoId);
        return dao.listarPorProjeto(p);
    }
    public List<VersaoProjeto> listarVersoesDoProjeto(Projeto p) {
        return dao.listarPorProjeto(p);
    }
}
