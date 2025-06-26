// VersaoProjetoDAO.java
package br.edu.utfpr.sonode.dao;

import br.edu.utfpr.sonode.model.Projeto;
import br.edu.utfpr.sonode.model.VersaoProjeto;
import org.hibernate.Session;
import java.util.List;

public class VersaoProjetoDAO extends GenericDAO<VersaoProjeto, Long> {
    public VersaoProjetoDAO() {
        super(VersaoProjeto.class);
    }
    @SuppressWarnings("unchecked")
    public List<VersaoProjeto> listarPorProjeto(Projeto projeto) {
        Session s = getSession();
        List<VersaoProjeto> list = s.createQuery(
            "from VersaoProjeto v where v.projeto = :p", VersaoProjeto.class)
          .setParameter("p", projeto)
          .list();
        s.close();
        return list;
    }
}
