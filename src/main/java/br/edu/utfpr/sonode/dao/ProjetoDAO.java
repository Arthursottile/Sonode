// ProjetoDAO.java
package br.edu.utfpr.sonode.dao;

import br.edu.utfpr.sonode.model.Projeto;
import br.edu.utfpr.sonode.model.Usuario;
import org.hibernate.Session;
import java.util.List;

public class ProjetoDAO extends GenericDAO<Projeto, Long> {
    public ProjetoDAO() {
        super(Projeto.class);
    }
    @SuppressWarnings("unchecked")
    public List<Projeto> listarPorCriador(Usuario criador) {
        Session s = getSession();
        List<Projeto> list = s.createQuery(
            "from Projeto p where p.criador = :c", Projeto.class)
          .setParameter("c", criador)
          .list();
        s.close();
        return list;
    }
}
