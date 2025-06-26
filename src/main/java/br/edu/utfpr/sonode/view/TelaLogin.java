package br.edu.utfpr.sonode.view;

import br.edu.utfpr.sonode.controller.UsuarioController;
import br.edu.utfpr.sonode.model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class TelaLogin extends JFrame {
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JButton btnLogin;
    private JButton btnCadastrar;
    private UsuarioController usuarioController;

    public TelaLogin() {
        super("SoNode - Login");
        usuarioController = new UsuarioController();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panel.add(txtEmail);

        panel.add(new JLabel("Senha:"));
        txtSenha = new JPasswordField();
        panel.add(txtSenha);

        btnLogin = new JButton("Login");
        panel.add(btnLogin);

        btnCadastrar = new JButton("Cadastrar-se");
        panel.add(btnCadastrar);

        add(panel, BorderLayout.CENTER);

        btnLogin.addActionListener(e -> fazerLogin());
        btnCadastrar.addActionListener(e -> abrirTelaCadastro());
    }

    private void fazerLogin() {
        String email = txtEmail.getText();
        String senha = new String(txtSenha.getPassword());

        if (email.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Optional<Usuario> usuarioOpt = usuarioController.login(email, senha);
        if (usuarioOpt.isPresent()) {
            JOptionPane.showMessageDialog(this, "Login bem-sucedido!");
            new MenuPrincipal(usuarioOpt.get()).setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Email ou senha inválidos.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirTelaCadastro() {
        new CadastroUsuario(this).setVisible(true);
    }
}