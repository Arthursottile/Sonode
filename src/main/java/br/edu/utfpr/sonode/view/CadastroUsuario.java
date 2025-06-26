package br.edu.utfpr.sonode.view;

import br.edu.utfpr.sonode.controller.UsuarioController;

import javax.swing.*;
import java.awt.*;

public class CadastroUsuario extends JDialog {
    private JTextField txtNome, txtEmail;
    private JPasswordField txtSenha;
    private JButton btnSalvar;
    private UsuarioController usuarioController;

    public CadastroUsuario(Frame owner) {
        super(owner, "Cadastro de Usuário", true);
        usuarioController = new UsuarioController();

        setSize(400, 250);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        formPanel.add(txtNome);

        formPanel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        formPanel.add(txtEmail);

        formPanel.add(new JLabel("Senha:"));
        txtSenha = new JPasswordField();
        formPanel.add(txtSenha);

        btnSalvar = new JButton("Salvar");
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btnSalvar);
        
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        btnSalvar.addActionListener(e -> salvarUsuario());
    }

    private void salvarUsuario() {
        String nome = txtNome.getText();
        String email = txtEmail.getText();
        String senha = new String(txtSenha.getPassword());

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean sucesso = usuarioController.cadastrarUsuario(nome, email, senha);

        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Este email já está em uso.", "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
        }
    }
}