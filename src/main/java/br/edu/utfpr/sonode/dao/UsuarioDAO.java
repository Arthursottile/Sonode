// UsuarioDAO.java
package br.edu.utfpr.sonode.dao;

import br.edu.utfpr.sonode.model.Usuario;

public class UsuarioDAO extends GenericDAO<Usuario, Long> {
    public UsuarioDAO() {
        super(Usuario.class);
    }
}
