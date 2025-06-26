package br.edu.utfpr.sonode.view;

import br.edu.utfpr.sonode.model.Usuario;
import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {
    private Usuario usuarioLogado;

    public MenuPrincipal(Usuario usuario) {
        super("SoNode - Menu Principal");
        this.usuarioLogado = usuario;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout());

        JLabel lblBoasVindas = new JLabel("Bem-vindo(a), " + usuarioLogado.getNome() + "!", SwingConstants.CENTER);
        lblBoasVindas.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblBoasVindas, BorderLayout.NORTH);

        JPanel panelBotoes = new JPanel(new GridLayout(3, 1, 10, 10));
        panelBotoes.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JButton btnVerProjetos = new JButton("Ver e Gerenciar Projetos");
        JButton btnCriarProjeto = new JButton("Criar Novo Projeto");
        JButton btnLogout = new JButton("Logout");

        panelBotoes.add(btnVerProjetos);
        panelBotoes.add(btnCriarProjeto);
        panelBotoes.add(btnLogout);
        
        add(panelBotoes, BorderLayout.CENTER);

        btnVerProjetos.addActionListener(e -> {
            new VisualizadorProjetos(this, usuarioLogado).setVisible(true);
        });

        btnCriarProjeto.addActionListener(e -> {
            new CadastroProjeto(this, usuarioLogado, null).setVisible(true);
        });
        
        btnLogout.addActionListener(e -> {
            int resp = JOptionPane.showConfirmDialog(this, "Deseja realmente sair?", "Logout", JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                this.dispose();
                new TelaLogin().setVisible(true);
            }
        });
    }
}