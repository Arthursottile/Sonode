// MenuPrincipal.java
package br.edu.utfpr.sonode.view;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {
    public MenuPrincipal() {
        super("Sonode - Menu Principal");
        setLayout(new FlowLayout(FlowLayout.CENTER,10,10));

        JButton btnUsuario    = new JButton("Cadastro Usuário");
        JButton btnProjeto    = new JButton("Cadastro Projeto");
        JButton btnVersao     = new JButton("Cadastro Versão");
        JButton btnVisualizar = new JButton("Visualizar Projetos");
        JButton btnSair       = new JButton("Sair");

        btnUsuario   .addActionListener(e -> new CadastroUsuario(this));
        btnProjeto   .addActionListener(e -> new CadastroProjeto(this));
        btnVersao    .addActionListener(e -> new CadastroVersao());
        btnVisualizar.addActionListener(e -> new VisualizadorProjetos());
        btnSair      .addActionListener(e -> System.exit(0));

        add(btnUsuario);
        add(btnProjeto);
        add(btnVersao);
        add(btnVisualizar);
        add(btnSair);

        setSize(500,100);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
