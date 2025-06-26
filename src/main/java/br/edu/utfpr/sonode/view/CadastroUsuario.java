// CadastroUsuario.java
package br.edu.utfpr.sonode.view;

import br.edu.utfpr.sonode.controller.UsuarioController;
import br.edu.utfpr.sonode.model.Usuario;

import javax.swing.*;
import java.awt.*;

public class CadastroUsuario extends JFrame {
    private final JTextField txtNome  = new JTextField();
    private final JTextField txtEmail = new JTextField();
    private final JPasswordField txtSenha = new JPasswordField();
    private final UsuarioController ctrl = new UsuarioController();

    public CadastroUsuario(JFrame parent) {
        super("Cadastro de Usuário");
        setLayout(new GridLayout(0,2,5,5));

        add(new JLabel("Nome:"));       add(txtNome);
        add(new JLabel("E-mail:"));     add(txtEmail);
        add(new JLabel("Senha:"));      add(txtSenha);

        JButton btnSalvar   = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");
        add(btnSalvar); add(btnCancelar);

        btnSalvar.addActionListener(e -> {
            Usuario u = new Usuario();
            u.setNome(txtNome.getText().trim());
            u.setEmail(txtEmail.getText().trim());
            u.setSenha(new String(txtSenha.getPassword()));
            ctrl.cadastrarUsuario(u);
            JOptionPane.showMessageDialog(this,"Usuário cadastrado!");
            dispose();
        });
        btnCancelar.addActionListener(e -> dispose());

        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }
}
